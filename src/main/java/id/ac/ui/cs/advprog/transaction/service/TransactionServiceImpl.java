package id.ac.ui.cs.advprog.transaction.service;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.repository.TransactionRepository;
import id.ac.ui.cs.advprog.transaction.handler.AuthCheckHandler;
import id.ac.ui.cs.advprog.transaction.handler.TotalPriceHandler;
import id.ac.ui.cs.advprog.transaction.handler.CouponHandler;
import id.ac.ui.cs.advprog.transaction.handler.BalanceCheckHandler;
import id.ac.ui.cs.advprog.transaction.dto.ShipmentDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    private final AuthCheckHandler authCheckHandler;
    private final RestTemplate restTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, RestTemplate restTemplate, AuthCheckHandler authCheckHandler){
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
        this. authCheckHandler = authCheckHandler;

        // set next handlers
        TotalPriceHandler totalPriceHandler = new TotalPriceHandler(restTemplate);
        CouponHandler couponHandler = new CouponHandler(restTemplate);
        BalanceCheckHandler balanceCheckHandler = new BalanceCheckHandler();

        this.authCheckHandler.setNextHandler(totalPriceHandler);
        totalPriceHandler.setNextHandler(couponHandler);
        couponHandler.setNextHandler(balanceCheckHandler);
    }

    @Override
    public Transaction create(Transaction transaction){
        try{
            this.authCheckHandler.handle(transaction);
            return transactionRepository.save(transaction);
        }
        catch(IllegalArgumentException e){
            throw e;
        }
    }

    @Async("asyncTaskExecutor")
    @Override
    public void createShipment(UUID transactionId){
        // sementara menggunakan localhost
        String shippingUrl = "http://localhost:8001/shipment/create/" + transactionId.toString();
        restTemplate.postForEntity(shippingUrl, null, ShipmentDTO.class);
    }

    @Override
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findAllByUserId(Integer userId){
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