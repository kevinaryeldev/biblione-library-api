package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.LoanPolicyRequest;
import com.biblione.library_api.dto.response.LoanPolicyResponse;
import com.biblione.library_api.mapper.LoanPolicyMapper;
import com.biblione.library_api.service.LoanPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-policy")
@RequiredArgsConstructor
public class LoanPolicyController {

    private final LoanPolicyService loanPolicyService;
    private final LoanPolicyMapper loanPolicyMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public LoanPolicyResponse getActive() {
        return loanPolicyMapper.toResponse(loanPolicyService.findActive());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LoanPolicyResponse update(@Valid @RequestBody LoanPolicyRequest request) {
        return loanPolicyMapper.toResponse(
                loanPolicyService.update(loanPolicyMapper.toEntity(request))
        );
    }
}
