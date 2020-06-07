package org.freecash.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.StringWriter;
import java.util.*;

/**
 * @author wanglint
 */
@Getter
public enum KnowledgeType {
    DEFINITION("定义","名词","文本内容"),
    ONE_CHOICE("单选","自由文本，填空内容以\"??\"代替","选项以#开始，第一个选项为真。如：属于直辖市的？#北京#南京#西安#深圳"),
    MULTIPLE_CHOICE("多选","自由文本，填空内容以\"??\"代替","正确选项个数，后接选项，以#开始，正确选择放在前面。如：中国直辖市有？“4#北京#上海#天津#重庆#成都”"),
    GAP_LILLING("填空","自由文本，填空内容以\"??\"代替","每空的答案以#开始"),
    TRUE_FALSE("判断","自由文本","正确为1，错误为0"),
    QUESTION("问答","自由文本","文本内容"),
    EVENT("事件","自由文本","自由格式，建议包含：时间，（地点，）主体，事件描述"),
    ESSAY("散文","自由文本","文本内容"),
    PAPER("论文","自由文本","自由文本");

    private String name;
    private String title;
    private String content;
    KnowledgeType(String name,String title,String content){
        this.name = name;
        this.title = title;
        this.content = content;
    }


    public static void main(String[] args) throws Exception {
        List<Map<String,String>> res = new ArrayList<>();
        Arrays.stream(KnowledgeType.values()).forEach(item->{
            Map<String,String> tmp = new HashMap<>();
            tmp.put("key",item.toString());
            tmp.put("name",item.getName());
            tmp.put("title",item.getTitle());
            tmp.put("content",item.getContent());
            res.add(tmp);
        });
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw,res);
        System.out.println(sw.toString());
    }
}
