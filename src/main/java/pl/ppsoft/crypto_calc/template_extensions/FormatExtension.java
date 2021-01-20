package pl.ppsoft.crypto_calc.template_extensions;

import io.quarkus.qute.TemplateExtension;

import java.text.NumberFormat;
import java.util.Locale;

@TemplateExtension(namespace = "number-format")
public class FormatExtension {

    public static String getLocaleFormattedNumber(double number, Locale locale) {
        var numberFormat = NumberFormat.getNumberInstance(locale);
        numberFormat.setMaximumFractionDigits(8);
        numberFormat.setMinimumFractionDigits(2);

        return numberFormat.format(number);
    }
}
