package com.PrintHobe.PrintingManagement.Controller;

import com.PrintHobe.PrintingManagement.DTOs.*;
import com.PrintHobe.PrintingManagement.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload-info")
    public ResponseEntity<DocumentUploadResponse> uploadPdfInfo (@RequestParam("file")
    MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("printerId") Long printerId, @RequestParam("color") String color, @RequestParam(value = "copies", required = false) Integer copies){
        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setUserId(userId);
        request.setPrinterId(printerId);
        request.setColor(color);
        request.setCopies(copies);

        try {
            DocumentUploadResponse response = documentService.handleUpload(file, request);
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
}
