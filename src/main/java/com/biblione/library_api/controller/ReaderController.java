package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.BlockReaderRequest;
import com.biblione.library_api.dto.request.ReaderRequest;
import com.biblione.library_api.dto.response.ReaderResponse;
import com.biblione.library_api.mapper.ReaderMapper;
import com.biblione.library_api.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReaderResponse> findAll(Pageable pageable) {
        return readerService.findAll(pageable).map(readerMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReaderResponse findById(@PathVariable UUID id) {
        return readerMapper.toResponse(readerService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ReaderResponse create(@Valid @RequestBody ReaderRequest request) {
        return readerMapper.toResponse(readerService.create(readerMapper.toEntity(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReaderResponse update(@PathVariable UUID id, @Valid @RequestBody ReaderRequest request) {
        return readerMapper.toResponse(readerService.update(id, readerMapper.toEntity(request)));
    }

    @PatchMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ReaderResponse block(@PathVariable UUID id, @Valid @RequestBody BlockReaderRequest request) {
        return readerMapper.toResponse(readerService.block(id, request.reason()));
    }

    @PatchMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public ReaderResponse unblock(@PathVariable UUID id) {
        return readerMapper.toResponse(readerService.unblock(id));
    }
}
