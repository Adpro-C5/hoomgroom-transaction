package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;

public class TotalPriceHandler extends Handler{

    @Override
    public void handle(Transaction transaction){

        // dummy product price
        Double productPrice = 3556000.0;

        Double totalPrice = productPrice * transaction.getProductAmount();
        transaction.setTotalPrice(totalPrice);

        handleNext(transaction);
    }
}

