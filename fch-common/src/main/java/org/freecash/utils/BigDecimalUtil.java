package org.freecash.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtil {

    public static final String POS_8 = "0.00000000";

    public static String format(BigDecimal num, String format){
        DecimalFormat temp = new DecimalFormat(format);
        return temp.format(num);
    }
}
