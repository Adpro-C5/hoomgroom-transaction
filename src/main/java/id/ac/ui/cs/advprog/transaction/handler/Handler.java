package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import lombok.Setter;

@Setter
public abstract class Handler{

    private Handler nextHandler;

    public abstract void handle(Transaction transaction, String jwtToken);

    protected void handleNext(Transaction transaction, String jwtToken){
        if(this.nextHandler != null){
            nextHandler.handle(transaction, jwtToken);
        }
    }
}

