package com.PrintHobe.PrintingManagement.DTOs;

import com.PrintHobe.PrintingManagement.Entity.Payment;

import java.math.BigDecimal;

public class PackageRequestInfo {
    private String referenceId;
    private int packagePage;
    private BigDecimal amount;
    private Payment.PaymentStatus status;

    public PackageRequestInfo(String referenceId, int packagePage, BigDecimal amount, Payment.PaymentStatus status) {
        this.referenceId = referenceId;
        this.packagePage = packagePage;
        this.amount = amount;
        this.status = status;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public int getPackagePage() {
        return packagePage;
    }

    public void setPackagePage(int packagePage) {
        this.packagePage = packagePage;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Payment.PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(Payment.PaymentStatus status) {
        this.status = status;
    }
}
