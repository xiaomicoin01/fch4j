package org.freecash.job;

import lombok.extern.slf4j.Slf4j;
import org.freecash.domain.FchVin;
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
import java.util.List;

/**
 * @author wanglint
 * @date 2020/7/1 8:20
 **/
@Component
@Configuration
@EnableScheduling
@Slf4j
public class UtxoUpdateJob {
    @Resource
    private FchVoutService fchVoutService;
    @Resource
    private FchVinService fchVinService;

    @Value("${btc.job.update.batch}")
    private int batch;

    @Scheduled(cron = "${btc.job.update.corn}")
    @Transactional
    public void updateUtxo(){
        PageRequest p = PageRequest.of(0,batch);
        List<FchVin> vinList = this.fchVinService.find(p);
        for (FchVin vin: vinList ) {
            this.fchVoutService.delete(vin.getTxId(),vin.getN());
            this.fchVinService.delete(vin);
        }
    }
}
