package com.biblione.library_api.repository;

import com.biblione.library_api.entity.LoanPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanPolicyRepository extends JpaRepository<LoanPolicy, UUID> {
    Optional<LoanPolicy> findByActiveTrue();
}