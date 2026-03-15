package com.biblione.library_api.service;

import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import com.biblione.library_api.entity.Book;
import com.biblione.library_api.entity.Copy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.biblione.library_api.enums.CopyStatus;
import com.biblione.library_api.kafka.event.CopyEventData;
import com.biblione.library_api.repository.BookRepository;
import com.biblione.library_api.repository.CopyRepository;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.exception.BookNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.biblione.library_api.kafka.producer.LibraryEventProducer;

import static org.apache.hc.core5.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final LibraryEventProducer eventProducer;

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public Book create(Book book, Set<UUID> authorIds, Set<UUID> categoryIds) {
        if (book.getIsbn13() != null && bookRepository.existsByIsbn13(book.getIsbn13())) {
            throw new BusinessException(
                    "ISBN já cadastrado: " + book.getIsbn13(),
                    SC_CONFLICT);
        }

        authorIds.forEach(id -> book.getAuthors().add(authorService.findById(id)));
        categoryIds.forEach(id -> book.getCategories().add(categoryService.findById(id)));

        return bookRepository.save(book);
    }

    @Transactional
    public Book update(UUID id, Book updated, Set<UUID> authorIds, Set<UUID> categoryIds) {
        Book existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setSubtitle(updated.getSubtitle());
        existing.setIsbn13(updated.getIsbn13());
        existing.setPublisher(updated.getPublisher());
        existing.setEdition(updated.getEdition());
        existing.setPublishYear(updated.getPublishYear());
        existing.setLanguage(updated.getLanguage());
        existing.setPages(updated.getPages());
        existing.setSynopsis(updated.getSynopsis());
        existing.setCoverUrl(updated.getCoverUrl());

        existing.getAuthors().clear();
        authorIds.forEach(aid -> existing.getAuthors().add(authorService.findById(aid)));

        existing.getCategories().clear();
        categoryIds.forEach(cid -> existing.getCategories().add(categoryService.findById(cid)));

        return bookRepository.save(existing);
    }

    @Transactional
    public Copy addCopy(UUID bookId, String barcode, LocalDate acquisitionDate, UUID registeredBy) {
        Book book = findById(bookId);

        if (copyRepository.existsByBarcode(barcode)) {
            throw new BusinessException(
                    "Código de barras já cadastrado: " + barcode,
                    SC_CONFLICT);
        }

        Copy copy = Copy.builder()
                .book(book)
                .barcode(barcode)
                .status(CopyStatus.AVAILABLE)
                .acquisitionDate(acquisitionDate)
                .build();

        copyRepository.save(copy);

        eventProducer.publishCopyAdded(CopyEventData.builder()
                .copyId(copy.getId())
                .bookId(book.getId())
                .bookTitle(book.getTitle())
                .barcode(barcode)
                .registeredBy(registeredBy)
                .acquisitionDate(acquisitionDate.atStartOfDay().atOffset(java.time.ZoneOffset.UTC))
                .build());

        return copy;
    }

    @Transactional
    public void discardCopy(UUID copyId, String reason, UUID registeredBy) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new BusinessException(
                        "Exemplar não encontrado: " + copyId,
                        SC_NOT_FOUND));

        if (copy.getStatus() == CopyStatus.LOANED) {
            throw new BusinessException(
                    "Exemplar não pode ser descartado pois está emprestado.",
                    SC_UNPROCESSABLE_CONTENT);
        }

        copy.setStatus(CopyStatus.DISCARDED);
        copy.setDiscardDate(LocalDate.now());
        copy.setDiscardReason(reason);
        copyRepository.save(copy);
        eventProducer.publishCopyDiscarded(CopyEventData.builder()
                .copyId(copy.getId())
                .bookId(copy.getBook().getId())
                .bookTitle(copy.getBook().getTitle())
                .barcode(copy.getBarcode())
                .discardReason(reason)
                .registeredBy(registeredBy)
                .build());
    }
}