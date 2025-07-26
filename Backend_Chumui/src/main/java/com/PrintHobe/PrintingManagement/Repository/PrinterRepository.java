package com.PrintHobe.PrintingManagement.Repository;

import com.PrintHobe.PrintingManagement.Entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {
    Printer findByOperator_OperatorId(Long operatorId);
}
