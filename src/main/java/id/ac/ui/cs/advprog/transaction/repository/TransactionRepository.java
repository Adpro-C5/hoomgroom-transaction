package id.ac.ui.cs.advprog.transaction.repository;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransactionRepository{
    private List<Transaction> transactionData = new ArrayList<>();

    public Transaction create(Transaction transaction){
        transactionData.add(transaction);
        return transaction;
    }

    public Iterator<Transaction> findAll(){
        return transactionData.iterator();
    }

    public Transaction findById(UUID transactionId){
        for(Transaction transaction : transactionData){
            if(transaction.getId().equals(transactionId)){
                return transaction;
            }
        }
        throw new NoSuchElementException("Product not found");
    }

    public void edit(Transaction editedTransaction){
        Transaction transaction = findById(editedTransaction.getId());

        transaction.setProductId(editedTransaction.getProductId());
        transaction.setProductAmount(editedTransaction.getProductAmount());
        transaction.setPromoCode(editedTransaction.getPromoCode());
        transaction.setPaymentDate(editedTransaction.getPaymentDate());
        transaction.setTotalPrice(editedTransaction.getTotalPrice());
    }

    public void delete(UUID transactionId ){
        Transaction deletedTransaction = findById(transactionId);
        transactionData.remove(deletedTransaction);
    }
}