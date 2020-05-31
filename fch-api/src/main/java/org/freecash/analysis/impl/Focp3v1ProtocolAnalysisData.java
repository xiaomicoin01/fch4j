package org.freecash.analysis.impl;

import lombok.extern.log4j.Log4j2;
import org.freecash.analysis.IAnalysisData;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawTransaction;
import org.freecash.dao.IFocp3v1Dao;
import org.freecash.dao.IKnowledgeDao;
import org.freecash.domain.Focp3v1;
import org.freecash.domain.Knowledge;
import org.freecash.domain.ProtocolHeader;
import org.freecash.utils.HexStringUtil;
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
public class Focp3v1ProtocolAnalysisData implements IAnalysisData {
    @Resource
    private FchdClient fchdClient;
    @Resource
    private IFocp3v1Dao focp3v1Dao;
    @Resource
    private IKnowledgeDao knowledgeDao;

    /**
     * CID协议类型
     *
     * @return
     */
    @Override
    public List<ProtocolHeader> getType() {
        return Arrays.asList( new ProtocolHeader("FEIP","2"));
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
        f.setDataHash(value[7]);
        f.setFilePath(value[8]);
        f.setStatus(true);
        f.setCreateDate(new Date());
        focp3v1Dao.save(f);

        String kg = HexStringUtil.hexStringToString(f.getDataHash());
        value = kg.split("\\|");
        Knowledge knowledge = new Knowledge();
        knowledge.setId(SnowflakeIdWorker.getUUID());
        knowledge.setAuthor(value[0]);
        knowledge.setType(value[1]);
        knowledge.setTitle(value[2]);
        if(value.length>3){
            knowledge.setContent(value[3]);
        }

        knowledge.setCreateDate(getTxDate(txId));
        knowledge.setDriveId(f.getFilePath());
        knowledge.setTxId(txId);
        knowledgeDao.save(knowledge);
    }

    public Date getTxDate(String txId) throws Exception{
        RawTransaction tx = (RawTransaction)fchdClient.getRawTransaction(txId,true);
        return new Date(tx.getTime() * 1000);
    }
}
