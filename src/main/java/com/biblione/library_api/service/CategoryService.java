package com.biblione.library_api.service;

import com.biblione.library_api.entity.Category;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Categoria não encontrada: " + id,
                        HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new BusinessException(
                    "Categoria já existe: " + category.getName(),
                    HttpStatus.CONFLICT);
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(UUID id, Category updated) {
        Category existing = findById(id);
        existing.setName(updated.getName());
        existing.setParent(updated.getParent());
        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}