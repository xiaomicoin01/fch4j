package org.freecash.job;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.freecash.analysis.AnalysisDataComponent;
import org.freecash.core.domain.Block;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.api.BlockApi;
import org.freecash.constant.ConstantKey;
import org.freecash.dao.IFchVoutDao;
import org.freecash.domain.*;
import org.freecash.service.*;
import org.freecash.utils.HexStringUtil;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author wanglint
 * @date 2019/10/14
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class BlockSyncJob {
    @Value("${btc.job.batch}")
    private int batch;
    @Resource
    private BlockInfoService blockInfoService;
    @Resource
    private AnalysisDataComponent analysisDataComponent;
    @Resource
    private BlockApi client;
    @Resource
    private IFchVoutDao fchVoutDao;

    @Scheduled(cron = "${btc.job.corn}")
    @Transactional(rollbackFor = Exception.class)
    public void run() throws Exception {
        int totalInWallet = client.getBlockHeight();

        BlockInfo info = blockInfoService.getBlockSyncHeight();
        int totalInDb = Integer.parseInt(info.getValue());

        if (totalInDb > totalInWallet) {
            log.debug("数据库中存放的高度大于钱包高度，不在同步区块");
            return;
        }

        int end = totalInDb + batch;
        if (totalInWallet < end) {
            end = totalInWallet;
        }

        for (int i = totalInDb + 1; i <= end; i++) {
            String hash = this.client.getBlockHash(i);
            log.debug("查询区块:" + i + ",hash=" + hash);
            processBlock(hash);
        }
        info.setValue(Integer.toString(end));
        blockInfoService.saveBlock(info);
    }

    /**
     * 处理区块
     *
     * @param hash 区块的hash值
     * @throws Exception
     */
    private void processBlock(String hash) throws Exception {

        Block block = (Block) this.client.getBlock(hash);

        //保存区块信息
        List<String> txIds = block.getTx();
        for (String txid : txIds) {
            RawTransaction t = (RawTransaction) this.client.getRawTransaction(txid, true);

            List<RawOutput> outputs = t.getVOut();
            processVoutAndVin(t);
            for (RawOutput out : outputs) {
                String asm = out.getScriptPubKey().getAsm();
                if (StringUtils.isEmpty(asm)) {
                    continue;
                }
                if (!asm.startsWith(ConstantKey.OP_RETURN)) {
                    continue;
                }
                String[] temp = asm.split("\\s");
                String protocolValue = HexStringUtil.hexStringToString(temp[1]);
                analysisDataComponent.analysis(protocolValue, txid);
            }
        }
    }

    private void processVoutAndVin(RawTransaction t){
        List<RawOutput> outputs = t.getVOut();
        for(RawOutput out : outputs){
            BigDecimal amount =  out.getValue();
            if(Objects.equals(amount,BigDecimal.ZERO)){
                return;
            }
            List<String> addresses = out.getScriptPubKey().getAddresses();

            if(addresses == null || addresses.size() == 0){
                return;
            }
            FchVout fchVout = new FchVout(
                    SnowflakeIdWorker.getUUID(),
                    t.getTxId(),
                    addresses.get(0),
                    out.getN(),
                    amount
            );
            fchVoutDao.save(fchVout);
        }

        for(RawInput input : t.getVIn()){
            String txId = input.getTxId();
            if(Objects.isNull(txId)){
                continue;
            }
            int n = input.getVOut();
            fchVoutDao.deleteByTxIdAndN(txId,n);
        }
    }
}