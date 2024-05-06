package id.ac.ui.cs.advprog.transaction.handler;

import id.ac.ui.cs.advprog.transaction.model.Transaction;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CouponHandler extends Handler{

    @Override
    public void handle(Transaction transaction){

        LocalDate today = LocalDate.now();

        // dummy coupon expired date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        LocalDate couponExpiredDate = LocalDate.parse("2030-Apr-10", formatter);

        // dummy coupon minimum purchase
        Double couponMinimumPurchase = 1000000.0;

        // dummy coupon discount
        Double couponDiscount = 375000.0;

        Double purchase = transaction.getTotalPrice();

        // nanti akan ditambahkan satu if lagi untuk memeriksa apakah kode kupon sudah digunakan
        if(today.isAfter(couponExpiredDate) || today.isEqual(couponExpiredDate)){
            throw new IllegalArgumentException("Your coupon code has expired by " + couponExpiredDate.format(formatter));
        }
        else if(purchase < couponMinimumPurchase){
            throw new IllegalArgumentException("Your purchase is " + purchase + " while the minimum purchase for this coupon is " + couponMinimumPurchase);
        }

        Double newPrice = purchase - couponDiscount;
        transaction.setTotalPrice(newPrice);

        handleNext(transaction);
    }
}

