package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;

public class AuthCheckHandler extends Handler{

    @Override
    public void handle(Transaction transaction){

        // nanti cek autentikasi

        handleNext(transaction);
    }
}

