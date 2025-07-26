package com.PrintHobe.PrintingManagement.Repository;

import com.PrintHobe.PrintingManagement.Entity.Payment;
import com.PrintHobe.PrintingManagement.DTOs.PackageRequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    // This is for the total earning of the printer operator
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.operator.operatorId = :operatorId AND p.paymentStatus = com.PrintHobe.PrintingManagement.Entity.Payment.PaymentStatus.APPROVED")
    BigDecimal getTotalEarningsByOperatorId(@Param("operatorId") Long operatorId);

    @Query("SELECT new com.PrintHobe.PrintingManagement.DTOs.PackageRequestInfo(p.referenceId, u.packagePage, p.amount, p.paymentStatus) " +
            "FROM Payment p JOIN p.user u " +
            "WHERE p.operator.operatorId = :opId AND p.paymentFor = 'PACKAGE' AND p.paymentStatus = 'VERIFYING'")
    List<PackageRequestInfo> findPackageRequestsByOperatorId(@Param("opId") Long opId);


    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.operator.operatorId = :operatorId AND p.paymentStatus = com.PrintHobe.PrintingManagement.Entity.Payment.PaymentStatus.APPROVED")
    BigDecimal sumAmountByOperatorIdAndApprovedStatus(@Param("operatorId") Long operatorId);

}
