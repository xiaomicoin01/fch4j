package org.freecash.service;

import org.freecash.dao.IFeip3v2Dao;
import org.freecash.domain.Feip3v2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class Feip3v2Service {
    @Resource
    private IFeip3v2Dao feip3v2Dao;

    public Feip3v2 getFeip3v2(String nickName){
        List<Feip3v2> res = feip3v2Dao.getAllByNickNameAndStatus(nickName,true);
        if(res == null || res.size() == 0){
            return null;
        }
        return res.get(0);
    }
}
