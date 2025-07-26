package com.PrintHobe.PrintingManagement.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "printers")
public class Printer {

    public enum  PrinterStatus{
        AVAILABLE, NOT_AVAILABLE
    }

    public enum HasPackage {
        YES, NO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printer_id")
    private Long printerId;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PrinterStatus status = PrinterStatus.NOT_AVAILABLE;

    @Column(name = "time_per_page_bw", nullable = false)
    private double timePerPageBw;

    @Column(name = "time_per_page_color", nullable = false)
    private double timePerPageColor;

    @Column(name = "cost_bw", precision = 4, scale = 2, nullable = false)
    private BigDecimal costBw;

    @Column(name = "busy_till")
    private LocalDateTime busyTill;

    @Column(name = "cost_color", precision = 4, scale = 2, nullable = false)
    private BigDecimal costColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "has_package", nullable = false)
    private HasPackage hasPackage = HasPackage.NO;

    @Column(name = "package_price", precision = 6, scale = 2)
    private BigDecimal packagePrice;

    @Column(name = "package_page")
    private Integer packagePage;

    @Column(name="available_till")
    private LocalDateTime availableTill;

    public LocalDateTime getAvailableTill() {
        return availableTill;
    }

    public void setAvailableTill(LocalDateTime availableTill) {
        this.availableTill = availableTill;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "op_id", nullable = false)
    private Operator operator;

    public Long getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Long printerId) {
        this.printerId = printerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PrinterStatus getStatus() {
        return status;
    }

    public void setStatus(PrinterStatus status) {
        this.status = status;
    }

    public double getTimePerPageBw() {
        return timePerPageBw;
    }

    public void setTimePerPageBw(double timePerPageBw) {
        this.timePerPageBw = timePerPageBw;
    }

    public double getTimePerPageColor() {
        return timePerPageColor;
    }

    public void setTimePerPageColor(double timePerPageColor) {
        this.timePerPageColor = timePerPageColor;
    }

    public LocalDateTime getBusyTill() {
        return busyTill;
    }

    public void setBusyTill(LocalDateTime busyTill) {
        this.busyTill = busyTill;
    }

    public BigDecimal getCostBw() {
        return costBw;
    }

    public void setCostBw(BigDecimal costBw) {
        this.costBw = costBw;
    }

    public BigDecimal getCostColor() {
        return costColor;
    }

    public void setCostColor(BigDecimal costColor) {
        this.costColor = costColor;
    }

    public  Operator getOperator(){
        return operator;
    }

    public void setOperator(Operator operator){
        this.operator = operator;
    }

    public BigDecimal getPackagePrice(){
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice){
        this.packagePrice = packagePrice;
    }

    public Integer getPackagePage() {
        return packagePage;
    }

    public void setPackagePage(Integer packagePage) {
        this.packagePage = packagePage;
    }

    public HasPackage getHasPackage() {
        return hasPackage;
    }

    public void setHasPackage(HasPackage hasPackage) {
        this.hasPackage = hasPackage;
    }
}
