package com.PrintHobe.PrintingManagement.Controller;

import com.PrintHobe.PrintingManagement.DTOs.PaymentHistoryOfUser;
import com.PrintHobe.PrintingManagement.DTOs.PaymentRequest;
import com.PrintHobe.PrintingManagement.Entity.Payment;
import com.PrintHobe.PrintingManagement.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make")
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentRequest request){
        System.out.println("Received payment: " + request);
        Payment savedPayment = paymentService.processPayment(request);
        return ResponseEntity.ok(savedPayment);
    }

    @PutMapping("/package/{referenceId}/status")
    public ResponseEntity<String> updatePackageStatus(@PathVariable String referenceId,
                                                      @RequestParam("status") String status) {
        try {
            paymentService.updatePackageStatus(referenceId, status);
            return ResponseEntity.ok("Package status updated to " + status);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user-payment-history/{userId}")
    public ResponseEntity<List<PaymentHistoryOfUser>> getPaymentHistory(@PathVariable Long userId) {
        List<PaymentHistoryOfUser> paymentHistory = paymentService.getPaymentHistoryByUserId(userId);
        return ResponseEntity.ok(paymentHistory);
    }
}
