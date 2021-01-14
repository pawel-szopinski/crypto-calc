package pl.ppsoft.crypto_calc.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class CalculatedFullResponse {

    private double overallBalance;
    private double overallNetProfit;
    private double overallAmountInvested;
    private String symbolInvested;
    private List<SingleCryptoData> singleCryptoDataList;

    public CalculatedFullResponse() {
    }

    public CalculatedFullResponse(double overallBalance, double overallNetProfit, double overallAmountInvested,
                                  String symbolInvested,
                                  List<SingleCryptoData> singleCryptoDataList) {
        this.overallBalance = overallBalance;
        this.overallNetProfit = overallNetProfit;
        this.overallAmountInvested = overallAmountInvested;
        this.symbolInvested = symbolInvested;
        this.singleCryptoDataList = singleCryptoDataList;
    }

    public double getOverallBalance() {
        return Math.round(overallBalance * 100.0) / 100.0;
    }

    public void setOverallBalance(double overallBalance) {
        this.overallBalance = overallBalance;
    }

    public double getOverallNetProfit() {
        return Math.round(overallNetProfit * 100.0) / 100.0;
    }

    public void setOverallNetProfit(double overallNetProfit) {
        this.overallNetProfit = overallNetProfit;
    }

    public double getOverallAmountInvested() {
        return Math.round(overallAmountInvested * 100.0) / 100.0;
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

    public List<SingleCryptoData> getSingleCryptoDataList() {
        return singleCryptoDataList;
    }

    public void setSingleCryptoDataList(List<SingleCryptoData> singleCryptoDataList) {
        this.singleCryptoDataList = singleCryptoDataList;
    }
}
