package org.freecash.service;

import org.freecash.dao.IFeip3v2Dao;
import org.freecash.dao.IFeip6v2Dao;
import org.freecash.domain.Feip3v2;
import org.freecash.domain.Feip6v2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class Feip6v2Service {
    @Resource
    private IFeip6v2Dao feip6v2Dao;

    @Transactional
    public Feip6v2 save(Feip6v2 feip6v2){
        return feip6v2Dao.save(feip6v2);
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(String fromAddress, String toAddress){
        feip6v2Dao.deleteAllByAuthFromAddressAndAuthToAddress(fromAddress,toAddress);
    }
}
