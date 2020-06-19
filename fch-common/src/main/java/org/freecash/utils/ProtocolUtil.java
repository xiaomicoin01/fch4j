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
            String valueHexLen = HexStringUtil.integerToHex(valueHex.length(),len);
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
            eIndex += contentLen;
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
        int AUTHOR_LENGTH = 2;
        int TYPE_LENGTH = 2;
        int TITLE_LENGTH = 3;
        int CONTENT_LENGTH = 8;
        ProtocolUtil.EncodeItem author = new ProtocolUtil.EncodeItem("张三",AUTHOR_LENGTH);
        ProtocolUtil.EncodeItem type = new ProtocolUtil.EncodeItem("DEFINITION",TYPE_LENGTH);
        ProtocolUtil.EncodeItem title = new ProtocolUtil.EncodeItem("测试",TITLE_LENGTH);
        ProtocolUtil.EncodeItem content = new ProtocolUtil.EncodeItem("看一下效果",CONTENT_LENGTH);
        String data1 = ProtocolUtil.encodeValue(Arrays.asList(author,type,title,content));

        System.out.println(data1);

        System.out.println("-------------------------");
        data1 = "00066d6331000a6573736179000030e6aca2e8bf8ee4bda0e58aa0e68891e4b8bae5a5bde58f8b000000000000021ee8afa6e68385e4bb8be7bb8d20e4b88de883bde4b8bae7a9baefbc810d0a2323234d4d4d4d4d4d0d0a232323e7a0b4e593a6e593a6e593a60d0a2323e9be99e9be9f0d0ae9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b6e9a1b60d0a23e8a1a8e6a0bc0d0a7c2020e9a1b6e9a1b6e9a1b6207c2020e9a1b6e9a1b6e9a1b6207c0d0a7c203a2d2d2d2d2d2d2d2d2d2d2d2d207c203a2d2d2d2d2d2d2d2d2d2d2d2d207c0d0a7c2020e9a1b6e9a1b6e9a1b6207c20e9a1b6e9a1b6e9a1b6e9a1b620207c0d0a7c2020e9a1b6e9a1b6e9a1b6207c2020e9a1b6e9a1b6e9a1b6e9a1b6207c0d0a0d0a0d0ae6aca2e8bf8e2020e58aa0e688912020e5a5bde58f8b2020";
        List<String> res = ProtocolUtil.decodeValue(data1,Arrays.asList(2,2,3,8));
        System.out.println(res);
        System.out.println("-------------------------");

        String meateData =org.freecash.utils.ProtocolUtil.encodeValue(Arrays.asList(
                new org.freecash.utils.ProtocolUtil.EncodeItem("FOCP", 1),
                new org.freecash.utils.ProtocolUtil.EncodeItem("3",1),
                new org.freecash.utils.ProtocolUtil.EncodeItem("3",1),
                new org.freecash.utils.ProtocolUtil.EncodeItem("CREATE",1),
                new org.freecash.utils.ProtocolUtil.EncodeItem("90cc1020b584e988d54772620209746f83476ef10f2d46c733b9be89754636e3",2),
                new org.freecash.utils.ProtocolUtil.EncodeItem("",2),
                new org.freecash.utils.ProtocolUtil.EncodeItem("",3),
                new org.freecash.utils.ProtocolUtil.EncodeItem("ar",2),
                new org.freecash.utils.ProtocolUtil.EncodeItem("",2)
        ));


        System.out.println(meateData);

        System.out.println("-------------------------");
        meateData = "08464f4350023302330c4352454154450080373934613330636532356539663962306136626562663232326365346562653432383665313738323636666665666462333863353264343863666539633766310000000000000a7a682d636e0000";
        res = ProtocolUtil.decodeValue(meateData,Arrays.asList(1,1,1,1,2,2,3,2,2));
        System.out.println(res);
        System.out.println("-------------------------");

        System.out.println(Integer.toHexString(3));

        System.out.print((char)Integer.parseInt("3"));
    }
}
