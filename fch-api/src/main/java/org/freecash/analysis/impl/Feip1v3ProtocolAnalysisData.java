package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.freecash.analysis.IAnalysisData;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.dao.IFeip1v3Dao;
import org.freecash.dao.IFeip3v2Dao;
import org.freecash.domain.Feip1v3;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.ProtocolHeader;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wanglint
 * @date 2020/5/17 15:01
 **/
@Component
@Log4j2
public class Feip1v3ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private IFeip1v3Dao feip1v3Dao;

    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Arrays.asList(
                new ProtocolHeader("FEIP","1","3")
        );
    }

    /**
     * 协议处理类
     *
     * @param protocolValue 协议内容
     */
    @Override
    public void analysis(String protocolValue,String txId) throws Exception{
        String[] value = protocolValue.split("\\|");
        List<String> values = Arrays.stream(value).map(item->{return Objects.isNull(item) ? "" : item;}).collect(Collectors.toList());

        if(value.length < 10){
            return;
        }

        Feip1v3 feip1v3 = new Feip1v3(
                SnowflakeIdWorker.getUUID(),
                values.get(0),
                values.get(1),
                values.get(2),
                values.get(3),
                values.get(4),
                values.get(5),
                values.get(6),
                values.get(7),
                values.get(8),
                values.get(9),
                txId,
                new Date()
        );
        if(!feip1v3Dao.findAllByDataHashAndTxId(feip1v3.getDataHash(),txId).isPresent()){
            feip1v3Dao.save(feip1v3);
        }
    }
}
