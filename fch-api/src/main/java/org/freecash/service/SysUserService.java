package org.freecash.service;

import org.freecash.dao.ISysUserDao;
import org.freecash.domain.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class SysUserService {
    @Resource
    private ISysUserDao sysUserDao;

    public SysUser add(SysUser user){
        return sysUserDao.save(user);
    }

    public SysUser findUserByUserName(String username){
        return sysUserDao.findByUsername(username).orElse(null);
    }

    public SysUser findUser(String username,String password){
        return sysUserDao.findByUsernameAndPassword(username,password).orElse(null);
    }
}
