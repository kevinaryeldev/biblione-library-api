package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.CategoryRequest;
import com.biblione.library_api.dto.response.CategoryResponse;
import com.biblione.library_api.entity.Category;
import com.biblione.library_api.mapper.CategoryMapper;
import com.biblione.library_api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryService.findAll(pageable).map(categoryMapper::toResponse);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable UUID id) {
        return categoryMapper.toResponse(categoryService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        if (request.parentId() != null) {
            category.setParent(categoryService.findById(request.parentId()));
        }
        return categoryMapper.toResponse(categoryService.create(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse update(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
        Category updated = categoryMapper.toEntity(request);
        if (request.parentId() != null) {
            updated.setParent(categoryService.findById(request.parentId()));
        }
        return categoryMapper.toResponse(categoryService.update(id, updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) {
        categoryService.delete(id);
    }
}
