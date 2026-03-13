package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, UUID> {
    Optional<Reader> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Reader> findByRegistrationNumber(String registrationNumber);
}