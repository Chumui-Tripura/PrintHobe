package com.PrintHobe.PrintingManagement.DTOs;

import com.PrintHobe.PrintingManagement.Entity.Printer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OperatorDashboardResponse {
    private long totalPrintings;
    private long approvedDocsInProgress;
    private BigDecimal totalEarnings;
    private String busyTill;
    private Printer.PrinterStatus printerStatus;
    private String availableTill;

    public String getBusyTill() {
        return busyTill;
    }

    public void setBusyTill(String busyTill) {
        this.busyTill = busyTill;
    }

    private List<DocumentReferenceInfo> documentReferences;
    private List<PackageRequestInfo> packageRequest;

    public long getTotalPrintings() {
        return totalPrintings;
    }

    public void setTotalPrintings(long totalPrintings) {
        this.totalPrintings = totalPrintings;
    }

    public long getApprovedDocsInProgress() {
        return approvedDocsInProgress;
    }

    public void setApprovedDocsInProgress(long approvedDocsInProgress) {
        this.approvedDocsInProgress = approvedDocsInProgress;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public List<DocumentReferenceInfo> getDocumentReferences() {
        return documentReferences;
    }

    public void setDocumentReferences(List<DocumentReferenceInfo> documentReferences) {
        this.documentReferences = documentReferences;
    }

    public List<PackageRequestInfo> getPackageRequest() {
        return packageRequest;
    }

    public void setPackageRequest(List<PackageRequestInfo> packageRequest) {
        this.packageRequest = packageRequest;
    }

    public Printer.PrinterStatus getPrinterStatus() {
        return printerStatus;
    }

    public void setPrinterStatus(Printer.PrinterStatus printerStatus) {
        this.printerStatus = printerStatus;
    }

    public String getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(String availableTill) {
        this.availableTill = availableTill;
    }
}
