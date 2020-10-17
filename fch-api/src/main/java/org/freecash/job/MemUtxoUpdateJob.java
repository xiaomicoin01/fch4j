package org.freecash.job;

import lombok.extern.slf4j.Slf4j;
import org.freecash.core.CommunicationException;
import org.freecash.core.FreecashException;
import org.freecash.core.client.FchdClient;
import org.freecash.core.domain.RawOutput;
import org.freecash.core.domain.RawTransaction;
import org.freecash.domain.FchVin;
import org.freecash.domain.FchVout;
import org.freecash.service.FchVinService;
import org.freecash.service.FchVoutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author wanglint
 * @date 2020/7/1 8:20
 **/
@Component
@Configuration
@EnableScheduling
@Slf4j
public class MemUtxoUpdateJob {
    @Resource
    private FchdClient client;
    @Resource
    private FchVoutService fchVoutService;
    @Resource
    private FchVinService fchVinService;

    @Value("${btc.job.memUpdate.batch}")
    private int batch;

    @Scheduled(cron = "${btc.job.memUpdate.corn}")
    @Transactional(rollbackOn = Exception.class)
    public void updateUtxo() throws Exception{

        Set<FchVin> vins = new HashSet<>();
        Set<FchVout> vouts = new HashSet<>();

        List<String> txIds = client.getRawMemPool();
        for (String txid : txIds) {
            try {
                Object obj = this.client.getRawTransaction(txid, true);
                if(Objects.nonNull(obj)){
                    RawTransaction t = (RawTransaction)obj;
                    CommonTxUtil.processVoutAndVin(t,vins,vouts);
                }else{
                    log.error("从内存中解析,txid:{},失败",txid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        vins.forEach(vin->{fchVinService.save(vin);});
        vouts.forEach(vout->{fchVoutService.save(vout);});
    }
}
