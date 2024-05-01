package id.ac.ui.cs.advprog.transaction.service;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    Transaction create(Transaction transaction);
    List<Transaction> findAll();
    List<Transaction> findAllByUserId(UUID userId);
    Optional<Transaction> findById(UUID transactionId);
    Transaction edit(Transaction transaction);
    void deleteById(UUID transactionId);
}