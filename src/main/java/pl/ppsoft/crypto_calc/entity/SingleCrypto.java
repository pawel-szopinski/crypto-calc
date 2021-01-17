package pl.ppsoft.crypto_calc.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SingleCrypto {

    private String symbol;
    private double balance;
    private double netProfit;
    private double amountInvested;
    private double amountAcquired;
    private BitBayTicker bitBayTicker;

    public SingleCrypto() {
    }

    public SingleCrypto(String symbol, double balance, double netProfit, double amountInvested,
                        double amountAcquired,
                        BitBayTicker bitBayTicker) {
        this.symbol = symbol;
        this.balance = balance;
        this.netProfit = netProfit;
        this.amountInvested = amountInvested;
        this.amountAcquired = amountAcquired;
        this.bitBayTicker = bitBayTicker;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public double getAmountAcquired() {
        return amountAcquired;
    }

    public void setAmountAcquired(double amountAcquired) {
        this.amountAcquired = amountAcquired;
    }

    public BitBayTicker getBitBayTicker() {
        return bitBayTicker;
    }

    public void setBitBayTicker(BitBayTicker bitBayTicker) {
        this.bitBayTicker = bitBayTicker;
    }
}
