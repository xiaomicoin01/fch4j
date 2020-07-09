package org.freecash.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static String encodeValue(List<EncodeItem> items){
        StringBuilder sb = new StringBuilder();
        for(EncodeItem item : items){
            String value = Objects.isNull(item.getValue()) ? "" : item.getValue();
            int len = item.getByteLength();
            String valueHex = HexStringUtil.stringToHexString(value);
            String valueHexLen = HexStringUtil.integerToHex(valueHex.length()/2,len);
            sb.append(valueHexLen).append(valueHex);
        }
        return sb.toString();
    }

    public static List<String> decodeValue(String hexStr, List<Integer> length){
        List<String> sb = new ArrayList<>();
        Result res = new Result("",0);
        int sIndex = 0;
        int eIndex = 0;
        for(int len : length){
            eIndex = sIndex + len*2;
            String tmp = hexStr.substring(sIndex,eIndex);
            int contentLen = HexStringUtil.hexToInteger(tmp);
            sIndex = eIndex;
            eIndex += contentLen*2;
            tmp = hexStr.substring(sIndex,eIndex);
            tmp = HexStringUtil.hexStringToString(tmp);
            sIndex = eIndex;
            sb.add(tmp);
        }
        return sb;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result{
        private String value;
        private int end;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EncodeItem{
        private String value;
        private int byteLength;
    }

    public static void main(String[] args) {
        ProtocolUtil.EncodeItem author = new ProtocolUtil.EncodeItem("1",4);
        ProtocolUtil.EncodeItem type = new ProtocolUtil.EncodeItem("1234",4);
        String data1 = ProtocolUtil.encodeValue(Arrays.asList(author,type));

        System.out.println(data1);
        List<String> res = ProtocolUtil.decodeValue(data1,Arrays.asList(4,4));
        System.out.println(res);

        String meateData = "04464f435001330133064352454154454061623365396238366233343230366461336535333736633135633661323530386332343065383436616466366437306161343964316237393938396137383032000000057a682d636e00";
        res = ProtocolUtil.decodeValue(meateData,Arrays.asList(1,1,1,1,1,1,2,1,1));
        System.out.println(res);
    }
}
