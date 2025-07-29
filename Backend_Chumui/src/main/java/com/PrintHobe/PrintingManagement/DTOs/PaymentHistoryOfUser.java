package com.PrintHobe.PrintingManagement.DTOs;

import com.PrintHobe.PrintingManagement.Entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentHistoryOfUser {
    private String referenceId;
    private BigDecimal amount;
    private String date;   // e.g., 10/05/25
    private String time;   // e.g., 2:00 pm
    private Payment.PaymentFor paymentFor;
    private Payment.PaymentStatus paymentStatus;

    public PaymentHistoryOfUser(String referenceId, BigDecimal amount, LocalDateTime dateNTime,
                              Payment.PaymentFor paymentFor, Payment.PaymentStatus paymentStatus) {
        this.referenceId = referenceId;
        this.amount = amount;
        this.date = dateNTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        this.time = dateNTime.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase(); // am/pm lowercase
        this.paymentFor = paymentFor;
        this.paymentStatus = paymentStatus;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Payment.PaymentFor getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(Payment.PaymentFor paymentFor) {
        this.paymentFor = paymentFor;
    }

    public Payment.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Payment.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
