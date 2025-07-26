package com.PrintHobe.PrintingManagement.DTOs;

import org.springframework.web.multipart.MultipartFile;

public class DocumentUploadRequest {
    private Long userId;
    private Long printerId;
    private String color;
    private Integer copies = 1; // default

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Long printerId) {
        this.printerId = printerId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
}
