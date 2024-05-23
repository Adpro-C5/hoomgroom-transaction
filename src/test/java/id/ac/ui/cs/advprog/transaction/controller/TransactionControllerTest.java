package id.ac.ui.cs.advprog.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.transaction.model.Transaction;
import id.ac.ui.cs.advprog.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest{

    SimpleDateFormat dateFormat;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    List<Transaction> transactions;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        this.dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        transactions = new ArrayList<Transaction>();

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(UUID.fromString("5814d7e4-4fa9-4cb3-80da-4b7315792611"));
        transaction1.setUserId(1);
        transaction1.setProductId(UUID.fromString("658e3ed6-1a43-43b8-98f0-2d23421f65c8"));
        transaction1.setProductAmount(15);
        transaction1.setPromoCode(null);
        transaction1.setTotalPrice(34500000.0);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(UUID.fromString("637bc42b-73a1-44aa-ac6b-a711363d6b57"));
        transaction2.setUserId(1);
        transaction2.setProductId(UUID.fromString("8dff20a8-9df2-4601-b301-30b31737444c"));
        transaction2.setProductAmount(15);
        transaction2.setPromoCode("BELANJAHEMAT20");
        transaction2.setTotalPrice(7975000.0);

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionId(UUID.fromString("2c44b0d4-6f6e-4c73-9f42-bf637cc5642c"));
        transaction3.setUserId(2);
        transaction3.setProductId(UUID.fromString("8dff20a8-9df2-4601-b301-30b31737444c"));
        transaction3.setProductAmount(10);
        transaction3.setPromoCode("BELANJAHEMAT25");
        transaction3.setTotalPrice(7235000.0);

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
    }

    @Test
    void testFindAll() throws Exception{
        when(transactionService.findAll()).thenReturn(transactions);
        mockMvc.perform(get("/transaction/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId", is(transactions.get(0).getTransactionId().toString())))
                .andExpect(jsonPath("$[1].transactionId", is(transactions.get(1).getTransactionId().toString())))
                .andExpect(jsonPath("$[2].transactionId", is(transactions.get(2).getTransactionId().toString())));
    }

    @Test
    void testFindById() throws Exception {
        Transaction transaction = transactions.getFirst();
        doReturn(Optional.of(transaction)).when(transactionService).findById(ArgumentMatchers.any());

        mockMvc.perform(get("/transaction/" + transaction.getTransactionId().toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId", is(transaction.getTransactionId().toString())));
    }

    @Test
    void testFindAllByUser() throws Exception {
        Transaction transaction1 = transactions.get(0);
        Transaction transaction2 = transactions.get(1);
        doReturn(List.of(transaction1, transaction2)).when(transactionService).findAllByUserId(ArgumentMatchers.any());

        mockMvc.perform(get("/transaction/user/" + transaction1.getUserId().toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId", is(transaction1.getUserId())))
                .andExpect(jsonPath("$[1].userId", is(transaction2.getUserId())));
    }

    @Test
    void testCreate() throws Exception {
        Transaction transaction = transactions.getFirst();
        doReturn(transaction).when(transactionService).create(ArgumentMatchers.any());

        String requestBody = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.transactionId", is(transaction.getTransactionId().toString())));

        verify(transactionService, times(1))
                .create(ArgumentMatchers.any());
    }

    @Test
    void testEdit() throws Exception {
        Transaction transaction = transactions.getFirst();
        doReturn(transaction).when(transactionService).edit(ArgumentMatchers.any());

        String requestBody = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(put("/transaction/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.transactionId", is(transaction.getTransactionId().toString())));

        verify(transactionService, times(1))
                .edit(ArgumentMatchers.any());
    }

    @Test
    void testDelete() throws Exception {
        Transaction transaction = transactions.getFirst();

        mockMvc.perform(delete("/transaction/delete/" + transaction.getTransactionId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction Deleted"));

        verify(transactionService, times(1))
                .deleteById(ArgumentMatchers.any());
    }
}