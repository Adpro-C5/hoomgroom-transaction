package id.ac.ui.cs.advprog.transaction.dto;
import lombok.Getter;

@Getter
public class BalanceDecreaseDTO{
    private int userId;
    private long addedBalance;

    public BalanceDecreaseDTO(int userId, long addedBalance){
        this.userId = userId;
        this.addedBalance = addedBalance;
    }
}