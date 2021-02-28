package org.freecash.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.freecash.core.client.FchdClient;
import org.freecash.core.util.CollectionUtils;
import org.freecash.dao.IFchVoutDao;
import org.freecash.domain.FchVout;
import org.freecash.dto.UtxoRequest;
import org.freecash.dto.UtxoResponse;
import org.freecash.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
@RequiredArgsConstructor
public class FchVoutService {

    private final IFchVoutDao fchVoutDao;
    private final FchdClient fchdClient;

    public FchVout save(FchVout fchVout){
        return this.fchVoutDao.save(fchVout);
    }

    public int delete(String txId, int n){
        return this.fchVoutDao.deleteAllByTxIdAndN(txId,n);
    }

    public UtxoResponse query(UtxoRequest request) throws Exception{

        List<String> rawMemPool = fchdClient.getRawMemPool();
        if(rawMemPool.isEmpty()){
            rawMemPool.add("aaa");
        }
        Page<FchVout> vouts;
        if(StringUtil.isEmpty(request.getAddress())){
            vouts = fchVoutDao.findAllByTxIdNotIn(rawMemPool, PageRequest.of(request.getPageNumber()-1, request.getPageSize()));
        }else{
            vouts = fchVoutDao.findAllByAddressAndTxIdNotIn(request.getAddress(),rawMemPool, PageRequest.of(request.getPageNumber()-1, request.getPageSize()));
        }

        if(!vouts.hasContent()){
            return UtxoResponse.builder().pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                    .count(0L).data(Lists.newArrayList()).build();
        }
        List<UtxoResponse.Trans> trans = vouts.stream().map(item-> {
            Long end = new Date().getTime();
            int coinDay = ((Double)((end/1000 - item.getOnLineTime())/3600/24 * item.getAmount().doubleValue())).intValue();
            return UtxoResponse.Trans.builder().amount(item.getAmount()).txid(item.getTxId())
                            .n(item.getN()).coinDay(coinDay).pid(item.getPid())
                            .build();
        }).collect(Collectors.toList());
        return UtxoResponse.builder().pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                .count(vouts.getTotalElements()).data(trans).build();
    }

    public List<FchVout> findByIds(List<Integer> ids){
        return fchVoutDao.findAllByPidIn(ids);
    }

    public List<FchVout> findByAddress(String address){
        return fchVoutDao.findAllByAddress(address);
    }
}
