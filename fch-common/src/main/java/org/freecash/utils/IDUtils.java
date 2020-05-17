package org.freecash.utils;

import java.text.DecimalFormat;

public class IDUtils {
    private static DecimalFormat numFormat = new DecimalFormat("0000");

    public static String getTimestampId() {
        long cur = System.currentTimeMillis();
        int a = (int) (Math.random() * 10000);
        String tmp = numFormat.format(a);
        return cur + "" + tmp;
    }

    public static void main(String args[]){
        String[] s = "*计算机外部设备*富士通扫描仪".split("\\*");
        for(String str : s){
            System.out.println(str);
        }
    }
}
