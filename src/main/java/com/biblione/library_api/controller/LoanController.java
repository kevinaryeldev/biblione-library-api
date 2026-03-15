package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.LoanRequest;
import com.biblione.library_api.dto.response.LoanResponse;
import com.biblione.library_api.mapper.LoanMapper;
import com.biblione.library_api.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<LoanResponse> findAll(Pageable pageable) {
        return loanService.findAll(pageable).map(loanMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse findById(@PathVariable UUID id) {
        return loanMapper.toResponse(loanService.findById(id));
    }

    @GetMapping("/reader/{readerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public List<LoanResponse> findActiveByReader(@PathVariable UUID readerId) {
        return loanService.findActiveByReader(readerId).stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse create(@Valid @RequestBody LoanRequest request, Authentication authentication) {
        UUID registeredBy = UUID.fromString(authentication.getName());
        return loanMapper.toResponse(loanService.create(request.bookId(), request.readerId(), registeredBy));
    }

    @PatchMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse returnLoan(@PathVariable UUID id) {
        return loanMapper.toResponse(loanService.returnLoan(id));
    }

    @PatchMapping("/{id}/renew")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public LoanResponse renew(@PathVariable UUID id) {
        return loanMapper.toResponse(loanService.renew(id));
    }
}
