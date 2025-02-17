package isc.etax.taxfiling.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public String dateFormat(Object timestamp) {
//		System.out.println(timestamp);
//		System.out.println("------------------------");
		long timeInMillis;

		if (timestamp == null || timestamp == "") {
			return ""; // Return an empty string for null input
		} else if (timestamp instanceof String) {
            try {
                timeInMillis = Long.parseLong((String) timestamp);
            } catch (NumberFormatException e) {
                // Log the invalid value for debugging
                System.err.println("Invalid timestamp string format: " + timestamp);
                throw new IllegalArgumentException("Invalid timestamp string format: " + timestamp, e);
            }
        } else if (timestamp instanceof Number) {
            timeInMillis = ((Number) timestamp).longValue();
        } else {
            throw new IllegalArgumentException("Unsupported timestamp type. Must be String or Number.");
        }

        // Convert the timestamp to a Date object and format it
        Date date = new Date(timeInMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
	}
	
	public String convertStringToLocalDate(LocalDate localDate, boolean isBuddhist) {
		if (localDate == null ) {
			return "";
		}
        try {

            if (isBuddhist) {
                ThaiBuddhistDate buddhistDate = ThaiBuddhistDate.from(localDate);
                return buddhistDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
            } else {
            	return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
            }
        } catch (DateTimeParseException e) {
            return "";
        }
	}
	
	public String formatDatePart(LocalDate localDate, String pattern, boolean isBuddhist) {
		try {
            if (isBuddhist) {
                int buddhistYear = localDate.getYear() + 543;
                pattern = pattern.replace("yyyy", String.valueOf(buddhistYear));
                pattern = pattern.replace("YYYY", String.valueOf(buddhistYear)); // Handling uppercase YYYY
                return localDate.format(DateTimeFormatter.ofPattern(pattern));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return localDate.format(formatter);
        } catch (DateTimeParseException e) {
            return "";
        }
	}
	
}
