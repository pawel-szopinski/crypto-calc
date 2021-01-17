package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.Message;

@Localized("pl")
public interface PolishAppMessages extends AppMessages {

    @Override
    @Message("Symbol zainwestowanej waluty nie może być pusty!")
    String symbolCannotBeNull();
}
