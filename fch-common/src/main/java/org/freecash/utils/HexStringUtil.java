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
        System.out.println(hexStringToString("61646d696e7c444546494e4954494f4e7ce6af94e789b9e5b881347ce6af94e789b9e5b881efbc88426974636f696eefbc89e79a84e6a682e5bfb5e69c80e5889de794b1e4b8ade69cace881aae59ca832303038e5b9b43131e69c8831e697a5e68f90e587baefbc8ce5b9b6e4ba8e32303039e5b9b431e69c8833e697a5e6ada3e5bc8fe8af9ee7949fe38082e6a0b9e68daee4b8ade69cace881aae79a84e6809de8b7afe8aebee8aea1e58f91e5b883e79a84e5bc80e6ba90e8bdafe4bbb6e4bba5e58f8ae5bbbae69e84e585b6e4b88ae79a84503250e7bd91e7bb9ce38082e6af94e789b9e5b881e698afe4b880e7a78d503250e5bda2e5bc8fe79a84e8999ae68b9fe79a84e58aa0e5af86e695b0e5ad97e8b4a7e5b881e38082e782b9e5afb9e782b9e79a84e4bca0e8be93e6848fe591b3e79d80e4b880e4b8aae58ebbe4b8ade5bf83e58c96e79a84e694afe4bb98e7b3bbe7bb9fe38082"));
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
