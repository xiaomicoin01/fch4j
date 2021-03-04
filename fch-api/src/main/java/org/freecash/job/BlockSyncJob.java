package org.freecash.job;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.freecash.analysis.AnalysisDataComponent;
import org.freecash.core.domain.Block;
import org.freecash.core.domain.RawInput;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.api.BlockApi;
import org.freecash.constant.ConstantKey;
import org.freecash.core.domain.enums.ScriptTypes;
import org.freecash.domain.*;
import org.freecash.enm.TxTypeEnum;
import org.freecash.service.*;
import org.freecash.utils.HexStringUtil;
import org.freecash.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author wanglint
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class BlockSyncJob {
    @Value("${btc.job.batch}")
    private int batch;
    private final BlockInfoService blockInfoService;
    private final AnalysisDataComponent analysisDataComponent;
    private final BlockApi client;
    private final FchVoutService fchVoutService;
    private final FchVinService fchVinService;
    private final AddressBalanceService addressBalanceService;
    private final FchUserService fchUserService;
    private final FchUserTxRecordService fchUserTxRecordService;

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

        Map<String, BigDecimal> balance = Maps.newHashMap();
        Set<FchVin> vins = new HashSet<>();
        Set<FchVout> vouts = new HashSet<>();

        for (int i = totalInDb + 1; i <= end; i++) {
            String hash = this.client.getBlockHash(i);
            log.debug("查询区块:" + i + ",hash=" + hash);

            Block block = this.client.getBlock(hash);
            //保存区块信息
            List<String> txIds = block.getTx();
            for (String txid : txIds) {
                RawTransaction t = this.client.getRawTransaction(txid, true);
                for (RawInput in : t.getVIn()) {
                    if (StringUtil.isEmpty(in.getCoinbase())) {
                        String tx = in.getTxId();
                        int n = in.getVOut();
                        RawTransaction vinTx = this.client.getRawTransaction(tx, true);
                        RawOutput out = vinTx.getVOut().get(n);
                        String address = out.getScriptPubKey().getAddresses().get(0);
                        BigDecimal amount = out.getValue();

                        balance.put(address, balance.getOrDefault(address, BigDecimal.ZERO).subtract(amount));
                    }
                }

                List<RawOutput> outputs = t.getVOut();
                CommonTxUtil.processVoutAndVin(t, vins, vouts);
                for (RawOutput out : outputs) {

                    if (out.getScriptPubKey().getType() == ScriptTypes.PUB_KEY_HASH) {
                        BigDecimal amount = out.getValue();
                        String address = out.getScriptPubKey().getAddresses().get(0);
                        balance.put(address, balance.getOrDefault(address, BigDecimal.ZERO).add(amount));
                    }

                    String asm = out.getScriptPubKey().getAsm();
                    if (StringUtils.isEmpty(asm)) {
                        continue;
                    }
                    if (!asm.startsWith(ConstantKey.OP_RETURN)) {
                        continue;
                    }
                    String[] temp = asm.split("\\s");
                    String protocolValue = HexStringUtil.hexStringToString(temp[1]);
                    try {
                        analysisDataComponent.analysis(protocolValue, txid);
                    } catch (Exception e) {
                        log.error("协议内容：{}，处理失败，信息为：{}", protocolValue, e.getMessage());
                    }
                }
            }
        }
        info.setValue(Integer.toString(end));
        blockInfoService.saveBlock(info);
        fchVinService.saveAll(vins);
//        for(FchVin item : vins){
//            fchVinService.save(item);
//            RawTransaction t = this.client.getRawTransaction(item.getTxId(), true);
//            String address = t.getVOut().get(item.getN()).getScriptPubKey().getAddresses().get(0);
//            FchUser user = fchUserService.findUserByAddress(address);
//            if(Objects.nonNull(user)){
//                RawTransaction t1  = this.client.getRawTransaction(item.getParentTxId(), true);
//                t1.getVOut().forEach(i->{
//                    FchUserTxRecord record = FchUserTxRecord.builder().address(i.getScriptPubKey().getAddresses().get(0))
//                            .inOrOut(TxTypeEnum.OUT)
//                            .txDate(new BigDecimal(t.getTime())).txId(item.getTxId())
//                            .amount(i.getValue())
//                            .build();
//                    fchUserTxRecordService.save(record);
//                });
//            }
//        }

        fchVoutService.saveAll(vouts);
        if(end > 610000){
            List<String> addressList = fchUserService.getAll().stream().map(FchUser::getAddress).collect(Collectors.toList());
            vouts.forEach(item->{
                String address = item.getAddress();
                if(addressList.contains(address)){
                    FchUserTxRecord record = FchUserTxRecord.builder().toAddress(address).inOrOut(TxTypeEnum.IN)
                            .txDate(new BigDecimal(item.getOnLineTime())).txId(item.getTxId()).amount(item.getAmount())
                            .build();
                    fchUserTxRecordService.save(record);
                }
            });
        }

        updateAddress(balance);
    }

    /**
     * 更新或者保存地址上的币量
     *
     * @param balance 余额变动信息
     */
    private void updateAddress(Map<String, BigDecimal> balance) {
        List<String> addresses = Lists.newArrayList(balance.keySet());

        Map<String, AddressBalance> existBalance = addressBalanceService.getBalances(addresses).stream()
                .collect(Collectors.toMap(AddressBalance::getAddress, item->item));
        balance.forEach((address, amount) -> {
            if(existBalance.containsKey(address)){
                AddressBalance exist = existBalance.get(address);
                exist.setAmount(exist.getAmount().add(amount));
                addressBalanceService.save(exist);
            }else{
                AddressBalance ab = AddressBalance.builder().address(address).amount(amount).build();
                addressBalanceService.save(ab);
            }
        });

    }
}