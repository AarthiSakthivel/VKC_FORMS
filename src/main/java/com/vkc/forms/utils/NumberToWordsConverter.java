package com.vkc.forms.utils;

import java.text.DecimalFormat;
import org.springframework.stereotype.Component;

@Component
public class NumberToWordsConverter {

    private static final String[] tensNames = {
        "",
        " ten",
        " twenty",
        " thirty",
        " forty",
        " fifty",
        " sixty",
        " seventy",
        " eighty",
        " ninety"
    };

    private static final String[] numNames = {
        "",
        " one",
        " two",
        " three",
        " four",
        " five",
        " six",
        " seven",
        " eight",
        " nine",
        " ten",
        " eleven",
        " twelve",
        " thirteen",
        " fourteen",
        " fifteen",
        " sixteen",
        " seventeen",
        " eighteen",
        " nineteen"
    };

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) {
            return current.trim();
        }

        return numNames[number] + " hundred" + (current.isEmpty() ? "" : " " + current.trim());
    }

    public static String convert(long number) {
        if (number == 0) {
            return "Zero rupees only";
        }

        String snumber = Long.toString(number);
        String mask = "000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        int crores = Integer.parseInt(snumber.substring(0, 2));
        int lakhs = Integer.parseInt(snumber.substring(2, 4));
        int thousands = Integer.parseInt(snumber.substring(4, 6));
        int hundreds = Integer.parseInt(snumber.substring(6, 9));

        String tradCrores = crores > 0 ? convertLessThanOneThousand(crores) + " crore " : "";
        String tradLakhs = lakhs > 0 ? convertLessThanOneThousand(lakhs) + " lakh " : "";
        String tradThousands = thousands > 0 ? convertLessThanOneThousand(thousands) + " thousand " : "";
        String tradHundreds = convertLessThanOneThousand(hundreds);

        String result = (tradCrores + tradLakhs + tradThousands + tradHundreds).trim();
        result = result.replaceAll("\\s{2,}", " "); // Remove extra spaces
        result = result.substring(0, 1).toUpperCase() + result.substring(1);

        return result + " rupees only";
    }

    public static String convertWithPaisa(double number) {
        long rupees = (long) number;
        int paisa = (int) Math.round((number - rupees) * 100);

        String rupeesPart = convert(rupees).replace("rupees only", "").trim();
        String paisaPart = (paisa > 0) ? convertLessThanOneThousand(paisa).trim() + " paisa" : "";

        String result;
        if (!paisaPart.isEmpty()) {
            result = "Indian Rupees " + rupeesPart + " and " + paisaPart + " only";
        } else {
            result = "Indian Rupees " + rupeesPart + " only";
        }

        // Convert everything after "Indian Rupees " to lowercase
        return result.substring(0, "Indian Rupees ".length()) + result.substring("Indian Rupees ".length()).toLowerCase();
    }
}


