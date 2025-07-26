package com.PrintHobe.PrintingManagement.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class SubmitPaymentRequest {
    private String referenceId;
    private BigDecimal amount;
    private Long userId;
    private Long operatorId;
    private String paymentFor;

    // For Documents
    private MultipartFile file;
    private int copies;
    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private String color;
    private String sides;
    private String punching;
    private Long printerId;

    // Getters and Setters

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getSides() {
        return sides;
    }

    public void setSides(String sides) {
        this.sides = sides;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPunching() {
        return punching;
    }

    public void setPunching(String punching) {
        this.punching = punching;
    }

    public Long getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Long printerId) {
        this.printerId = printerId;
    }
}
