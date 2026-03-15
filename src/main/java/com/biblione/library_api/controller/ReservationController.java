package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.ReservationRequest;
import com.biblione.library_api.dto.response.ReservationResponse;
import com.biblione.library_api.mapper.ReservationMapper;
import com.biblione.library_api.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @GetMapping("/reader/{readerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public List<ReservationResponse> findByReader(@PathVariable UUID readerId) {
        return reservationService.findByReader(readerId).stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public ReservationResponse findById(@PathVariable UUID id) {
        return reservationMapper.toResponse(reservationService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return reservationMapper.toResponse(
                reservationService.create(request.bookId(), request.readerId())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'READER')")
    public void cancel(@PathVariable UUID id) {
        reservationService.cancel(id);
    }
}
