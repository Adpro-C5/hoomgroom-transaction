package id.ac.ui.cs.advprog.transaction.service;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Transaction transaction){ return transactionRepository.save(transaction); }

    @Override
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findAllByUserId(UUID userId){
        return transactionRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Transaction> findById(UUID transactionId){
        return transactionRepository.findById(transactionId);
    }

    @Override
    public Transaction edit(Transaction editedTransaction){
        Optional<Transaction> foundTransaction = transactionRepository.findById(editedTransaction.getTransactionId());
        Transaction transaction = foundTransaction.get();

        transaction.setProductId(editedTransaction.getProductId());
        transaction.setProductAmount(editedTransaction.getProductAmount());
        transaction.setPromoCode(editedTransaction.getPromoCode());
        transaction.setPaymentDate(editedTransaction.getPaymentDate());
        transaction.setTotalPrice(editedTransaction.getTotalPrice());

        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public void deleteById(UUID transactionId){
        transactionRepository.deleteById(transactionId);
    }
}