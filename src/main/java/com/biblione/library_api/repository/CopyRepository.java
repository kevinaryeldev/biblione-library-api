package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Copy;
import com.biblione.library_api.enums.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CopyRepository extends JpaRepository<Copy, UUID> {
    List<Copy> findByBookId(UUID bookId);
    List<Copy> findByBookIdAndStatus(UUID bookId, CopyStatus status);
    Optional<Copy> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
    long countByBookIdAndStatus(UUID bookId, CopyStatus status);
}