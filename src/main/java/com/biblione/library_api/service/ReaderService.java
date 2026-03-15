package com.biblione.library_api.service;

import com.biblione.library_api.entity.LoanPolicy;
import com.biblione.library_api.entity.Reader;
import com.biblione.library_api.exception.ReaderNotFoundException;
import com.biblione.library_api.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final LoanPolicyService loanPolicyService;

    public Page<Reader> findAll(Pageable pageable) {
        return readerRepository.findAll(pageable);
    }

    public Reader findById(UUID id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException(id.toString()));
    }

    public Reader findByEmail(String email) {
        return readerRepository.findByEmail(email)
                .orElseThrow(() -> new ReaderNotFoundException(email));
    }

    @Transactional
    public Reader create(Reader reader) {
        LoanPolicy policy = loanPolicyService.findActive();
        reader.setPolicy(policy);
        reader.setBlocked(false);
        return readerRepository.save(reader);
    }

    @Transactional
    public Reader update(UUID id, Reader updated) {
        Reader existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        existing.setRegistrationNumber(updated.getRegistrationNumber());
        return readerRepository.save(existing);
    }

    @Transactional
    public Reader block(UUID id, String reason) {
        Reader reader = findById(id);
        reader.setBlocked(true);
        reader.setBlockedReason(reason);
        return readerRepository.save(reader);
    }
    @Transactional
    public void blockReadersWithPendingFines(List<UUID> readerIds) {
        readerIds.forEach(id -> {
            Reader reader = findById(id);
            reader.setBlocked(true);
            reader.setBlockedReason("Multa pendente.");
            readerRepository.save(reader);
        });
    }
    @Transactional
    public Reader unblock(UUID id) {
        Reader reader = findById(id);
        reader.setBlocked(false);
        reader.setBlockedReason(null);
        return readerRepository.save(reader);
    }
}