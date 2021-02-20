package org.freecash.service;

import org.freecash.dao.IFchUserDao;
import org.freecash.domain.FchUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class FchUserService {
    @Resource
    private IFchUserDao fchUserDao;

    public FchUser add(FchUser user){
        return fchUserDao.save(user);
    }

    public FchUser findUserByUserName(String username){
        return fchUserDao.findByUsername(username).orElse(null);
    }

    public FchUser findUser(String username, String password){
        return fchUserDao.findByUsernameAndPassword(username,password).orElse(null);
    }
}
