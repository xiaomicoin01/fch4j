package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.freecash.component.FreeDriveComponnet;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.dao.IFocp3v1Dao;
import org.freecash.dao.IKnowledgeDao;
import org.freecash.domain.Focp3v1;
import org.freecash.domain.Knowledge;
import org.freecash.domain.ProtocolHeader;
import org.freecash.dto.FreeDriveGetRequest;
import org.freecash.dto.FreeDriveGetResponse;
import org.freecash.dto.KnowledgeType;
import org.freecash.utils.HexStringUtil;
import org.freecash.utils.ProtocolUtil;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Component;

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
public class Focp3v3ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private FchdClient fchdClient;
    @Resource
    private IFocp3v1Dao focp3v1Dao;
    @Resource
    private IKnowledgeDao knowledgeDao;
    @Resource
    private FreeDriveComponnet freeDriveComponnet;

    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Arrays.asList(
                new ProtocolHeader("FEIP","2","3")

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

        Focp3v1 f = new Focp3v1();
        f.setId(SnowflakeIdWorker.getUUID());
        f.setProtocolName(value[0]);
        f.setProtocolNo(Integer.valueOf(value[1]));
        f.setProtocolVersion(value[2]);
        f.setAuthor(value[3]);
        if(!Objects.isNull(value[4])){
            f.setFileType(value[4]);
        }
        if(!Objects.isNull(value[5])){
            f.setFileNo(value[5]);
        }
        if(!Objects.isNull(value[6])){
            f.setFileVersion(value[6]);
        }
        if(value.length > 7){
            f.setDataHash(value[7]);
        }
        if(value.length > 8){
            f.setFilePath(value[8]);
        }

        f.setStatus(true);
        f.setCreateDate(new Date());
        focp3v1Dao.save(f);

        try {
            String address = getAddress(txId);
            FreeDriveGetResponse response = freeDriveComponnet.put(new FreeDriveGetRequest(address,f.getFilePath()));

            Knowledge knowledge = new Knowledge();
            knowledge.setId(SnowflakeIdWorker.getUUID());
            String[] dataValue;
            if(response.getUpdate() == null || response.getUpdate().size() == 0){
                String hexStr = HexStringUtil.hexStringToString(response.getPut().getData());
                ProtocolUtil.Result res = ProtocolUtil.getValue(hexStr,0,2);
                knowledge.setAuthor(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),2);
                knowledge.setType(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),2);
                knowledge.setTitle(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),8);
                knowledge.setContent(res.getValue());

            }else{
                int len = response.getUpdate().size();
                String hexStr = HexStringUtil.hexStringToString(response.getUpdate().get(len -1).getData());
                ProtocolUtil.Result res = ProtocolUtil.getValue(hexStr,0,2);
                knowledge.setAuthor(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),2);
                knowledge.setType(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),2);
                knowledge.setTitle(res.getValue());
                res = ProtocolUtil.getValue(hexStr,res.getEnd(),8);
                knowledge.setContent(res.getValue());
            }

            knowledge.setCreateDate(getTxDate(txId));
            knowledge.setDriveId(f.getFilePath());
            knowledge.setTxId(txId);

            knowledgeDao.save(knowledge);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取知识事变，失败信息为：{}，driveId = {}",e.getMessage(),f.getFilePath());
        }
    }

    private Date getTxDate(String txId) throws Exception{
        RawTransaction tx = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        return new Date(tx.getTime() * 1000);
    }

    private String getAddress(String txId) throws Exception{
        RawTransaction tx = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        RawInput input = tx.getVIn().get(0);
        String tmp = input.getTxId();
        int vout = input.getVOut();
        tx = (RawTransaction)fchdClient.getRawTransaction(tmp,true);
        return tx.getVOut().get(vout).getScriptPubKey().getAddresses().get(0);
    }
}
