package pl.ppsoft.crypto_calc.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.ppsoft.crypto_calc.client.BitBayClient;
import pl.ppsoft.crypto_calc.entity.AppError;
import pl.ppsoft.crypto_calc.entity.BitBayTicker;
import pl.ppsoft.crypto_calc.entity.SingleCrypto;
import pl.ppsoft.crypto_calc.entity.Wallet;
import pl.ppsoft.crypto_calc.messages.AppMessages;
import pl.ppsoft.crypto_calc.messages.MessageUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
    @RestClient
    BitBayClient bitBayClient;

    @RequestScoped
    AppMessages messages;

    public Wallet getWalletOverview(String symbolInvested, List<String> cryptoListRequest, boolean noRounding,
                                    String locale) {
        messages = MessageUtils.setLocale(locale);

        if (symbolInvested == null) {
            throw new WebApplicationException(messages.symbolCannotBeNull(), Response.Status.BAD_REQUEST);
        }

        var matcher = TICKER_SYMBOL_PATTERN.matcher(symbolInvested);
        if (!matcher.matches()) {
            throw new WebApplicationException(messages.symbolInvestedIncorrect(), Response.Status.BAD_REQUEST);
        }

        if (cryptoListRequest.size() == 0) {
            throw new WebApplicationException(messages.needOneOrMoreInvested(),
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
                throw new WebApplicationException(messages.bitBayException(), bbResponse.getStatus());
            }

            cryptoListResponse.add(singleCryptoResponse);

            overallBalance += singleCryptoResponse.getBalance();
            overallNetProfit += singleCryptoResponse.getNetProfit();
            overallInvested += singleCryptoResponse.getAmountInvested();
        }

        var wallet = new Wallet(overallBalance, overallNetProfit, overallInvested, symbolInvested, cryptoListResponse);

        if (!noRounding) {
            applyRounding(wallet);
        }

        return wallet;
    }

    private SingleCrypto parseSingleCryptoInput(String input) {
        String[] singleCryptoRequest;
        singleCryptoRequest = input.split(",");

        if (singleCryptoRequest.length < 3) {
            throw new WebApplicationException(messages.cryptoDataMissing(), Response.Status.BAD_REQUEST);
        }

        String cryptoSymbol = singleCryptoRequest[0];
        var matcher = TICKER_SYMBOL_PATTERN.matcher(cryptoSymbol);
        if (!matcher.matches()) {
            throw new WebApplicationException(messages.cryptoFormatIncorrect() + " (" + cryptoSymbol + ")!",
                    Response.Status.BAD_REQUEST);
        }

        double parsedAmountInvested;
        try {
            parsedAmountInvested = Double.parseDouble(singleCryptoRequest[1]);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(messages.cannotParseInvestedToNumber() + " (" + cryptoSymbol + ")!",
                    Response.Status.BAD_REQUEST);
        }

        double parsedCryptoAmount;
        try {
            parsedCryptoAmount = Double.parseDouble(singleCryptoRequest[2]);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(messages.cannotParseCryptoToNumber() + " (" + cryptoSymbol + ")!",
                    Response.Status.BAD_REQUEST);
        }

        var singleCryptoResponse = new SingleCrypto();
        singleCryptoResponse.setSymbol(cryptoSymbol);
        singleCryptoResponse.setAmountInvested(parsedAmountInvested);
        singleCryptoResponse.setAmountAcquired(parsedCryptoAmount);

        return singleCryptoResponse;
    }

    private void applyRounding(Wallet wallet) {
        wallet.setOverallBalance(Math.round(wallet.getOverallBalance() * ROUND) / ROUND);
        wallet.setOverallNetProfit(Math.round(wallet.getOverallNetProfit() * ROUND) / ROUND);
        wallet.setOverallAmountInvested(Math.round(wallet.getOverallAmountInvested() * ROUND) / ROUND);

        for (SingleCrypto singleCrypto : wallet.getSingleCryptoList()) {
            singleCrypto.setBalance(Math.round(singleCrypto.getBalance() * ROUND) / ROUND);
            singleCrypto.setNetProfit(Math.round(singleCrypto.getNetProfit() * ROUND) / ROUND);
            singleCrypto.setAmountInvested(Math.round(singleCrypto.getAmountInvested() * ROUND) / ROUND);

            var bitBayTicker = singleCrypto.getBitBayTicker();
            bitBayTicker.setAverage(Math.round(bitBayTicker.getAverage() * ROUND) / ROUND);
            bitBayTicker.setMin(Math.round(bitBayTicker.getMin() * ROUND) / ROUND);
            bitBayTicker.setMax(Math.round(bitBayTicker.getMax() * ROUND) / ROUND);
        }
    }
}