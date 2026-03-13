package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByFullNameContainingIgnoreCase(String name);
}