package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.Message;

@Localized("pl")
public interface PolishAppMessages extends AppMessages {

    @Override
    @Message("Portfel Kryptowalut")
    String title();

    @Override
    @Message("Cześć")
    String hello();

    @Override
    @Message("Podsumowanie Portfela")
    String walletSummary();

    @Override
    @Message("Podział Na Waluty")
    String splitByCurrency();

    @Override
    @Message("Dane Giełdowe")
    String exchangeData();

    @Override
    @Message("Symbol")
    String symbol();

    @Override
    @Message("Stan Konta")
    String balance();

    @Override
    @Message("Zysk / Strata")
    String netProfit();

    @Override
    @Message("Inwestycja")
    String invested();

    @Override
    @Message("Nabyto")
    String acquired();

    @Override
    @Message("Obecna Cena")
    String currentPrice();

    @Override
    @Message("Min Cena")
    String minPrice();

    @Override
    @Message("Max Cena")
    String maxPrice();

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

    @Override
    @Message("Coś poszło nie tak...")
    String oops();
}
