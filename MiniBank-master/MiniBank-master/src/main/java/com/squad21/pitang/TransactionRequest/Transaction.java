package com.squad21.pitang.TransactionRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Data
@NoArgsConstructor
class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    private User owner;

    private LocalDateTime createdAt;

    public void debit(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}

@Entity
@Data
@NoArgsConstructor
class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Account source;

    @ManyToOne(optional = false)
    private Account destination;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private String status;

    public Transaction(Account source, Account destination, BigDecimal amount, String status) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.status = status;
        this.transactionDate = LocalDateTime.now();
    }
}

@Entity
@Data
@NoArgsConstructor
class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    @Column(unique = true, length = 11)
    private String cpf;

    private String endereco;

    private String email;

    private String senha;

    private LocalDateTime createdAt;
}


@Service
class TransferService {

    @PersistenceContext
    private EntityManager entityManager;

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
}


@RestController
@RequestMapping("/transferencias")
class TransferController {

    @Autowired
    private TransferService transferService;

    public record TransferRequest(
        UUID sourceAccountId,
        UUID destinationAccountId,
        BigDecimal amount
    ) {}

    @PostMapping
    public ResponseEntity<?> makeTransfer(@RequestBody TransferRequest request) {
        try {
            transferService.transfer(request.sourceAccountId(), request.destinationAccountId(), request.amount());
            return ResponseEntity.ok("{\"message\": \"Transferência concluída com sucesso.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
