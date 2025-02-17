package isc.etax.taxfiling.utils;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import isc.etax.taxfiling.entities.Address;
import isc.etax.taxfiling.entities.Profile;

@Component
public class StringUtil {

	@Autowired
	private NumberUtil numberUtils;

	public String safeString(Object value) {
		return value == null ? "" : value.toString();
	}

	public String formatNumber(Object value) {
		if (value == null || value == "" || value.toString() == "0.0") {
			return "";
		}
		try {
			double number = numberUtils.roundToTwoDecimalPlaces(Double.parseDouble(value.toString()));
			DecimalFormat formatter = new DecimalFormat("#,##0.00");
			return formatter.format(number);
		} catch (NumberFormatException e) {
			// just in cases where the input is not a valid number
			return "";
		}
	}

	private static final String[] THAI_NUMBERS = { "ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด",
			"เก้า" };

	private static final String[] UNIT_POSITION = { "", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", "ล้าน" };

	public String convertToThaiWords(double number) {
        if (number == 0.0) {
            return "ศูนย์บาทถ้วน";
        }

        // Split number into integer and decimal parts
        String[] parts = new DecimalFormat("0.00").format(number).split("\\.");
        long integerPart = Long.parseLong(parts[0]);
        int decimalPart = Integer.parseInt(parts[1]);

        StringBuilder result = new StringBuilder();

        // Special case for 1.00 → "หนึ่งบาทถ้วน"
        if (integerPart == 1 && decimalPart == 0) {
            return "หนึ่งบาทถ้วน";
        }

        // Convert integer part
        result.append(convertLargeNumber(integerPart)).append("บาท");

        // Convert decimal part
        if (decimalPart > 0) {
            result.append(convertIntegerPart(decimalPart)).append("สตางค์");
        } else {
            result.append("ถ้วน");
        }

        return result.toString();
    }

    private String convertLargeNumber(long number) {
        if (number == 0) {
            return "";
        }

        if (number < 1_000_000) {
            return convertIntegerPart((int) number);
        }

        StringBuilder result = new StringBuilder();
        long higherPart = number / 1_000_000;
        long remainder = number % 1_000_000;

        // Convert the first part before "ล้าน"
        result.append(convertIntegerPart((int) higherPart)).append("ล้าน");

        // Convert the remaining part without adding "และ"
        if (remainder > 0) {
            result.append(convertIntegerPart((int) remainder));
        }

        return result.toString();
    }

    private String convertIntegerPart(int number) {
        if (number == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String numberStr = String.valueOf(number);
        int length = numberStr.length();

        for (int i = 0; i < length; i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            int position = length - i - 1;

            if (digit == 0) {
                continue; // Skip zero
            }

            if (position == 1 && digit == 1) {
                result.append("สิบ");
            } else if (position == 1 && digit == 2) {
                result.append("ยี่สิบ");
            } else {
                if (position > 0 || digit > 1) {
                    result.append(THAI_NUMBERS[digit]);
                } else if (position == 0 && digit == 1) {
                    result.append("เอ็ด"); // Special case for ending in "เอ็ด"
                }

                if (position > 0) {
                    result.append(UNIT_POSITION[position % 6]); // Correct unit
                }
            }
        }

        return result.toString();
    }
	
	private void appendIfNotEmpty(StringJoiner joiner, String value, String prefix) {
	    if (value != null && !value.isBlank()) {
	        joiner.add(prefix + value);
	    }
	}
	
	public String addressConcat(Address address) {
		boolean isBkk = address.getProvince().equals("กรุงเทพ") || address.getProvince().equals("กรุงเทพฯ") || address.getProvince().equals("กรุงเทพมหานคร");
		
		StringJoiner joiner = new StringJoiner(" ");
		appendIfNotEmpty(joiner, address.getBuildName(), "อาคาร");
		appendIfNotEmpty(joiner, address.getVillageNane(), "หมู่บ้าน");
		appendIfNotEmpty(joiner, address.getRoomNo(), "ห้องเลขที่");
		appendIfNotEmpty(joiner, address.getFloorNo(), "ชั้น");
		appendIfNotEmpty(joiner, address.getAddNo(), "");
		appendIfNotEmpty(joiner, address.getSoi(), "ซอย");
	    appendIfNotEmpty(joiner, address.getMooNo(), "หมู่ที่");
	    appendIfNotEmpty(joiner, address.getStreetName(), "ถนน");
	    appendIfNotEmpty(joiner, address.getTambon(), !isBkk ? "ต." : "แขวง");
	    appendIfNotEmpty(joiner, address.getAmphur(), !isBkk ? "อ." : "เขต");
	    appendIfNotEmpty(joiner, address.getProvince(), !isBkk ? "จ." : "");
	    appendIfNotEmpty(joiner, address.getPostalCode(), "");
		
		return joiner.toString();
	}
	
	public String getBranchName(Profile profile) {
		if (profile == null ) {
			return "";
		}
		String branchName = profile.getBranchName();
		String branchNo = profile.getBranchNo();
		
		if (branchName != null && !branchName.isEmpty()) {
	        return " (" + branchName + ")";
	    } else if (branchNo.equals("00000")) {
	    	return " (สำนักงานใหญ่)";
	    } else if (branchNo != null && !branchNo.isEmpty()) {
	        return " (" + branchNo + ") ";
	    }
		return "";
		
	}

}
