package pl.ppsoft.crypto_calc.messages;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.MessageBundles;

public class MessageUtils {

    private static final String PL = "pl";

    public static AppMessages setLocale(String locale) {
        if (PL.equals(locale)) {
            return MessageBundles.get(AppMessages.class, Localized.Literal.of(PL));
        }

        return MessageBundles.get(AppMessages.class);
    }
}
