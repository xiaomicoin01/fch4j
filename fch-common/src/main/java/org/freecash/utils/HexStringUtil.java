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

    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static void main(String args[]){
        String hex = "464549507c337c317c6379746573747c23667265656361736823e887aae794b1e78eb0e9879123e8baabe4bbbde799bbe8aeb02363727970746f206964656e74697479";
        String txt = hexStringToString(hex);
        System.out.println(txt);

        System.out.println(txt.split("\\|")[0]);
        System.out.println(stringToHexString(txt));


    }
}
