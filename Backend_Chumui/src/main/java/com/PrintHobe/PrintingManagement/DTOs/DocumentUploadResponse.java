package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DocumentUploadResponse {
    private int pages;
    private BigDecimal amount;
    private String originalFileName;
    private boolean canUsePackage;
    private LocalDateTime estimatedTime;


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
}
