package com.biblione.library_api.service;

import com.biblione.library_api.entity.Author;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author findById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Autor não encontrado: " + id,
                        SC_NOT_FOUND));
    }

    @Transactional
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author update(UUID id, Author updated) {
        Author existing = findById(id);
        existing.setFullName(updated.getFullName());
        existing.setBio(updated.getBio());
        existing.setNationality(updated.getNationality());
        return authorRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Author author = findById(id);
        authorRepository.delete(author);
    }
}