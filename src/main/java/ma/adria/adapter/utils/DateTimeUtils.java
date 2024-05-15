package ma.adria.adapter.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static ma.adria.adapter.common.CoreConstant.DateTimeFormats.INPUT_FORMATTER;
import static ma.adria.adapter.common.CoreConstant.DateTimeFormats.OUTPUT_FORMATTER;

@UtilityClass
@Slf4j
public class DateTimeUtils {

    public static String parse(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        try {
            final var dateTime = LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
            return dateTime.format(OUTPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("Error parsing date time string: {}. Exception: {}", dateTimeString, e.getMessage());
            log.debug("Stack trace: ", e);
            return null;
        }
    }
}
