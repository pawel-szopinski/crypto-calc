package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle
public interface AppMessages {

    @Message("Currency invested symbol cannot be null!")
    String symbolCannotBeNull();
}
