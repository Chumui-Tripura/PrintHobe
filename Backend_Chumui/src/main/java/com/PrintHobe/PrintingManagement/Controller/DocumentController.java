package com.PrintHobe.PrintingManagement.Controller;

import com.PrintHobe.PrintingManagement.DTOs.*;
import com.PrintHobe.PrintingManagement.Entity.Printer;
import com.PrintHobe.PrintingManagement.Repository.PrinterRepository;
import com.PrintHobe.PrintingManagement.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private PrinterRepository printerRepository;

    @PostMapping("/upload-info")
    public ResponseEntity<DocumentUploadResponse> uploadPdfInfo (@RequestParam("file")
    MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("printerId") Long printerId, @RequestParam("color") String color, @RequestParam(value = "copies", required = false) Integer copies){
        DocumentUploadRequest request = new DocumentUploadRequest();
        Printer printer = printerRepository.findById(9L)
                .orElseThrow(() -> new RuntimeException("Printer not found"));
        request.setUserId(userId);
        request.setPrinterId(printerId);
        request.setColor(color);
        request.setCopies(copies);

        try {
            DocumentUploadResponse response = documentService.handleUpload(file, request);
            response.setTimePerPageColor(printer.getTimePerPageColor());
            response.setTimePerPageBw(printer.getTimePerPageBw());
            response.setCostColor(printer.getCostColor());
            response.setCostBw(printer.getCostBw());
            response.setAvailableTill(printer.getAvailableTill());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/upload-use-package")
    public ResponseEntity<?> uploadDocumentUsingPackage(@ModelAttribute UsePackageRequest request) {
        try {
            documentService.saveDocumentUsingPackage(request);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Document Uploaded Successfully using package.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to upload document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/submit-payment")
    public ResponseEntity<?> submitPayment(@ModelAttribute SubmitPaymentRequest request){
        try{
            documentService.submitPayment(request);
            return ResponseEntity.ok("Payment and Document Submitted successfully!!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!");
        }
    }

    @GetMapping("/ongoing/{userId}")
    public ResponseEntity<List<OngoingDocumentResponse>> getOngoingDocuments(@PathVariable Long userId){
       List<OngoingDocumentResponse> ongoingDocs = documentService.getOngoingDocumentForUser(userId);

       if(ongoingDocs.isEmpty()){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(ongoingDocs);
    }

    // when the operator clicks on the print button
    @PutMapping("/{documentId}/approve")
    public ResponseEntity<String> approveDocument(@PathVariable Long documentId) {
        try {
            documentService.approveDocument(documentId);
            return ResponseEntity.ok("Document marked as APPROVED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve document: " + e.getMessage());
        }
    }

    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "Uploaded_Files");

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = uploadDir.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // when the document printing is completed
    @PutMapping("/{documentId}/complete")
    public ResponseEntity<String> completeDocument(@PathVariable Long documentId) {
        try {
            documentService.completeDocument(documentId);
            return ResponseEntity.ok("Document marked as COMPLETED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to complete document: " + e.getMessage());
        }
    }

    // when the document printing is rejected
    @PutMapping("/{documentId}/reject")
    public ResponseEntity<String> rejectDocument(@PathVariable Long documentId) {
        try {
            documentService.rejectDocumentAndPayment(documentId);
            return ResponseEntity.ok("Document and payment rejected (if applicable)");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject: " + e.getMessage());
        }
    }

    @GetMapping("/user-printing-history/{userId}")
    public ResponseEntity<List<PrintingHistoryOfUser>> getPrintingHistory(@PathVariable Long userId) {
        List<PrintingHistoryOfUser> history = documentService.getPrintingHistoryByUserId(userId);
        return ResponseEntity.ok(history);
    }
}
