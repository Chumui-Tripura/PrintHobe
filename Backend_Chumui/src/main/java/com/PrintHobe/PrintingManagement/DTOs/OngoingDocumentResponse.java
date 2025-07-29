package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OngoingDocumentResponse {
    private String documentName;
    private int pages;
    private String type;
    private BigDecimal cost;
    private String date;
    private String time;
    private String status;

    private OngoingDocumentResponse(){}

    public OngoingDocumentResponse(String documentName, int pages, String type, BigDecimal cost, String date, String time, String status) {
        this.documentName = documentName;
        this.pages = pages;
        this.type = type;
        this.cost = cost;
        this.date = date;
        this.time = time;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
