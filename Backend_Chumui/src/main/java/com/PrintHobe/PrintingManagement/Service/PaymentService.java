package com.PrintHobe.PrintingManagement.Service;

import com.PrintHobe.PrintingManagement.DTOs.PaymentHistoryOfUser;
import com.PrintHobe.PrintingManagement.DTOs.PaymentRequest;
import com.PrintHobe.PrintingManagement.Entity.Operator;
import com.PrintHobe.PrintingManagement.Entity.Payment;
import com.PrintHobe.PrintingManagement.Entity.Printer;
import com.PrintHobe.PrintingManagement.Entity.User;
import com.PrintHobe.PrintingManagement.Repository.OperatorRepository;
import com.PrintHobe.PrintingManagement.Repository.PaymentRepository;
import com.PrintHobe.PrintingManagement.Repository.PrinterRepository;
import com.PrintHobe.PrintingManagement.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private PrinterRepository printerRepository;

    // For front end in the user dashboard
    public List<PaymentHistoryOfUser> getPaymentHistoryByUserId(Long userId) {
        return paymentRepository.findPaymentsByUserId(userId);
    }

    public Payment processPayment(PaymentRequest request){
        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new RuntimeException("User not found!"));

        Operator operator = operatorRepository.findById(request.getOperatorId()).orElseThrow(()-> new RuntimeException("Operator Not Found!"));

        // Creating Payment
        Payment payment = new Payment();
        payment.setReferenceId(request.getReferenceId());
        payment.setAmount(request.getAmount());
        payment.setDateNTime(LocalDateTime.now());
        payment.setPaymentFor(Payment.PaymentFor.valueOf(request.getPaymentFor().toUpperCase()));
        payment.setUser(user);
        payment.setOperator(operator);
        payment.setPaymentStatus(Payment.PaymentStatus.VERIFYING);
        return paymentRepository.save(payment);
    }

    @Transactional
    public void updatePackageStatus(String referenceId, String status) {
        Payment payment = paymentRepository.findById(referenceId)
                .orElseThrow(() -> new RuntimeException("Payment not found for reference ID: " + referenceId));

        Payment.PaymentStatus newStatus = Payment.PaymentStatus.valueOf(status.toUpperCase());
        payment.setPaymentStatus(newStatus);

        // Save status update
        paymentRepository.save(payment);

        if (newStatus == Payment.PaymentStatus.APPROVED) {
            User user = payment.getUser();
            Operator operator = payment.getOperator();
            Printer printer = printerRepository.findByOperator_OperatorId(operator.getOperatorId());

            if (printer == null) {
                throw new RuntimeException("Printer not found for operator.");
            }

            int extraPages = printer.getPackagePage();
            int currentPages = user.getPackagePage();

            user.setPackagePage(currentPages + extraPages);

            userRepository.save(user);
        }
    }
}
