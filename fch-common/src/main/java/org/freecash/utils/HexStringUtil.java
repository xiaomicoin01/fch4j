package org.freecash.utils;

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

    public static void main(String args[]){
        String hex = "464549507c337c317c77616e676c696e7c23e69cace4baba7c5669705f6e4d6554";
        String txt = hexStringToString(hex);
        System.out.println(txt);
    }
}
