import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class to validate date-time, date, and time strings.
 *
 * @author jvz
 * @date 2024/6/13
 */
public class DateTimeValidator {

    // Enum to distinguish between date-time, date, and time formats
    public enum DateTimeType {
        DATE_TIME, DATE, TIME
    }

    // List of common date formats
    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd",
            "yyyy-M-d",
            "yyyy/MM/dd",
            "yyyy/M/d",
            "yyyy.MM.dd",
            "yyyy.M.d",
            "dd-MM-yyyy",
            "d-M-yyyy",
            "dd/MM/yyyy",
            "d/M/yyyy",
            "dd.MM.yyyy",
            "d.M.yyyy",
            "MM-dd-yyyy",
            "M-d-yyyy",
            "MM/dd/yyyy",
            "M/d/yyyy",
            "MM.dd.yyyy",
            "M.d.yyyy"
    );

    // List of common time formats
    private static final List<String> TIME_FORMATS = Arrays.asList(
            "HH:mm:ss",
            "HH:mm:s",
            "HH:mm",
            "HH:m:ss",
            "HH:m:s",
            "HH:m",
            "H:mm:ss",
            "H:mm:s",
            "H:mm",
            "H:m:ss",
            "H:m:s",
            "H:m",
            "HH.mm.ss",
            "HH.mm.s",
            "HH.m.ss",
            "HH.m.s",
            "H.mm.ss",
            "H.mm.s",
            "H.m.ss",
            "H.m.s",
            "HHmm"
    );

    /**
     * Checks if a given string is a valid date-time, date, or time in any of the specified formats.
     *
     * @param dateTimeStr the string to check
     * @return true if the string is a valid date-time, date, or time, false otherwise
     */
    public static boolean isValidDateTimeOrTime(String dateTimeStr) {
        for (String format : TIME_FORMATS) {
            if (isValidFormat(dateTimeStr, format, DateTimeType.TIME)) {
                return true;
            }
        }
        for (String format : DATE_FORMATS) {
            if (isValidFormat(dateTimeStr, format, DateTimeType.DATE)) {
                return true;
            }
        }
        for (String format : generateDateTimeFormats()) {
            if (isValidFormat(dateTimeStr, format, DateTimeType.DATE_TIME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given string is a valid date-time, date, or time in the specified format.
     *
     * @param dateTimeStr the string to check
     * @param format      the format to check against
     * @param type        the type of date/time to check (date-time, date, or time)
     * @return true if the string is a valid date-time, date, or time in the specified format, false otherwise
     */
    public static boolean isValidFormat(String dateTimeStr, String format, DateTimeType type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            switch (type) {
                case DATE:
                    LocalDate.parse(dateTimeStr, formatter);
                    break;
                case TIME:
                    LocalTime.parse(dateTimeStr, formatter);
                    break;
                case DATE_TIME:
                    LocalDateTime.parse(dateTimeStr, formatter);
                    break;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Generates a list of date-time formats by combining date and time formats.
     *
     * @return a list of date-time formats
     */
    public static List<String> generateDateTimeFormats() {
        List<String> dateTimeFormats = new ArrayList<>();

        for (String dateFormat : DATE_FORMATS) {
            for (String timeFormat : TIME_FORMATS) {
                dateTimeFormats.add(dateFormat + " " + timeFormat);
                dateTimeFormats.add(timeFormat + " " + dateFormat);
            }
        }

        return dateTimeFormats;
    }
}
