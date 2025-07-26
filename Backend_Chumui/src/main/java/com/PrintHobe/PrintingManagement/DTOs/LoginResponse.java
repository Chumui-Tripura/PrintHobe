package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;

public class LoginResponse {

    // User Information
    private Long userId;
    private String role;
    private String message;
    private String firstName;
    private int availablePackagePages;


    // Printer Information
    private boolean printerAvailable;
    private BigDecimal costPerPageBW;
    private BigDecimal getCostPerPageColor;

    // Default constructor
    public LoginResponse(){}

    public LoginResponse(Long userId, String role, String message, String firstName, boolean printerAvailable, int availablePackagePages, BigDecimal costPerPageBW, BigDecimal getCostPerPageColor) {
        this.userId = userId;
        this.role = role;
        this.message = message;
        this.firstName = firstName;
        this.printerAvailable = printerAvailable;
        this.availablePackagePages = availablePackagePages;
        this.costPerPageBW = costPerPageBW;
        this.getCostPerPageColor = getCostPerPageColor;
    }

    // Getters and Setters ALT+INSERT

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isPrinterAvailable() {
        return printerAvailable;
    }

    public void setPrinterAvailable(boolean printerAvailable) {
        this.printerAvailable = printerAvailable;
    }

    public int getAvailablePackagePages() {
        return availablePackagePages;
    }

    public void setAvailablePackagePages(int availablePackagePages) {
        this.availablePackagePages = availablePackagePages;
    }

    public BigDecimal getCostPerPageBW() {
        return costPerPageBW;
    }

    public void setCostPerPageBW(BigDecimal costPerPageBW) {
        this.costPerPageBW = costPerPageBW;
    }

    public BigDecimal getGetCostPerPageColor() {
        return getCostPerPageColor;
    }

    public void setGetCostPerPageColor(BigDecimal getCostPerPageColor) {
        this.getCostPerPageColor = getCostPerPageColor;
    }
}
