package id.ac.ui.cs.advprog.transaction.service;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    Transaction create(Transaction transaction, String jwtToken);
    List<Transaction> findAll();
    List<Transaction> findAllByUserId(Integer userId);
    Optional<Transaction> findById(UUID transactionId);
    Transaction edit(Transaction transaction);
    void deleteById(UUID transactionId);
    void createShipment(UUID transactionId);
}