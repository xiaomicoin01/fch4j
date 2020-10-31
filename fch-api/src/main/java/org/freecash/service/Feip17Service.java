package org.freecash.service;

import org.freecash.dao.IFeip17Dao;
import org.freecash.domain.Feip17;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author wanglint
 **/
@Service
public class Feip17Service {
    @Resource
    private IFeip17Dao feip17Dao;

    @Transactional
    public Feip17 save(Feip17 feip17){
        return feip17Dao.save(feip17);
    }

}
