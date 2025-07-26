package com.PrintHobe.PrintingManagement.Repository;

import com.PrintHobe.PrintingManagement.DTOs.DocumentReferenceInfo;
import com.PrintHobe.PrintingManagement.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserUserIdAndStatusNotIn(Long userId, List<Document.Status> excludedStatuses);

    long countByPrinter_PrinterIdAndStatus(Long printerId, Document.Status status);

    List<Document> findByPrinter_PrinterIdAndStatus(Long printerId, Document.Status status);

    @Query("SELECT new com.PrintHobe.PrintingManagement.DTOs.DocumentReferenceInfo(" +
            "d.docId, " +
            "CASE WHEN d.payment IS NULL THEN 'Used Package' ELSE d.payment.referenceId END, " +
            "d.color, d.filePath, d.originalFileName, d.copies) " +
            "FROM Document d WHERE d.operator.operatorId = :operatorId")
    List<DocumentReferenceInfo> findDocumentReferencesByOperatorId(@Param("operatorId") Long operatorId);

    long countByOperator_OperatorIdAndStatus(Long operatorId, Document.Status status);
}
