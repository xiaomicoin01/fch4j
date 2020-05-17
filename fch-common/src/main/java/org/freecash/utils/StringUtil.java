package org.freecash.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author: create by hayes
 * @version: v1.0
 * @description: cn.enn.utils
 * @date:2019/8/13
 **/
public class StringUtil {
    private static final String STRING_NULL="null";

    /**
     * 格式化空字符串或者“null”字符
     * @param src
     * @return
     */
    public static String formatNull(Object src){
        if(src == null){
            return "";
        }
        String strValue = src.toString();
        if(StringUtils.isEmpty(strValue)||STRING_NULL.equals(strValue)){
            return "";
        }else{
            return strValue;
        }
    }

    public static boolean isEmpty(String src) {
        if (src == null) {
            return true;
        }
        if (src.trim().intern() == "".intern()) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(String src) {
        return !isEmpty(src);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String parseString(Object src) {
        if (src == null) {
            return "";
        }
        if (StringUtils.isEmpty(src.toString())) {
            return "";
        }
        return src.toString();
    }
    public static Double parseDouble(Object src) {
        if (src == null) {
            return 0.0d;
        }
        if (StringUtils.isEmpty(src.toString())) {
            return 0.0d;
        }
        try {
            return Double.parseDouble(src.toString());
        } catch (NumberFormatException e) {
            return 0.0d;
        }
    }
}
