package com.PrintHobe.PrintingManagement.Service;

import com.PrintHobe.PrintingManagement.DTOs.*;
import com.PrintHobe.PrintingManagement.Entity.*;
import com.PrintHobe.PrintingManagement.Repository.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public DocumentUploadResponse handleUpload(MultipartFile file, DocumentUploadRequest request) throws IOException {

        // Count pages
        PDDocument document = PDDocument.load(file.getInputStream());
        int pages = document.getNumberOfPages();
        document.close();

        // Fetch printer
        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(() -> new RuntimeException("Printer Not Found"));

        // Determine color and cost
        boolean isColor = request.getColor().equalsIgnoreCase("COLOR");
        BigDecimal costPerPage = isColor ? printer.getCostColor() : printer.getCostBw();

        int copies = request.getCopies() == null ? 1 : request.getCopies();
        BigDecimal amount = costPerPage.multiply(BigDecimal.valueOf(pages)).multiply(BigDecimal.valueOf(copies));

        // Time calculation
        double timePerPage = isColor ? printer.getTimePerPageColor() : printer.getTimePerPageBw();
        long totalSeconds = (long) (timePerPage * pages * copies);
        long bufferSeconds = 5 * 60; // 5 minutes = 300 seconds

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime estimatedTime;

        if (printer.getBusyTill() == null || printer.getBusyTill().isBefore(now)) {
            estimatedTime = now.plusSeconds(totalSeconds + bufferSeconds);
        } else {
            estimatedTime = printer.getBusyTill().plusSeconds(totalSeconds + bufferSeconds);
        }

        // Set new busyTill time
        printer.setBusyTill(estimatedTime);
        printerRepository.save(printer);

        // Check user's package
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        boolean canUsePackage = user.getPackagePage() >= pages * copies;

        String originalFileName = file.getOriginalFilename();

        return new DocumentUploadResponse(pages, amount, originalFileName, canUsePackage, estimatedTime);
    }


    @Transactional
    public void saveDocumentUsingPackage(UsePackageRequest request) {
        // Fetch user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch printer
        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(() -> new RuntimeException("Printer not found"));

        // Save the uploaded file to server
        MultipartFile file = request.getFile();
        String filePath = fileStorageService.saveFile(file);
        String originalFileName = file.getOriginalFilename();

        // Create Document entity
        Document document = new Document();
        document.setUser(user);
        document.setPrinter(printer);
        document.setOperator(printer.getOperator());
        document.setColor(Document.Color.valueOf(request.getColor().toUpperCase()));
        document.setSides(Document.Side.valueOf(request.getSides().toUpperCase()));
        document.setPunching(Document.Punching.valueOf(request.getPunching().toUpperCase()));
        document.setCopies(request.getCopies());
        document.setPages(request.getPages());
        document.setUsedPackage(Document.UsedPackage.YES);
        document.setStatus(Document.Status.VERIFYING);
        document.setStartTime(LocalDateTime.now());
        document.setFilePath(filePath);
        document.setOriginalFileName(originalFileName);

        // Save document
        documentRepository.save(document);

        // Deduct used pages from user package
        int totalPages = request.getPages() * request.getCopies();
        user.setPackagePage(user.getPackagePage() - totalPages);
        userRepository.save(user);
    }

    @Transactional
    public void submitPayment(SubmitPaymentRequest request){
        // Checking for duplicate referenceId
        if(paymentRepository.existsById(request.getReferenceId())){
            throw new IllegalArgumentException("Reference ID already exists. Please use valid reference ID");
        }

        // Fetching the user and the operator
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Operator operator = operatorRepository.findById(request.getOperatorId())
                .orElseThrow(()-> new RuntimeException("Operator Not Found"));

        Printer printer = printerRepository.findById(request.getPrinterId())
                .orElseThrow(()-> new RuntimeException("Printer Not Found"));

        // Saving Payment
        Payment payment = new Payment();
        payment.setReferenceId(request.getReferenceId());
        payment.setAmount(request.getAmount());
        payment.setDateNTime(LocalDateTime.now());
        payment.setPaymentFor(Payment.PaymentFor.valueOf(request.getPaymentFor().toUpperCase()));
        payment.setUser(user);
        payment.setOperator(operator);
        payment.setPaymentStatus(Payment.PaymentStatus.VERIFYING);

        paymentRepository.save(payment);

        String filePath = fileStorageService.saveFile(request.getFile());
        String originalFileName = request.getFile().getOriginalFilename();

        //Creating and saving the document
        Document document = new Document();
        document.setUser(user);
        document.setOperator(operator);
        document.setPrinter(printer);
        document.setPages(request.getPages());
        document.setSides(Document.Side.valueOf(request.getSides()));
        document.setCopies(request.getCopies());
        document.setColor(Document.Color.valueOf(request.getColor().toUpperCase()));
        document.setPunching(Document.Punching.valueOf(request.getPunching().toUpperCase()));
        document.setStatus(Document.Status.VERIFYING);
        document.setUsedPackage(Document.UsedPackage.NO);
        document.setStartTime(null);
        document.setEndTime(null);
        document.setFilePath(filePath);
        document.setPayment(payment);
        document.setOriginalFileName(originalFileName);

        documentRepository.save(document);
    }

    public List<OngoingDocumentResponse> getOngoingDocumentForUser(Long userId){
        List<Document.Status> excludedStatus = List.of(Document.Status.COMPLETED, Document.Status.REJECTED);

        List<Document> documents = documentRepository.findByUserUserIdAndStatusNotIn(userId, excludedStatus);

        return documents.stream()
                .map(doc -> {
                    String documentName = extractFileName(doc.getFilePath());  // e.g., "abc.pdf"
                    String type = doc.getColor().name();  // e.g., BW or COLOR
                    BigDecimal cost = calculateCost(doc); // custom method (optional)

                    return new OngoingDocumentResponse(
                            documentName,
                            doc.getPages(),
                            type,
                            cost,
                            doc.getStartTime(),  // or doc.getEndTime() if needed
                            doc.getStatus().name() // Not sure why we are using .name() here
                    );
                })
                .collect(Collectors.toList());
    }

    private String extractFileName(String path) {
        if (path == null) return "Unknown";
        return Path.of(path).getFileName().toString();
    }

    private BigDecimal calculateCost(Document doc) {
        BigDecimal baseCost = doc.getColor() == Document.Color.COLOR
                ? doc.getPrinter().getCostColor()
                : doc.getPrinter().getCostBw();

        int totalPages = doc.getPages() * doc.getCopies();
        return baseCost.multiply(BigDecimal.valueOf(totalPages));
    }

    // This method id for to set the printer's busy time to null
    public void updatePrinterBusyStatusIfNoActiveDocs(Long printerId) {
        long approvedCount = documentRepository.countByPrinter_PrinterIdAndStatus(printerId, Document.Status.APPROVED);
        long verifyingCount = documentRepository.countByPrinter_PrinterIdAndStatus(printerId, Document.Status.VERIFYING);
        long activeCount = approvedCount + verifyingCount;

        if (activeCount == 0) {
            Printer printer = printerRepository.findById(printerId)
                    .orElseThrow(() -> new RuntimeException("Printer not found"));

            printer.setBusyTill(null);
            printerRepository.save(printer);
        }
    }

}
