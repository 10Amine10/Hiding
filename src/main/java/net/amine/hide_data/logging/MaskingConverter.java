package net.amine.hide_data.logging;


import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Plugin(name = "LogMaskingConverter", category = "Converter")
@ConverterKeys({"msgMask"})
public class MaskingConverter extends LogEventPatternConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaskingConverter.class);

    private static final Pattern SENSITIVE_DATA_PATTERN = Pattern.compile(
            "(pin:\\s*\\d{4})|(cardNumber:\\s*\\d{10})"
    );

    protected MaskingConverter(String name, String style) {
        super(name, style);
    }

    public static MaskingConverter newInstance(final String[] options) {
        LOGGER.info("new instance....123");
        return new MaskingConverter("msgMask123", null);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        String message = event.getMessage().getFormattedMessage();
        Matcher matcher = SENSITIVE_DATA_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String replacement;
            if (matcher.group().contains("pin")) {
                replacement = "pin: ****";
            } else if (matcher.group().contains("cardNumber")) {
                replacement = "cardNumber: **********" + matcher.group().substring(matcher.group().length() -3);
            } else {
                replacement = matcher.group();
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        toAppendTo.append(sb);
    }
}
