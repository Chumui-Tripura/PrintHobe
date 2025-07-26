package com.PrintHobe.PrintingManagement.Repository;

import com.PrintHobe.PrintingManagement.Entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    Optional<Operator> findByEmail(String email);
    boolean existsByEmail(String email);
}
