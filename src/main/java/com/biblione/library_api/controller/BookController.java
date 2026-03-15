package com.biblione.library_api.controller;

import com.biblione.library_api.dto.request.AddCopyRequest;
import com.biblione.library_api.dto.request.BookRequest;
import com.biblione.library_api.dto.request.DiscardCopyRequest;
import com.biblione.library_api.dto.response.BookResponse;
import com.biblione.library_api.dto.response.CopyResponse;
import com.biblione.library_api.mapper.BookMapper;
import com.biblione.library_api.mapper.CopyMapper;
import com.biblione.library_api.service.BookService;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final CopyMapper copyMapper;

    @GetMapping
    public Page<BookResponse> findAll(Pageable pageable) {
        return bookService.findAll(pageable).map(bookMapper::toResponse);
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable UUID id) {
        return bookMapper.toResponse(bookService.findById(id));
    }

    @GetMapping("/search")
    public List<BookResponse> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title).stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        return bookMapper.toResponse(
                bookService.create(bookMapper.toEntity(request), request.authorIds(), request.categoryIds())
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse update(@PathVariable UUID id, @Valid @RequestBody BookRequest request) {
        return bookMapper.toResponse(
                bookService.update(id, bookMapper.toEntity(request), request.authorIds(), request.categoryIds())
        );
    }

    @PostMapping("/{id}/copies")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CopyResponse addCopy(@PathVariable UUID id,
                                @Valid @RequestBody AddCopyRequest request,
                                Authentication authentication) {
        UUID registeredBy = UUID.fromString(authentication.getName());
        return copyMapper.toResponse(
                bookService.addCopy(id, request.barcode(), request.acquisitionDate(), registeredBy)
        );
    }

    @PatchMapping("/copies/{copyId}/discard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void discardCopy(@PathVariable UUID copyId,
                            @Valid @RequestBody DiscardCopyRequest request,
                            Authentication authentication) {
        UUID registeredBy = UUID.fromString(authentication.getName());
        bookService.discardCopy(copyId, request.reason(), registeredBy);
    }
}
