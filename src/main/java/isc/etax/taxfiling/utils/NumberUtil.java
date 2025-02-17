package isc.etax.taxfiling.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class NumberUtil {

	public double roundToTwoDecimalPlaces(double rawNumber) {
		String strValue = String.valueOf(rawNumber);
		if (strValue.matches("\\d+\\.\\d{2}")) {
			return rawNumber;
		}
		
		return new BigDecimal(rawNumber).setScale(2, RoundingMode.HALF_UP).doubleValue();

	}
	
	public double saveDouble(Object rawNumber) {
        if (rawNumber == null || rawNumber instanceof Boolean) {
            return 0.00;
        }
        if (rawNumber instanceof String) {
            try {
                return Double.parseDouble((String) rawNumber);
            } catch (NumberFormatException e) {
                return 0.00;
            }
        }
        if (rawNumber instanceof Number) {
            return ((Number) rawNumber).doubleValue();
        }
		
		return 0.00;

	}

}
