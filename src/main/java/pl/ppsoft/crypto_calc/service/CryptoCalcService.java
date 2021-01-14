package pl.ppsoft.crypto_calc.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.ppsoft.crypto_calc.client.BitBayClient;
import pl.ppsoft.crypto_calc.entity.BitBayTicker;
import pl.ppsoft.crypto_calc.entity.CalculatedFullResponse;
import pl.ppsoft.crypto_calc.entity.Error;
import pl.ppsoft.crypto_calc.entity.SingleCryptoData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class CryptoCalcService {

    private static final Pattern TICKER_SYMBOL_PATTERN =
            Pattern.compile("^[a-zA-Z]{3}$");

    @Inject
    @RestClient
    BitBayClient bitBayClient;

    public CalculatedFullResponse getWalletOverview(String symbolInvested, List<String> cryptoListRequest) {
        if (symbolInvested == null) {
            throw new WebApplicationException("Currency invested symbol cannot be null", Response.Status.BAD_REQUEST);
        }

        var matcher = TICKER_SYMBOL_PATTERN.matcher(symbolInvested);
        if (!matcher.matches()) {
            throw new WebApplicationException("Currency invested symbol is incorrect", Response.Status.BAD_REQUEST);
        }

        if (cryptoListRequest.size() == 0) {
            throw new WebApplicationException("Please provide at least one currency you invested into",
                    Response.Status.BAD_REQUEST);
        }

        var cryptoListResponse = new ArrayList<SingleCryptoData>();
        double overallBalance = 0;
        double overallNetProfit = 0;
        double overallInvested = 0;

        for (String input : cryptoListRequest) {
            var singleCryptoResponse = parseSingleCryptoInput(input);

            var bbResponse = bitBayClient.getTickerForCurrencyPair(
                    singleCryptoResponse.getCryptoSymbol() + symbolInvested);
            if (bbResponse.getStatus() == 200) {
                bbResponse.bufferEntity();

                BitBayTicker ticker;
                try {
                    ticker = bbResponse.readEntity(BitBayTicker.class);
                } catch (Exception e) {
                    var error = bbResponse.readEntity(Error.class);
                    throw new WebApplicationException(error.getMessage(), Response.Status.BAD_REQUEST);
                }

                singleCryptoResponse.setBitBayTicker(ticker);
                singleCryptoResponse.setBalance(ticker.getAverage() * singleCryptoResponse.getCryptoAmount());
                singleCryptoResponse.setNetProfit(
                        singleCryptoResponse.getBalance() - singleCryptoResponse.getAmountInvested());
            } else {
                throw new WebApplicationException("BitBay threw an exception", bbResponse.getStatus());
            }

            cryptoListResponse.add(singleCryptoResponse);

            overallBalance += singleCryptoResponse.getBalance();
            overallNetProfit += singleCryptoResponse.getNetProfit();
            overallInvested += singleCryptoResponse.getAmountInvested();
        }

        return new CalculatedFullResponse(overallBalance, overallNetProfit, overallInvested, symbolInvested,
                cryptoListResponse);
    }

    private SingleCryptoData parseSingleCryptoInput(String input) {
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

        var singleCryptoResponse = new SingleCryptoData();
        singleCryptoResponse.setCryptoSymbol(cryptoSymbol);
        singleCryptoResponse.setAmountInvested(parsedAmountInvested);
        singleCryptoResponse.setCryptoAmount(parsedCryptoAmount);

        return singleCryptoResponse;
    }
}
