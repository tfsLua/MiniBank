package com.squad21.pitang.TransactionRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        Account sourceAccount = entityManager.find(Account.class, sourceAccountId);
        Account destinationAccount = entityManager.find(Account.class, destinationAccountId);

        if (sourceAccount == null || destinationAccount == null) {
            throw new IllegalArgumentException("Uma ou ambas as contas não foram encontradas.");
        }

        sourceAccount.debit(amount);
        destinationAccount.credit(amount);

        Transaction transaction = new Transaction(sourceAccount, destinationAccount, amount, "CONCLUÍDO");
        entityManager.persist(transaction);

        entityManager.merge(sourceAccount);
        entityManager.merge(destinationAccount);
    }

    public List<Transaction> getMovimentacoes(UUID accountId) {
        return transactionRepository.findBySourceIdOrDestinationId(accountId, accountId);
    }
}