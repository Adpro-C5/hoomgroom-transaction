package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import lombok.Setter;

@Setter
public abstract class Handler{

    private Handler nextHandler;

    public abstract void handle(Transaction transaction);

    protected void handleNext(Transaction transaction){
        if(this.nextHandler != null){
            nextHandler.handle(transaction);
        }
    }
}

