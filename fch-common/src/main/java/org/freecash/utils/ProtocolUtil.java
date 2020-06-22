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
//        int AUTHOR_LENGTH = 2;
//        int TYPE_LENGTH = 2;
//        int TITLE_LENGTH = 3;
//        int CONTENT_LENGTH = 8;
//        ProtocolUtil.EncodeItem author = new ProtocolUtil.EncodeItem("张三",AUTHOR_LENGTH);
//        ProtocolUtil.EncodeItem type = new ProtocolUtil.EncodeItem("DEFINITION",TYPE_LENGTH);
//        ProtocolUtil.EncodeItem title = new ProtocolUtil.EncodeItem("测试",TITLE_LENGTH);
//        ProtocolUtil.EncodeItem content = new ProtocolUtil.EncodeItem("看一下效果",CONTENT_LENGTH);
//        String data1 = ProtocolUtil.encodeValue(Arrays.asList(author,type,title,content));
//
//        System.out.println(data1);

        System.out.println("-------------------------");
        String data1 = "0012616161615f47544471000a4553534159000012e68891e79a844643480000000000000138dadae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958ae5958adadadada215b5d286672656564726976653a313233343529dadadadada2020202020202020202020202020202020202020202020202020202020202020";
        List<String> res = ProtocolUtil.decodeValue(data1,Arrays.asList(2,2,3,8));
        System.out.println(res);
        System.out.println("-------------------------");

//        String meateData =org.freecash.utils.ProtocolUtil.encodeValue(Arrays.asList(
//                new org.freecash.utils.ProtocolUtil.EncodeItem("FOCP", 1),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("3",1),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("3",1),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("CREATE",1),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("90cc1020b584e988d54772620209746f83476ef10f2d46c733b9be89754636e3",2),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("",2),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("",3),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("ar",2),
//                new org.freecash.utils.ProtocolUtil.EncodeItem("",2)
//        ));
//
//
//        System.out.println(meateData);

        System.out.println("-------------------------");
        String meateData = "08464f4350023302330c4352454154450080393863623233623262653433386531313961373530343833303733353266363264666238643235343866373039363165323362346436643535666537343664320000000000000a7a682d636e0000";
        res = ProtocolUtil.decodeValue(meateData,Arrays.asList(1,1,1,1,2,2,3,2,2));
        System.out.println(res);
        System.out.println("-------------------------");
    }
}
