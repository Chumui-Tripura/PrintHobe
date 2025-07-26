package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OngoingDocumentResponse {
    private String documentName;
    private int pages;
    private String type;
    private BigDecimal cost;
    private LocalDateTime dateTime;
    private String status;

    private OngoingDocumentResponse(){}

    public OngoingDocumentResponse(String documentName, int pages, String type, BigDecimal cost, LocalDateTime dateTime, String status) {
        this.documentName = documentName;
        this.pages = pages;
        this.type = type;
        this.cost = cost;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
