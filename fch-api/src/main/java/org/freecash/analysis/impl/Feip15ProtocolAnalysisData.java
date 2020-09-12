package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.freecash.component.FreecashComponent;
import org.freecash.dao.IFeip15Dao;
import org.freecash.dao.IFeip3v2Dao;
import org.freecash.domain.Feip15;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.ProtocolHeader;
import org.freecash.utils.SnowflakeIdWorker;
import org.freecash.utils.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanglint
 **/
@Component
@Log4j2
public class Feip15ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private IFeip15Dao feip15Dao;
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
        return Arrays.asList(
                new ProtocolHeader("FEIP","15","2")
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

        if(value.length < 3){
            return;
        }

        String address = freecashComponent.getBindAddress(txId);

        List<Feip3v2> feip3v2s = feip3v2Dao.getAllByAddressAndStatus(address,true);
        if(CollectionUtils.isEmpty(feip3v2s)){
            return;
        }
        String cid = feip3v2s.get(0).getNickName();
        deleteAll(cid);

        if(value.length == 3 || StringUtil.isEmpty(values.get(3))){
            return;
        }

        Feip15 feip15 = new Feip15();
        feip15.setPid(SnowflakeIdWorker.getUUID());
        feip15.setProtocolType(values.get(0));
        feip15.setProtocolNo(values.get(1));
        feip15.setProtocolVersion(values.get(2));
        feip15.setEnName(values.get(3));
        feip15.setOtherName(values.get(4));
        feip15.setAppVersion(values.get(5));
        feip15.setCreateCid(values.get(6));
        feip15.setDetail(values.get(7));
        feip15.setStatus(true);
        feip15.setCid(cid);
        if(values.size()>8){
            feip15.setTag(values.get(8));
        }
        if(values.size()>9){
            feip15.setAppProtocol(values.get(9));
        }

        feip15Dao.save(feip15);
    }

    private void deleteAll(String cid){
        List<Feip15> feip15 = feip15Dao.findAllByCid(cid).orElse(new ArrayList<>());
        if(!CollectionUtils.isEmpty(feip15)){
            feip15.forEach(item->{
                item.setStatus(false);
                feip15Dao.save(item);
            });
        }
    }
}
