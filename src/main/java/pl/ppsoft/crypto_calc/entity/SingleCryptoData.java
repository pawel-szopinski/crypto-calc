package pl.ppsoft.crypto_calc.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SingleCryptoData {

    private String cryptoSymbol;
    private double balance;
    private double netProfit;
    private double amountInvested;
    private double cryptoAmount;
    private BitBayTicker bitBayTicker;

    public SingleCryptoData() {
    }

    public SingleCryptoData(String cryptoSymbol, double balance, double netProfit, double amountInvested,
                            double cryptoAmount,
                            BitBayTicker bitBayTicker) {
        this.cryptoSymbol = cryptoSymbol;
        this.balance = balance;
        this.netProfit = netProfit;
        this.amountInvested = amountInvested;
        this.cryptoAmount = cryptoAmount;
        this.bitBayTicker = bitBayTicker;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public double getBalance() {
        return Math.round(balance * 100.0) / 100.0;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getNetProfit() {
        return Math.round(netProfit * 100.0) / 100.0;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public double getAmountInvested() {
        return Math.round(amountInvested * 100.0) / 100.0;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public double getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(double cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public BitBayTicker getBitBayTicker() {
        return bitBayTicker;
    }

    public void setBitBayTicker(BitBayTicker bitBayTicker) {
        this.bitBayTicker = bitBayTicker;
    }
}
