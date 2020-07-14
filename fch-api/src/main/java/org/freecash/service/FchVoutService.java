package org.freecash.service;

import org.freecash.dao.IFchVoutDao;
import org.freecash.domain.FchVout;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public int delete(String txId, int n){
        return this.fchVoutDao.deleteAllByTxIdAndN(txId,n);
    }

}
