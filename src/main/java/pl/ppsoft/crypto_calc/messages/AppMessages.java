package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle
public interface AppMessages {

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
}
