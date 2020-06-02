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

    public static void main(String args[]) throws Exception{
        String hex = "464549507c337c317c6379746573747c23667265656361736823e887aae794b1e78eb0e9879123e8baabe4bbbde799bbe8aeb02363727970746f206964656e74697479";
        String txt = hexStringToString(hex);
        System.out.println(txt);

//        System.out.println(txt.split("\\|")[0]);
//        System.out.println(stringToHexString(txt));
//
//
//        String cid = "FEIP|3|1|aaaa|#test";
//        System.out.println(cid);
//        System.out.println(stringToHexString(cid));


        //System.out.println(hexStringToString("61646d696e7c444546494e4954494f4e7c464348662fff1f7c6672656563617368"));


        String str = "admin|DEFINITION|FCH定义|freecash";
        //str = new String(str.getBytes(Charset.forName("UTF-8")),"ISO-8859-1");
        hex = stringToHexString(str);
        System.out.println(hex);
        System.out.println(hexStringToString("464549507c337c317c43597c23e6988ce794a823e697a5e5b8b823e5beaee4bfa123e8aebae59d9b7c"));
//        hex = "464549507c337c327c616161617c23746573747c7c";
//        txt = hexStringToString(hex);
//        System.out.println(txt);
//
//
//        String tmp = "FEIP|3|1||1|";
//        for(String str : tmp.split("\\|")){
//            System.out.println(str);
//        }
    }
}
