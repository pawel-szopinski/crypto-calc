package pl.ppsoft.crypto_calc.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class Wallet {

    private double overallBalance;
    private double overallNetProfit;
    private double overallAmountInvested;
    private String symbolInvested;
    private List<SingleCrypto> singleCryptoList;

    public Wallet() {
    }

    public Wallet(double overallBalance, double overallNetProfit, double overallAmountInvested,
                  String symbolInvested,
                  List<SingleCrypto> singleCryptoList) {
        this.overallBalance = overallBalance;
        this.overallNetProfit = overallNetProfit;
        this.overallAmountInvested = overallAmountInvested;
        this.symbolInvested = symbolInvested;
        this.singleCryptoList = singleCryptoList;
    }

    public double getOverallBalance() {
        return overallBalance;
    }

    public void setOverallBalance(double overallBalance) {
        this.overallBalance = overallBalance;
    }

    public double getOverallNetProfit() {
        return overallNetProfit;
    }

    public void setOverallNetProfit(double overallNetProfit) {
        this.overallNetProfit = overallNetProfit;
    }

    public double getOverallAmountInvested() {
        return overallAmountInvested;
    }

    public void setOverallAmountInvested(double overallAmountInvested) {
        this.overallAmountInvested = overallAmountInvested;
    }

    public String getSymbolInvested() {
        return symbolInvested;
    }

    public void setSymbolInvested(String symbolInvested) {
        this.symbolInvested = symbolInvested;
    }

    public List<SingleCrypto> getSingleCryptoList() {
        return singleCryptoList;
    }

    public void setSingleCryptoList(List<SingleCrypto> singleCryptoList) {
        this.singleCryptoList = singleCryptoList;
    }
}
