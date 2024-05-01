package id.ac.ui.cs.advprog.transaction.controller;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController{
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService= transactionService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllTransactions(){
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findAllTransactionsByUserId(@PathVariable("userId") UUID userId){
        try{
            List<Transaction> transactions = transactionService.findAllByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTransactionById(@PathVariable("id") String transactionId){
        try{
            Transaction transaction = transactionService.findById(UUID.fromString(transactionId)).get();
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction){
        try{
            // nanti akan dibuat handler
            Transaction createdTransaction = new Transaction(
                    transaction.getUserId(),
                    transaction.getProductId(),
                    transaction.getProductAmount(),
                    transaction.getPromoCode()
            );

            Transaction resultTransaction = transactionService.create(createdTransaction);

            return ResponseEntity.status(HttpStatus.OK).body(resultTransaction);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editTransaction(@RequestBody Transaction editedTransaction){
        try{
            Transaction resultTransaction = transactionService.edit(editedTransaction);
            return ResponseEntity.status(HttpStatus.OK).body(resultTransaction);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable("id") UUID transactionId){
        try{
            transactionService.deleteById(transactionId);
            return ResponseEntity.status(HttpStatus.OK).body("Transaction Deleted");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}