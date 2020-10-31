package org.freecash.analysis.impl;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.dao.IFocp3v1Dao;
import org.freecash.domain.Focp3v3;
import org.freecash.domain.ProtocolHeader;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 知库
 * @author wanglint
 **/
@Component
@Log4j2
public class Focp3v3ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private FchdClient fchdClient;
    @Resource
    private IFocp3v1Dao focp3v1Dao;

    /**
     * CID协议类型
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Lists.newArrayList(
                new ProtocolHeader("FOCP","3","6")

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

        Focp3v3 f = new Focp3v3();
        f.setId(SnowflakeIdWorker.getUUID());
        f.setProtocolName(value[0]);
        f.setProtocolNo(Integer.valueOf(value[1]));
        f.setProtocolVersion(value[2]);
        f.setDataHash(value[value.length-2]);
        f.setFilePath(value[value.length-1]);

        f.setCreateDate(new Date());
        focp3v1Dao.save(f);

        try {
            String address = getAddress(txId);
//            FreeDriveGetResponse response = focp3FreeDriveComponent.get(new FreeDriveGetRequest(address,f.getFilePath()));
//
//            Knowledge knowledge = new Knowledge();
//            knowledge.setId(SnowflakeIdWorker.getUUID());
//            String[] dataValue;
//            String dataHex = "";
//            String metaDataHex = "";
//
//            if(response.getUpdate() == null || response.getUpdate().size() == 0){
//                dataHex = response.getPut().getData();
//                metaDataHex = response.getPut().getMetadata();
//            }else{
//                int len = response.getUpdate().size();
//                dataHex = response.getUpdate().get(len -1).getData();
//                metaDataHex = response.getUpdate().get(len -1).getMetadata();
//            }
//
//            ProtocolUtil.Result res = ProtocolUtil.getValue(dataHex,0,2);
//            knowledge.setAuthor(res.getValue());
//            res = ProtocolUtil.getValue(dataHex,res.getEnd(),2);
//            knowledge.setType(res.getValue());
//            res = ProtocolUtil.getValue(dataHex,res.getEnd(),2);
//            knowledge.setTitle(res.getValue());
//            res = ProtocolUtil.getValue(dataHex,res.getEnd(),8);
//            knowledge.setContent(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,0,1);
//            knowledge.setProtocolName(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),1);
//            knowledge.setProtocolNo(Integer.parseInt(res.getValue()));
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),1);
//            knowledge.setProtocolVersion(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),1);
//            knowledge.setAction(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),2);
//            knowledge.setDataHash(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),2);
//            knowledge.setEncrypt(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),3);
//            knowledge.setEncryptedPwd(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),2);
//            knowledge.setLanguage(res.getValue());
//
//            res = ProtocolUtil.getValue(metaDataHex,res.getEnd(),2);
//            knowledge.setDriveId(res.getValue());
//
//            knowledge.setCreateDate(getTxDate(txId));
//
//            knowledgeDao.save(knowledge);
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
