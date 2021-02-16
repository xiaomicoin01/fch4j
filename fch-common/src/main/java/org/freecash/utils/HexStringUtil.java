package org.freecash.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        try {
            if (StringUtil.isEmpty(s)) {
                s = "";
            }
            s = new String(s.getBytes(Charset.forName("UTF-8")), "ISO-8859-1");
            String str = "";
            for (int i = 0; i < s.length(); i++) {
                int ch = (int) s.charAt(i);
                String s4 = Integer.toHexString(ch);
                str = str + s4;
            }
            return str;
        } catch (Exception e) {
            return s;
        }

    }

    public static String integerToHex(int src, int byteLength) {
        String target = Integer.toHexString(src);
        StringBuilder sb = new StringBuilder();
        if (target.length() < byteLength * 2) {
            for (int i = 0; i < byteLength * 2 - target.length(); i++) {
                sb.append("0");
            }
        }
        return sb.append(target).toString();
    }

    public static Integer hexToInteger(String hex) {
        try {
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public static String fileToHex(InputStream in) throws Exception {
        StringBuffer sb = new StringBuffer();
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read = 1024;
        int readSize = 1024;
        while (read == readSize) {
            read = in.read(buffer, 0, readSize);
            bos.write(buffer, 0, read);
        }

        byte[] result = bos.toByteArray();
        // 字节数组转成十六进制
        String str = byteToHex(result);
        return str;
    }

    public static void hexToFile(String src, OutputStream out) throws Exception {
        if (src == null || src.length() == 0) {
            return;
        }
        byte[] bytes = hexToByte(src);
        IOUtils.write(bytes,out);
    }

    private static int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }

    /**
     * byte数组转hex
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
        }
        return sb.toString().trim();
    }

    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }


    public static void main(String args[]) throws Exception {
        String src="aaaa";
        System.out.println(stringToHexString(src));

    }
}
