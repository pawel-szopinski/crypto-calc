package pl.ppsoft.crypto_calc.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.ppsoft.crypto_calc.client.BitBayClient;
import pl.ppsoft.crypto_calc.entity.AppError;
import pl.ppsoft.crypto_calc.entity.BitBayTicker;
import pl.ppsoft.crypto_calc.entity.SingleCrypto;
import pl.ppsoft.crypto_calc.entity.Wallet;
import pl.ppsoft.crypto_calc.messages.AppMessages;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class CryptoCalcService {

    private static final Pattern TICKER_SYMBOL_PATTERN = Pattern.compile("^[a-zA-Z]{3}$");
    private static final double ROUND = 10.0;

    @Inject
    AppMessages appMessages;

    @Inject
    @RestClient
    BitBayClient bitBayClient;

    public Wallet getWalletOverview(String symbolInvested, List<String> cryptoListRequest, boolean noRounding) {
        if (symbolInvested == null) {
            throw new WebApplicationException(appMessages.symbolCannotBeNull(), Response.Status.BAD_REQUEST);
        }

        var matcher = TICKER_SYMBOL_PATTERN.matcher(symbolInvested);
        if (!matcher.matches()) {
            throw new WebApplicationException("Currency invested symbol is incorrect", Response.Status.BAD_REQUEST);
        }

        if (cryptoListRequest.size() == 0) {
            throw new WebApplicationException("Please provide at least one currency you invested into",
                    Response.Status.BAD_REQUEST);
        }

        var cryptoListResponse = new ArrayList<SingleCrypto>();
        double overallBalance = 0;
        double overallNetProfit = 0;
        double overallInvested = 0;

        for (String input : cryptoListRequest) {
            var singleCryptoResponse = parseSingleCryptoInput(input);

            var bbResponse = bitBayClient.getTickerForCurrencyPair(
                    singleCryptoResponse.getSymbol() + symbolInvested);
            if (bbResponse.getStatus() == 200) {
                bbResponse.bufferEntity();

                BitBayTicker ticker;
                try {
                    ticker = bbResponse.readEntity(BitBayTicker.class);
                } catch (Exception e) {
                    var error = bbResponse.readEntity(AppError.class);
                    throw new WebApplicationException(error.getMessage(), Response.Status.BAD_REQUEST);
                }

                singleCryptoResponse.setBitBayTicker(ticker);
                singleCryptoResponse.setBalance(ticker.getAverage() * singleCryptoResponse.getAmountAcquired());
                singleCryptoResponse.setNetProfit(
                        singleCryptoResponse.getBalance() - singleCryptoResponse.getAmountInvested());
            } else {
                throw new WebApplicationException("BitBay threw an exception", bbResponse.getStatus());
            }

            cryptoListResponse.add(singleCryptoResponse);

            overallBalance += singleCryptoResponse.getBalance();
            overallNetProfit += singleCryptoResponse.getNetProfit();
            overallInvested += singleCryptoResponse.getAmountInvested();

            if (!noRounding) {
                singleCryptoResponse.setBalance(Math.round(singleCryptoResponse.getBalance() * ROUND) / ROUND);
                singleCryptoResponse.setNetProfit(Math.round(singleCryptoResponse.getNetProfit() * ROUND) / ROUND);
                singleCryptoResponse.setAmountInvested(
                        Math.round(singleCryptoResponse.getAmountInvested() * ROUND) / ROUND);
            }
        }

        if (!noRounding) {
            overallBalance = Math.round(overallBalance * ROUND) / ROUND;
            overallNetProfit = Math.round(overallNetProfit * ROUND) / ROUND;
            overallInvested = Math.round(overallInvested * ROUND) / ROUND;
        }

        return new Wallet(overallBalance, overallNetProfit, overallInvested, symbolInvested,
                cryptoListResponse);
    }

    private SingleCrypto parseSingleCryptoInput(String input) {
        String[] singleCryptoRequest;
        singleCryptoRequest = input.split(",");

        if (singleCryptoRequest.length < 3) {
            throw new WebApplicationException("Crypto data missing or incorrectly formatted",
                    Response.Status.BAD_REQUEST);
        }

        String cryptoSymbol = singleCryptoRequest[0];
        var matcher = TICKER_SYMBOL_PATTERN.matcher(cryptoSymbol);
        if (!matcher.matches()) {
            throw new WebApplicationException("Crypto symbol (" + cryptoSymbol + ") format is incorrect",
                    Response.Status.BAD_REQUEST);
        }

        double parsedAmountInvested;
        try {
            parsedAmountInvested = Double.parseDouble(singleCryptoRequest[1]);
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Cannot parse (" + cryptoSymbol + ") amount invested to number",
                    Response.Status.BAD_REQUEST);
        }

        double parsedCryptoAmount;
        try {
            parsedCryptoAmount = Double.parseDouble(singleCryptoRequest[2]);
        } catch (NumberFormatException e) {
            throw new WebApplicationException("Cannot parse (" + cryptoSymbol + ") crypto amount to number",
                    Response.Status.BAD_REQUEST);
        }

        var singleCryptoResponse = new SingleCrypto();
        singleCryptoResponse.setSymbol(cryptoSymbol);
        singleCryptoResponse.setAmountInvested(parsedAmountInvested);
        singleCryptoResponse.setAmountAcquired(parsedCryptoAmount);

        return singleCryptoResponse;
    }
}
