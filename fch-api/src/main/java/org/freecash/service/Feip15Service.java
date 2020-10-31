package org.freecash.service;

import org.freecash.dao.IFeip15Dao;
import org.freecash.domain.Feip15;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author wanglint
 **/
@Service
public class Feip15Service {
    @Resource
    private IFeip15Dao feip15Dao;

    @Transactional
    public Feip15 save(Feip15 feip15){
        return feip15Dao.save(feip15);
    }

    public Optional<List<Feip15>> findAllByCid(String cid){
        return feip15Dao.findAllByCid(cid);
    }
}
