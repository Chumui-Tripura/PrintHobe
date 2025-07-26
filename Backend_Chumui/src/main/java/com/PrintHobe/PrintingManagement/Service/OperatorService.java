package com.PrintHobe.PrintingManagement.Service;

import com.PrintHobe.PrintingManagement.DTOs.DocumentReferenceInfo;
import com.PrintHobe.PrintingManagement.DTOs.OperatorDashboardResponse;
import com.PrintHobe.PrintingManagement.DTOs.OperatorRegistrationRequest;
import com.PrintHobe.PrintingManagement.DTOs.PackageRequestInfo;
import com.PrintHobe.PrintingManagement.Entity.Document;
import com.PrintHobe.PrintingManagement.Entity.Operator;
import com.PrintHobe.PrintingManagement.Entity.Printer;
import com.PrintHobe.PrintingManagement.Repository.DocumentRepository;
import com.PrintHobe.PrintingManagement.Repository.OperatorRepository;
import com.PrintHobe.PrintingManagement.Repository.PaymentRepository;
import com.PrintHobe.PrintingManagement.Repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerOperator(OperatorRegistrationRequest dto){
        // to check if the email is already taken
        if(operatorRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        // Creating and saving operator
        Operator operator = new Operator();
        operator.setFirstName(dto.getFirstName());
        operator.setLastName(dto.getLastName());
        operator.setEmail(dto.getEmail());
        operator.setPassword(passwordEncoder.encode(dto.getPassword()));
        operator.setPhoneNumber(dto.getPhoneNumber());

        Operator savedOperator = operatorRepository.save(operator);

        // Create and save printer
        Printer printer = new Printer();
        printer.setName(dto.getPrinter().getName());
        printer.setLocation(dto.getPrinter().getLocation());
        printer.setTimePerPageBw(dto.getPrinter().getTimePerPageBw());
        printer.setTimePerPageColor(dto.getPrinter().getTimePerPageColor());
        printer.setCostBw(dto.getPrinter().getCostBw());
        printer.setCostColor(dto.getPrinter().getCostColor());

        printer.setStatus(Printer.PrinterStatus.NOT_AVAILABLE);
        printer.setBusyTill(null);          // Initially no job
        printer.setOperator(savedOperator);

        String hasPackageStr = dto.getPrinter().getHasPackage();
        if (hasPackageStr != null && hasPackageStr.equalsIgnoreCase("YES")) {
            printer.setHasPackage(Printer.HasPackage.YES);
            printer.setPackagePrice(dto.getPrinter().getPackagePrice());
            printer.setPackagePage(dto.getPrinter().getPackagePage());
        } else {
            printer.setHasPackage(Printer.HasPackage.NO);
            printer.setPackagePrice(null);
            printer.setPackagePage(null);
        }
        printerRepository.save(printer);
    }


    // This is for Operator Dashboard
    public Map<String, Object> getPrintStatus(Long printerId, Long operatorId){
        Map<String, Object> stats = new HashMap<>();

        long completedCount = documentRepository.countByPrinter_PrinterIdAndStatus(printerId, Document.Status.COMPLETED);
        long inProgressCount = documentRepository.countByPrinter_PrinterIdAndStatus(printerId, Document.Status.APPROVED);
        BigDecimal totalEarnings = paymentRepository.getTotalEarningsByOperatorId(operatorId);

        stats.put("completedPrints", completedCount);
        stats.put("inProgressPrints", inProgressCount);
        stats.put("totalEarnings", totalEarnings != null ? totalEarnings : BigDecimal.ZERO);
        return stats;
    }

    public OperatorDashboardResponse getOperatorDashboardData(Long operatorId){
        OperatorDashboardResponse response = new OperatorDashboardResponse();

        // Total Completed Printings
        long totalPrintings = documentRepository.countByOperator_OperatorIdAndStatus(operatorId, Document.Status.COMPLETED);

        // Approved documents currently in progress
        long approvedDocs = documentRepository.countByOperator_OperatorIdAndStatus(operatorId, Document.Status.APPROVED);

        // Approved earnings from Approved payments
        BigDecimal totalEarnings = paymentRepository.sumAmountByOperatorIdAndApprovedStatus(operatorId);

        Printer printer = printerRepository.findByOperator_OperatorId(operatorId);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // 24-hour format
        String busyTillTime = "00:00";
        String availableTillTime = "00:00";
        Printer.PrinterStatus printerStatus = Printer.PrinterStatus.NOT_AVAILABLE; // default

        if (printer != null) {
            if (printer.getBusyTill() != null) {
                busyTillTime = printer.getBusyTill().format(timeFormatter);
            }
            if (printer.getAvailableTill() != null) {
                availableTillTime = printer.getAvailableTill().format(timeFormatter);
            }
            printerStatus = printer.getStatus();
        }


        List<DocumentReferenceInfo> docReferences = documentRepository.findDocumentReferencesByOperatorId(operatorId);

        // Package Request
        List<PackageRequestInfo> packageRequest = paymentRepository.findPackageRequestsByOperatorId(operatorId);

        response.setTotalPrintings(totalPrintings);
        response.setApprovedDocsInProgress(approvedDocs);
        response.setTotalEarnings(totalEarnings != null ? totalEarnings : BigDecimal.ZERO);
        response.setDocumentReferences(docReferences);
        response.setPackageRequest(packageRequest);
        response.setBusyTill(busyTillTime);
        response.setPrinterStatus(printerStatus);
        response.setAvailableTill(availableTillTime);

        return response;
    }

    @Transactional
    public void updatePrinterStatus(Long operatorId, String status) {
        Printer.PrinterStatus newStatus;
        try {
            newStatus = Printer.PrinterStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value. Must be AVAILABLE or NOT_AVAILABLE");
        }

        Printer printer = printerRepository.findByOperator_OperatorId(operatorId);
        if (printer == null) {
            throw new RuntimeException("Printer not found for the operator");
        }

        printer.setStatus(newStatus);
        printerRepository.save(printer);
    }

    public void updateAvailableTill(Long operatorId, String timeStr) {
        Printer printer = printerRepository.findByOperator_OperatorId(operatorId);
        if (printer == null) {
            throw new RuntimeException("Printer not found for operator: " + operatorId);
        }

        // Parse time string (e.g., "14:30")
        LocalTime time = LocalTime.parse(timeStr); // throws if format invalid

        // Combine with current date
        LocalDateTime availableTill = LocalDateTime.of(LocalDate.now(), time);

        printer.setAvailableTill(availableTill);
        printerRepository.save(printer);
    }
}
