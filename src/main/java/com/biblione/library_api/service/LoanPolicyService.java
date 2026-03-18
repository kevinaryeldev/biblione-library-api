package com.biblione.library_api.service;

import com.biblione.library_api.entity.LoanPolicy;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.repository.LoanPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.hc.core5.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class LoanPolicyService {

    private final LoanPolicyRepository loanPolicyRepository;

    public LoanPolicy findActive() {
        return loanPolicyRepository.findByActiveTrue()
                .orElseThrow(() -> new BusinessException(
                        "Nenhuma política de empréstimo ativa encontrada.",
                        SC_INTERNAL_SERVER_ERROR));
    }

    @Transactional
    public LoanPolicy update(LoanPolicy updated) {
        LoanPolicy existing = findActive();
        existing.setName(updated.getName());
        existing.setLoanDays(updated.getLoanDays());
        existing.setMaxRenewals(updated.getMaxRenewals());
        existing.setMaxSimultaneous(updated.getMaxSimultaneous());
        existing.setFinePerDay(updated.getFinePerDay());
        existing.setReservationHoldDays(updated.getReservationHoldDays());
        return loanPolicyRepository.save(existing);
    }
}