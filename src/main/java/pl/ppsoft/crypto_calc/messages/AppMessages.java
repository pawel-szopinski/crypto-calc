package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle
public interface AppMessages {

    @Message("Crypto Wallet")
    String title();

    @Message("Hello")
    String hello();

    @Message("Wallet Summary")
    String walletSummary();

    @Message("Split By Currency")
    String splitByCurrency();

    @Message("Exchange Data")
    String exchangeData();

    @Message("Symbol")
    String symbol();

    @Message("Balance")
    String balance();

    @Message("Net Profit")
    String netProfit();

    @Message("Invested")
    String invested();

    @Message("Acquired")
    String acquired();

    @Message("Current Price")
    String currentPrice();

    @Message("Min Price")
    String minPrice();

    @Message("Max Price")
    String maxPrice();

    @Message("Currency invested symbol cannot be null!")
    String symbolCannotBeNull();

    @Message("Currency invested symbol is incorrect!")
    String symbolInvestedIncorrect();

    @Message("Please provide at least one currency you invested into!")
    String needOneOrMoreInvested();

    @Message("BitBay threw an exception")
    String bitBayException();

    @Message("Crypto data missing or incorrectly formatted")
    String cryptoDataMissing();

    @Message("Crypto symbol format is incorrect")
    String cryptoFormatIncorrect();

    @Message("Cannot parse amount invested to number")
    String cannotParseInvestedToNumber();

    @Message("Cannot parse crypto amount to number")
    String cannotParseCryptoToNumber();

    @Message("Something went wrong...")
    String oops();
}
