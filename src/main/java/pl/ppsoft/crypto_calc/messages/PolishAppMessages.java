package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.Message;

@Localized("pl")
public interface PolishAppMessages extends AppMessages {

    @Override
    @Message("Symbol zainwestowanej waluty nie może być pusty!")
    String symbolCannotBeNull();

    @Override
    @Message("Symbol zainwestowanej waluty nie jest poprawny!")
    String symbolInvestedIncorrect();

    @Override
    @Message("Podaj przynajmniej jedną walutę, w którą zainwestowałeś!")
    String needOneOrMoreInvested();

    @Override
    @Message("BitBay wyrzucił błąd")
    String bitBayException();

    @Override
    @Message("Brak danych kryptowalut, lub są niepoprawnie sformatowane!")
    String cryptoDataMissing();

    @Override
    @Message("Symbol kryptowaluty jest niepoprawny")
    String cryptoFormatIncorrect();

    @Override
    @Message("Nie udało się sparsować zainwestowanej kwoty do formatu numerycznego")
    String cannotParseInvestedToNumber();

    @Override
    @Message("Nie udało się sparsować ilości posiadanej kryptowaluty do formatu numerycznego")
    String cannotParseCryptoToNumber();
}
