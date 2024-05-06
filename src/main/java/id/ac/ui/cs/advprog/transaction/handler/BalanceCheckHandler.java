package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;

public class BalanceCheckHandler extends Handler{

    @Override
    public void handle(Transaction transaction){

        // dummy wallet balance
        Double balance = 550600000.0;

        if(balance < transaction.getTotalPrice()){
            throw new IllegalArgumentException("Your balance is not enough");
        }

        handleNext(transaction);
    }
}

