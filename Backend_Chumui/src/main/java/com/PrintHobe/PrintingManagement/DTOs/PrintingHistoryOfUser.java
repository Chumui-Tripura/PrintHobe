package com.PrintHobe.PrintingManagement.DTOs;

public class PrintingHistoryOfUser {
    private String date;
    private String time;
    private String fileName;
    private int numberOfPages;
    private String amount; // either "200.00" or "Package"
    private String status;
    private String color;

    public PrintingHistoryOfUser(String date, String time, String fileName, int numberOfPages, String amount, String status, String color) {
        this.date = date;
        this.time = time;
        this.fileName = fileName;
        this.numberOfPages = numberOfPages;
        this.amount = amount;
        this.status = status;
        this.color = color;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
