package org.freecash.analysis;

import lombok.extern.log4j.Log4j2;
import org.freecash.domain.ProtocolHeader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wanglint
 * @date 2020/5/17 14:59
 **/
@Component
@Log4j2
public class AnalysisDataComponent implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<ProtocolHeader,IAnalysisData> allAnalysisData = new HashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        Map<String,IAnalysisData> instans = applicationContext.getBeansOfType(IAnalysisData.class);
        for (IAnalysisData analysisData : instans.values()){
            analysisData.getType().forEach(i->{
                allAnalysisData.put(i,analysisData);
            });

        }
    }

    public void analysis(String protocolValue,String txId) throws Exception{
        if(StringUtils.isEmpty(protocolValue)){
            return;
        }
        String[] tmps = protocolValue.split("\\|");
        if(tmps.length <3){
            log.warn("协议内容：{}，长度少于3，不需要处理",protocolValue);
            return;
        }
        String protocolName = tmps[0];
        String protocolNo = tmps[1];
        String protocolVersion = tmps[2];
        IAnalysisData tmp = allAnalysisData.get(new ProtocolHeader(protocolName,protocolNo,protocolVersion));
        if(Objects.isNull(tmp)){
            log.error("不能处理协议内容：{}",protocolValue);
        }else{
            tmp.analysis(protocolValue,txId);
        }
    }

    public static void main(String[] args) {
        String[] s = "OP_RETURN 464549507c327c337c616161615f475444717c6e756c6c7c7c7c346132303038626163353734366462396132626164323130333238363038316136396561346239623434313364396464306164303931663836336530373762657c37303164326261353961316139626136653564626566303533316334363265653863373038386263366238623036623866306262343166623331336662336133".split("\\s");
        Arrays.stream(s).forEach(str->System.out.println(str));
    }
}
