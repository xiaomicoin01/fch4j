package org.freecash.service;

import org.freecash.dao.IFchVoutDao;
import org.freecash.dao.IFeip3v2Dao;
import org.freecash.domain.FchVout;
import org.freecash.domain.Feip3v2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class FchVoutService {
    @Resource
    private IFchVoutDao fchVoutDao;

    public FchVout save(FchVout fchVout){
        return this.fchVoutDao.save(fchVout);
    }

    public void delete(String txId, int n){
        this.fchVoutDao.changeSpentStatus(txId,n,true);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveAndDelete(Set<FchVout> delOuts,Set<FchVout> saveOuts){
        for (FchVout vout : delOuts){
            fchVoutDao.changeSpentStatus(vout.getTxId(),vout.getN(),true);
        }
        for (FchVout vout: saveOuts) {
            fchVoutDao.save(vout);
        }
        fchVoutDao.deleteAllBySpent(true);
    }
}
