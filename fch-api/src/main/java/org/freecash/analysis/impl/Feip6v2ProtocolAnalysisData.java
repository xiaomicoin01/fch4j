package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.freecash.analysis.IAnalysisData;
import org.freecash.config.FreecashConfig;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.*;
import org.freecash.dao.IFeip3v2Dao;
import org.freecash.dao.IFeip6v2Dao;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.Feip6v2;
import org.freecash.domain.Feip6v2Otption;
import org.freecash.domain.ProtocolHeader;
import org.freecash.service.Feip6v2Service;
import org.freecash.utils.SnowflakeIdWorker;
import org.freecash.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanglint
 * @date 2020/5/17 15:01
 **/
@Component
@Log4j2
public class Feip6v2ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private FreecashConfig config;
    @Resource
    private FchdClient fchdClient;
    @Resource
    private Feip6v2Service feip6v2Service;

    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Arrays.asList(
                new ProtocolHeader("FEIP","6","2")
        );
    }

    /**
     * 协议处理类
     *
     * @param protocolValue 协议内容
     */
    @Override
    public void analysis(String protocolValue,String txId) throws Exception{
        String address = getBindAddress(txId);
        String[] value = protocolValue.split("\\|");
        Feip6v2 feip6v2 = new Feip6v2();

        feip6v2.setProtocolName(value[0]);
        feip6v2.setProtocolNo(Integer.parseInt(value[1]));
        feip6v2.setProtocolVersion(value[2]);
        try{
            feip6v2.setOperation(Feip6v2Otption.acqureByValue(value[3].toLowerCase()));
        }catch (Exception e){
            log.error("操作类型【{}】未知，直接跳过",value[3]);
            return;
        }
        if(value.length > 4){
            feip6v2.setBeginDate(value[4]);
        }
        if(value.length > 5){
            feip6v2.setEndDate(value[5]);
        }
        if(value.length > 6){
            feip6v2.setProtocolRange(value[6]);
        }
        feip6v2.setAuthFromAddress(address);

        List<String> addresses = getOutAddress(txId);
        addresses.remove(feip6v2.getAuthFromAddress());
        if(CollectionUtils.isEmpty(addresses)){
            feip6v2Service.delete(feip6v2.getAuthFromAddress());
            return;
        }
        for (String toAddr : addresses){
            Feip6v2 tmp = new Feip6v2();
            BeanUtils.copyProperties(feip6v2,tmp);
            tmp.setId(SnowflakeIdWorker.getUUID());
            tmp.setAuthToAddress(toAddr);
            List<Feip6v2> feip6v2s = feip6v2Service.getFeip6v2(tmp.getAuthFromAddress(),tmp.getAuthToAddress());
            if(feip6v2.getOperation() == Feip6v2Otption.AUTHORITION){
                if(!CollectionUtils.isEmpty(feip6v2s)){
                    Feip6v2 feip6v21 = feip6v2s.get(0);
                    feip6v21.setBeginDate(tmp.getBeginDate());
                    feip6v21.setEndDate(tmp.getEndDate());
                    if(StringUtil.isEmpty(tmp.getProtocolRange())){
                        feip6v21.setProtocolRange("");
                    }else{
                        feip6v21.setProtocolRange(feip6v2.getProtocolRange()+"#"+tmp.getProtocolRange());
                    }
                    feip6v2Service.save(feip6v21);
                }else{
                    feip6v2Service.save(tmp);
                }

            }else if(feip6v2.getOperation() == Feip6v2Otption.IRREVOCABLE_AUTHORITION){
                feip6v2Service.delete(tmp.getAuthFromAddress(),tmp.getAuthToAddress());
                feip6v2Service.save(tmp);
            }else if(feip6v2.getOperation() == Feip6v2Otption.DEPRIVATION){
                if(!CollectionUtils.isEmpty(feip6v2s)){
                    Feip6v2 feip6v21 = feip6v2s.get(0);
                    Set<String> protocols = Arrays.stream(feip6v21.getProtocolRange().split("#")).map(item->{
                        item = item.replaceAll(",","");
                        return item;
                    }).collect(Collectors.toSet());

                    Set<String> rmProtocols = Arrays.stream(tmp.getProtocolRange().split("#")).map(item->{
                        item = item.replaceAll(",","");
                        return item;
                    }).collect(Collectors.toSet());

                    if(CollectionUtils.isEmpty(rmProtocols)){
                        feip6v2Service.delete(tmp.getAuthFromAddress(),tmp.getAuthToAddress());
                    }else if(!CollectionUtils.isEmpty(rmProtocols) && CollectionUtils.isEmpty(protocols)){
                        protocols = new HashSet<>(config.getProtocols());
                        protocols.removeAll(rmProtocols);
                        if(CollectionUtils.isEmpty(protocols)){
                            feip6v2Service.delete(tmp.getAuthFromAddress(),tmp.getAuthToAddress());
                        }else{
                            feip6v21.setProtocolRange(String.join("#",protocols.toArray(new String[0])));
                            feip6v2Service.save(feip6v21);
                        }
                    }else if(!CollectionUtils.isEmpty(rmProtocols) && !CollectionUtils.isEmpty(protocols)){
                        protocols.removeAll(rmProtocols);
                        if(CollectionUtils.isEmpty(protocols)){
                            feip6v2Service.delete(tmp.getAuthFromAddress(),tmp.getAuthToAddress());
                        }else{
                            feip6v21.setProtocolRange(String.join("#",protocols.toArray(new String[0])));
                            feip6v2Service.save(feip6v21);
                        }
                    }
                }else{
                    log.warn("未做授权就做解除权限操作非法，直接忽略");
                }

            }else{
                log.error("该操作:{},没有添加处理逻辑，联系管理员",tmp.getOperation());
            }
        }
    }

    private String getBindAddress(String txId) throws Exception{
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        RawInput rawInput = rawTransaction.getVIn().get(0);
        String id = rawInput.getTxId();
        int vout = rawInput.getVOut();

        rawTransaction = (RawTransaction)fchdClient.getRawTransaction(id,true);
        RawOutput rawOutput = rawTransaction.getVOut().get(vout);
        return rawOutput.getScriptPubKey().getAddresses().get(0);
    }

    private List<String> getOutAddress(String txId) throws Exception{
        RawTransaction rawTransaction = (RawTransaction)fchdClient.getRawTransaction(txId,true);

        List<RawOutput> rawOutput = rawTransaction.getVOut();
        List<String> res = new ArrayList<>();
        rawOutput.forEach(item->{
            List<String> addresses = item.getScriptPubKey().getAddresses();
            if(Objects.nonNull(addresses)){
                res.addAll(addresses);
            }
        });
        return res;
    }
}
