package org.freecash.analysis.impl;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.freecash.component.FreecashComponent;
import org.freecash.dao.IFeip3v2Dao;
import org.freecash.domain.Feip17;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.ProtocolHeader;
import org.freecash.service.Feip17Service;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 保险柜
 * @author wanglint
 **/
@Component
@Log4j2
public class Feip17ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private Feip17Service feip17Service;
    @Resource
    private FreecashComponent freecashComponent;
    @Resource
    private IFeip3v2Dao feip3v2Dao;

    /**
     * CID协议类型
     * @return 协议头
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Lists.newArrayList(new ProtocolHeader("FEIP","17","2"));
    }

    /**
     * 协议处理类
     *
     * @param protocolValue 协议内容
     */
    @Override
    public void analysis(String protocolValue,String txId) throws Exception{
        String[] value = protocolValue.split("\\|");
        List<String> values = Arrays.stream(value).map(item->Objects.isNull(item) ? "" : item).collect(Collectors.toList());

        if(value.length < 3){
            return;
        }

        String address = freecashComponent.getBindAddress(txId);
        Long time = freecashComponent.getOnlineTime(txId);

        List<Feip3v2> feip3v2s = feip3v2Dao.getAllByAddressAndStatus(address,true);
        if(CollectionUtils.isEmpty(feip3v2s)){
            return;
        }
        String cid = feip3v2s.get(0).getNickName();

        Feip17 feip17 = new Feip17();
        feip17.setPid(SnowflakeIdWorker.getUUID());
        feip17.setProtocolType(values.get(0));
        feip17.setProtocolNo(values.get(1));
        feip17.setProtocolVersion(values.get(2));
        feip17.setAlgorithm(values.get(3));
        feip17.setCipherText(values.get(4));
        if(values.size() > 5){
            feip17.setType(values.get(5));
        }
        if(values.size() > 6){
            feip17.setRemarks(values.get(6));
        }
        feip17.setCid(cid);
        feip17.setTxId(txId);
        feip17.setOnLineDate(Long.toString(time));

        feip17Service.save(feip17);
    }
}
