package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.freecash.analysis.IAnalysisData;
import org.freecash.component.FreecashComponent;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.dao.IFeip3v2Dao;
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

/**
 * @author wanglint
 * @date 2020/5/17 15:01
 **/
@Component
@Log4j2
public class Feip3v2ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private FreecashComponent freecashComponent;
    @Resource
    private IFeip3v2Dao feip3v2Dao;

    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Arrays.asList(
                new ProtocolHeader("FEIP","3","1"),
                new ProtocolHeader("FEIP","3","2")
        );
    }

    /**
     * 协议处理类
     *
     * @param protocolValue 协议内容
     */
    @Override
    public void analysis(String protocolValue,String txId) throws Exception{
        String address = freecashComponent.getBindAddress(txId);
        String[] value = protocolValue.split("\\|");
        List<Feip3v2> feip3v2 = feip3v2Dao.getAllByAddressAndStatus(address,true);
        //注销(有垃圾数据)
        boolean isTrue = (value.length < 3) || (value.length > 3 && StringUtils.isEmpty(value[3]));
        if(isTrue){
            //changeStatus(feip3v2);
            return;
        }
        //注销CID
        if(value.length == 3){
            changeStatus(feip3v2);
            return;
        }

        //新增
        if(CollectionUtils.isEmpty(feip3v2)){
            Feip3v2 f = new Feip3v2();
            f.setId(SnowflakeIdWorker.getUUID());
            f.setProtocolName(value[0]);
            f.setProtocolNo(Integer.valueOf(value[1]));
            f.setProtocolVersion(value[2]);
            f.setNickName(getNickName(value[3],address));
            if(value.length>4){
                f.setTag(value[4]);
            }
            if(value.length>5){
                f.setInviteUser(value[5]);
            }
            if(value.length>6){
                f.setDetail(value[6]);
            }
            f.setTxHash(txId);
            f.setAddress(address);
            f.setStatus(true);
            f.setCreateDate(new Date());
            feip3v2Dao.save(f);
            return;
        }
        //更新
        if(value.length > 3 && StringUtils.isNotEmpty(value[3])){
            String tmp = value[3] + "_" + address.substring(address.length() - 4);
            if(Objects.equals(tmp,feip3v2.get(0).getNickName()) &&
                Objects.equals(feip3v2.get(0).getAddress(),address)){
                log.warn("cid：{}，address:{}已经存在，且cid是一样的，不用操作，直接跳过",tmp, address);
                return;
            }

            changeStatus(feip3v2);

            Feip3v2 newF = new Feip3v2();
            BeanUtils.copyProperties(feip3v2.get(0),newF,"id");
            newF.setStatus(true);
            String nickName = getNickName(value[3],address);
            newF.setNickName(nickName);
            newF.setCreateDate(new Date());
            newF.setId(SnowflakeIdWorker.getUUID());
            newF.setAddress(address);
            if(value.length>6){
                newF.setDetail(value[6]);
            }
            newF.setTxHash(txId);
            feip3v2Dao.save(newF);
        }

    }

    private void changeStatus(List<Feip3v2> feip3v2) {
        for (Feip3v2 f : feip3v2){
            f.setStatus(false);
            feip3v2Dao.save(f);
        }
    }



    private String getNickName(String name,String address){

        int len = 4;
        String nickName = "";
        while(true){
            nickName = name + "_" + address.substring(address.length() - len);
            List<Feip3v2> feip3v2s = feip3v2Dao.getAllByNickName(nickName);
            if(CollectionUtils.isEmpty(feip3v2s)){
                break;
            }else{
                if(Objects.equals(feip3v2s.get(0).getAddress(),address)){
                    break;
                }else{
                    len++;
                }
            }
        }
        return nickName;
    }
}
