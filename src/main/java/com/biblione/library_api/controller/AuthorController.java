package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.AuthorRequest;
import com.biblione.library_api.dto.response.AuthorResponse;
import com.biblione.library_api.mapper.AuthorMapper;
import com.biblione.library_api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    public Page<AuthorResponse> findAll(Pageable pageable) {
        return authorService.findAll(pageable).map(authorMapper::toResponse);
    }

    @GetMapping("/{id}")
    public AuthorResponse findById(@PathVariable UUID id) {
        return authorMapper.toResponse(authorService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorResponse create(@Valid @RequestBody AuthorRequest request) {
        return authorMapper.toResponse(authorService.create(authorMapper.toEntity(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorResponse update(@PathVariable UUID id, @Valid @RequestBody AuthorRequest request) {
        return authorMapper.toResponse(authorService.update(id, authorMapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) {
        authorService.delete(id);
    }
}
