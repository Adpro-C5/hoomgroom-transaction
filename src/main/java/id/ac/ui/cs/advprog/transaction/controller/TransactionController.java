package id.ac.ui.cs.advprog.transaction.controller;

import id.ac.ui.cs.advprog.transaction.auth.AuthHelper;
import id.ac.ui.cs.advprog.transaction.dto.ProfileDTO;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.service.TransactionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController{
    private final TransactionService transactionService;
    private final AuthHelper authHelper;

    public TransactionController(TransactionService transactionService, AuthHelper authHelper){
        this.transactionService= transactionService;
        this.authHelper = authHelper;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllTransactions(@RequestHeader("Authorization") String jwtToken){

        // authorize
        String userRole = authHelper.getUserRole(jwtToken);
        if (userRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }
        if (! userRole.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non admin users cannot view this page");
        }

        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findAllTransactionsByUserId(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String jwtToken){
        try{

            // authorize
            ProfileDTO userProfile = authHelper.getUserProfile(jwtToken);
            if(userId != userProfile.getId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID doesn't match the currently logged in user");
            }

            List<Transaction> transactions = transactionService.findAllByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTransactionById(@PathVariable("id") String transactionId, @RequestHeader("Authorization") String jwtToken){
        try{
            Optional<Transaction> transaction = transactionService.findById(UUID.fromString(transactionId));
            if (transaction.isEmpty()) throw new IllegalArgumentException("Transaction with ID " + transactionId + " not found");

            // authorize
            Transaction foundTransaction = transaction.get();
            ProfileDTO userProfile = authHelper.getUserProfile(jwtToken);
            if(foundTransaction.getUserId() != userProfile.getId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This transaction isn't owned by the currently logged in user");
            }

            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction, @RequestHeader("Authorization") String jwtToken){
        try{

            // authorize
            ProfileDTO userProfile = authHelper.getUserProfile(jwtToken);
            if (userProfile.getId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
            if(transaction.getUserId() != userProfile.getId()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Transaction's user ID doesn't match the currently logged in user");
            }

            Transaction resultTransaction = transactionService.create(transaction, jwtToken);
            transactionService.createShipment(resultTransaction.getTransactionId());
            return ResponseEntity.status(HttpStatus.OK).body(resultTransaction);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editTransaction(@RequestBody Transaction editedTransaction, @RequestHeader("Authorization") String jwtToken){
        try{
            // authorize
            String userRole = authHelper.getUserRole(jwtToken);
            if (userRole == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
            if (! userRole.equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non admin users cannot execute this operation");
            }

            Transaction resultTransaction = transactionService.edit(editedTransaction);
            return ResponseEntity.status(HttpStatus.OK).body(resultTransaction);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable("id") UUID transactionId, @RequestHeader("Authorization") String jwtToken){
        try{

            // authorize
            String userRole = authHelper.getUserRole(jwtToken);
            if (userRole == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
            }
            if (! userRole.equals("ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non admin users cannot execute this operation");
            }

            transactionService.deleteById(transactionId);
            return ResponseEntity.status(HttpStatus.OK).body("Transaction Deleted");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}