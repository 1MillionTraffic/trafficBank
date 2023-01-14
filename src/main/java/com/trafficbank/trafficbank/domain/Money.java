package com.trafficbank.trafficbank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;


public class Money {
    private BigInteger amount;

    public Money(BigInteger amount) {
        this.amount = amount;
    }

    public static Money ZERO = new Money(BigInteger.valueOf(0L));

    public static Money of(long amount){
        return new Money(BigInteger.valueOf(amount));
    }

    public static Money add(Money a, Money b){
        return new Money(a.getAmount().add(b.getAmount()));
    }
    public static Money subtract(Money a, Money b){
        return new Money(a.getAmount().subtract(b.getAmount()));
    }

    public BigInteger getAmount(){
        return this.amount;
    }
    public boolean isPositiveOrZero(){
        return this.amount.compareTo(BigInteger.ZERO) >= 0;
    }
}
