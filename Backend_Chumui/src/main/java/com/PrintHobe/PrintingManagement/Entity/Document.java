package com.PrintHobe.PrintingManagement.Entity;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="documents")
public class Document {

    public enum Side{
        SINGLE_SIDED, DOUBLE_SIDED
    }

    public enum Color{
        BW, COLOR
    }

    public enum Punching{
        YES, NO
    }

    public enum Status{
        COMPLETED, REJECTED, APPROVED, VERIFYING
    }

    public enum UsedPackage{
        YES, NO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="doc_id")
    private Long docId;

    @Column(name="pages", nullable = false)
    private int pages;

    @Column(name="copies", nullable = false)
    private int copies;

    @Enumerated(EnumType.STRING)
    @Column(name="sides", nullable = false)
    private Side sides;

    @Enumerated(EnumType.STRING)
    @Column(name="color", nullable = false)
    private Color color;

    @Enumerated(EnumType.STRING)
    @Column(name="punching", nullable = false)
    private Punching punching;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private Status status;

    @Column(name="start_time")
    private LocalDateTime startTime;

    @Column(name="end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="used_package", nullable = false)
    private UsedPackage usedPackage;

    @Column(name="file_path", length = 255)
    private String filePath;

    @Column(name="original_file_name", length = 255)
    private String originalFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="printer_id", nullable = false)
    private Printer printer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="op_id", nullable = false)
    private Operator operator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", referencedColumnName = "reference_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="user_id", nullable = false)
    private User user;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Side getSides() {
        return sides;
    }

    public void setSides(Side sides) {
        this.sides = sides;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Punching getPunching() {
        return punching;
    }

    public void setPunching(Punching punching) {
        this.punching = punching;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public UsedPackage getUsedPackage() {
        return usedPackage;
    }

    public void setUsedPackage(UsedPackage usedPackage) {
        this.usedPackage = usedPackage;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
