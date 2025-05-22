package com.squad21.pitang.TransactionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transferencias")
public class TransferController {

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
            return ResponseEntity.ok().body("{\"message\": \"Transferência concluída com sucesso.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Erro interno: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{accountId}/movimentacoes")
    public ResponseEntity<?> getMovimentacoes(@PathVariable UUID accountId) {
        try {
            List<Transaction> movimentacoes = transferService.getMovimentacoes(accountId);
            return ResponseEntity.ok(movimentacoes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar movimentações: " + e.getMessage() + "\"}");
        }
    }
}