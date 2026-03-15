package com.biblione.library_api.controller;

import com.biblione.library_api.dto.response.FineResponse;
import com.biblione.library_api.mapper.FineMapper;
import com.biblione.library_api.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;
    private final FineMapper fineMapper;

    @GetMapping("/reader/{readerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public List<FineResponse> findByReader(@PathVariable UUID readerId) {
        return fineService.findByReader(readerId).stream()
                .map(fineMapper::toResponse)
                .toList();
    }

    @GetMapping("/reader/{readerId}/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public List<FineResponse> findPendingByReader(@PathVariable UUID readerId) {
        return fineService.findPendingByReader(readerId).stream()
                .map(fineMapper::toResponse)
                .toList();
    }

    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasRole('ADMIN')")
    public FineResponse pay(@PathVariable UUID id) {
        return fineMapper.toResponse(fineService.payFine(id));
    }

    @PatchMapping("/{id}/waive")
    @PreAuthorize("hasRole('ADMIN')")
    public FineResponse waive(@PathVariable UUID id) {
        return fineMapper.toResponse(fineService.waiveFine(id));
    }
}
