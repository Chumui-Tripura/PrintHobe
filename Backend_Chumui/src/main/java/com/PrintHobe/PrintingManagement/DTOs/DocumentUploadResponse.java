package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DocumentUploadResponse {
    private int pages;
    private BigDecimal amount;
    private String originalFileName;
    private boolean canUsePackage;
    private LocalDateTime estimatedTime;
    private double timePerPageColor;
    private double timePerPageBw;
    private BigDecimal costColor;
    private BigDecimal costBw;
    private LocalDateTime availableTill;



    public DocumentUploadResponse(){

    }

    public DocumentUploadResponse(int pages, BigDecimal amount, String originalFileName, boolean canUsePackage, LocalDateTime estimatedTime) {
        this.pages = pages;
        this.amount = amount;
        this.originalFileName = originalFileName;
        this.canUsePackage = canUsePackage;
        this.estimatedTime = estimatedTime;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isCanUsePackage() {
        return canUsePackage;
    }

    public void setCanUsePackage(boolean canUsePackage) {
        this.canUsePackage = canUsePackage;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public LocalDateTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalDateTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getTimePerPageColor() {
        return timePerPageColor;
    }

    public void setTimePerPageColor(double timePerPageColor) {
        this.timePerPageColor = timePerPageColor;
    }

    public double getTimePerPageBw() {
        return timePerPageBw;
    }

    public void setTimePerPageBw(double timePerPageBw) {
        this.timePerPageBw = timePerPageBw;
    }

    public BigDecimal getCostColor() {
        return costColor;
    }

    public void setCostColor(BigDecimal costColor) {
        this.costColor = costColor;
    }

    public BigDecimal getCostBw() {
        return costBw;
    }

    public void setCostBw(BigDecimal costBw) {
        this.costBw = costBw;
    }

    public LocalDateTime getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(LocalDateTime availableTill) {
        this.availableTill = availableTill;
    }
}
