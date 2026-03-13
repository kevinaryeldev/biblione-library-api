package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FineRepository extends JpaRepository<Fine, UUID> {
    List<Fine> findByReaderId(UUID readerId);

    @Query("SELECT f FROM Fine f WHERE f.reader.id = :readerId AND f.paidAt IS NULL AND f.waivedAt IS NULL")
    List<Fine> findPendingByReaderId(UUID readerId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Fine f WHERE f.reader.id = :readerId AND f.paidAt IS NULL AND f.waivedAt IS NULL")
    boolean hasPendingFines(UUID readerId);
}