package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;

public abstract class Handler{

    private Handler nextHandler;

    public void setNextHandler(Handler nextHandler){
        this.nextHandler = nextHandler;
    }

    public abstract void handle(Transaction transaction);

    protected void handleNext(Transaction transaction){
        if(this.nextHandler != null){
            nextHandler.handle(transaction);
        }
    }
}

