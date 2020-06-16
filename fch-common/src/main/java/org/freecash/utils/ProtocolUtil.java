package org.freecash.utils;

/**
 * @author wanglint
 * @date 2020/6/15 16:57
 **/
public class ProtocolUtil {

    public static Result getValue(String hexStr,int start, int length){

        String tmp = hexStr.substring(start,start + length);
        int authorLength = HexStringUtil.hexToInteger(tmp);
        int sIndex = start + length;
        int eIndex = start + length + authorLength;
        tmp = hexStr.substring(sIndex,eIndex);
        tmp = HexStringUtil.hexStringToString(tmp);
        return new Result(tmp,eIndex);
    }

    public static class Result{
        private String value;
        private int end;

        public Result(String value, int end) {
            this.value = value;
            this.end = end;
        }


        public String getValue() {
            return value;
        }

        public int getEnd() {
            return end;
        }
    }
}
