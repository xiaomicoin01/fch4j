package org.freecash.analysis.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.freecash.analysis.IAnalysisData;
import org.freecash.analysis.ProtocolHeader;
import org.freecash.component.FreecashComponent;
import org.freecash.dao.IFeip3Dao;
import org.freecash.domain.Feip3;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wanglint
 **/
@Component
@Slf4j
public class Feip3JsonProtocol implements IAnalysisData {
    @Resource
    private FreecashComponent freecashComponent;
    @Resource
    private IFeip3Dao feip3Dao;

    /**
     * CID协议类型
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Lists.newArrayList(
                ProtocolHeader.builder().protocolName("CID").protocolNo("3")
                        .protocolVersion("4").protocolType("FEIP").build()
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
        List<Feip3> feip3v2 = feip3Dao.getAllByAddressAndStatus(address,true);
        JSONObject tmp = JSONObject.parseObject(protocolValue);
        //注销(有垃圾数据)
        if(!tmp.containsKey("name")){
            changeStatus(feip3v2);
            return;
        }

        //新增
        if(CollectionUtils.isEmpty(feip3v2)){
            Feip3 f = Feip3.builder().name(tmp.getString("name")).txHash(txId)
                    .address(address).status(true).homePage(tmp.getString("homepage")).build();
            feip3Dao.save(f);
            return;
        }else{
            String name = tmp.getString("name") + "_" + address.substring(address.length() - 4);
            if(Objects.equals(name,feip3v2.get(0).getName()) &&
                    Objects.equals(feip3v2.get(0).getAddress(),address)){
                log.warn("cid：{}，address:{}已经存在，且cid是一样的，不用操作，直接跳过",tmp, address);
                return;
            }

            changeStatus(feip3v2);

            Feip3 newF = new Feip3();
            BeanUtils.copyProperties(feip3v2.get(0),newF,"pid");
            String nickName = getNickName(tmp.getString("name"),address);
            newF.setStatus(true);
            newF.setName(nickName);
            newF.setAddress(address);
            newF.setHomePage(tmp.getString("homepage"));
            newF.setTxHash(txId);
            feip3Dao.save(newF);
        }

    }

    private void changeStatus(List<Feip3> feip3v2) {
        for (Feip3 f : feip3v2){
            f.setStatus(false);
            feip3Dao.save(f);
        }
    }



    private String getNickName(String name,String address){

        int len = 4;
        String nickName;
        while(true){
            nickName = name + "_" + address.substring(address.length() - len);
            List<Feip3> feip3v2s = feip3Dao.getAllByName(nickName);
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