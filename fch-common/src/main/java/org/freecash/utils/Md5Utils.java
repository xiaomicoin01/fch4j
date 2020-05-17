package org.freecash.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 */
public class Md5Utils {
    /**
     * 字符串转换成MD5字符串
     *
     * @param src 原始字符串
     * @return 返回md5后的字符串
     */
    public static String strToMd5(String src) {
        return DigestUtils.md5Hex(src).toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(strToMd5("4DHsVtVExLLYF1Duz9z7PSsG1ehZBke4rMjtimebank"));
    }
}
