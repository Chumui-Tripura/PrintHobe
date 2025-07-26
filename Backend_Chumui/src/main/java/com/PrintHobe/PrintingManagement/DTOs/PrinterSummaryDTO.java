package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;

public class PrinterSummaryDTO {
    private String printerName;
    private BigDecimal colorCost;
    private BigDecimal blackWhiteCost;
    private BigDecimal packagePrice;
    private Integer packagePages;
    private boolean available;
    private String operatorPhoneNumber;

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public BigDecimal getColorCost() {
        return colorCost;
    }

    public void setColorCost(BigDecimal colorCost) {
        this.colorCost = colorCost;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public BigDecimal getBlackWhiteCost() {
        return blackWhiteCost;
    }

    public void setBlackWhiteCost(BigDecimal blackWhiteCost) {
        this.blackWhiteCost = blackWhiteCost;
    }

    public Integer getPackagePages() {
        return packagePages;
    }

    public void setPackagePages(Integer packagePages) {
        this.packagePages = packagePages;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getOperatorPhoneNumber() {
        return operatorPhoneNumber;
    }

    public void setOperatorPhoneNumber(String operatorPhoneNumber) {
        this.operatorPhoneNumber = operatorPhoneNumber;
    }
}
