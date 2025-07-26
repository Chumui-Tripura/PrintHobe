package com.PrintHobe.PrintingManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    public enum PaymentFor{
        PACKAGE,
        PRINTING
    }

    public enum PaymentStatus {
        APPROVED, REJECTED, VERIFYING
    }

    @Id
    @Column(name="reference_id", length = 50)
    private String referenceId;

    @Column(name="amount", precision = 6, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name="date_n_time", nullable = false)
    private LocalDateTime dateNTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_for", nullable = false)
    private PaymentFor paymentFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.VERIFYING;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "op_id", nullable = false)
    private Operator operator;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateNTime() {
        return dateNTime;
    }

    public void setDateNTime(LocalDateTime dateNTime) {
        this.dateNTime = dateNTime;
    }

    public PaymentFor getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(PaymentFor paymentFor) {
        this.paymentFor = paymentFor;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
