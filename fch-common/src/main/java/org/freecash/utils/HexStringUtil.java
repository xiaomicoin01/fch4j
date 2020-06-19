package org.freecash.utils;

import java.nio.charset.Charset;

/**
 * @author wanglint
 * @date 2020/5/17 15:17
 **/
public class HexStringUtil {

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        try{
            if(StringUtil.isEmpty(s)){
                s = "";
            }
            s = new String(s.getBytes(Charset.forName("UTF-8")),"ISO-8859-1");
            String str = "";
            for (int i = 0; i < s.length(); i++) {
                int ch = (int) s.charAt(i);
                String s4 = Integer.toHexString(ch);
                str = str + s4;
            }
            return str;
        }catch (Exception e){
            return s;
        }

    }

    public static String integerToHex(int src, int byteLength){
        String target = Integer.toHexString(src);
        StringBuilder sb = new StringBuilder();
        if(target.length() < byteLength * 2){
            for(int i =0;i<byteLength * 2-target.length();i++){
                sb.append("0");
            }
        }
        return sb.append(target).toString();
    }

    public static Integer hexToInteger(String hex){
        try {
            return Integer.parseInt(hex,16);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String args[]) throws Exception{



        String str = "b47aa484fd2e0aa78677329ba3fa85f296f055161ff020044eef755d78201ece";
        //str = new String(str.getBytes(Charset.forName("UTF-8")),"ISO-8859-1");
        String hex = stringToHexString(str);
        System.out.println(hex);

    }
}
