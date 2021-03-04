package org.freecash.service;

import com.google.common.collect.Lists;
import org.freecash.core.util.FreeCashUtil;
import org.freecash.dao.IFchUserDao;
import org.freecash.domain.FchUser;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

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

    public FchUser findUserByCid(String cid){
        return fchUserDao.findByNickname(cid).orElse(null);
    }

    public FchUser findUserByAddress(String address){
        return fchUserDao.findByAddress(address).orElse(null);
    }

    public List<FchUser> getAll(){
        return fchUserDao.findAll((root, criteriaQuery, cb) ->{
            List<Predicate> predicates = Lists.newArrayList();
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public FchUser findUser(String username, String password){
        return fchUserDao.findByUsernameAndPassword(username,password).orElse(null);
    }

    public FchUser createUser(String userName) throws Exception{
        String pwd = SnowflakeIdWorker.getUUID();
        return createUser(userName,pwd);
    }

    public FchUser createUser(String userName, String password) throws Exception{
        List<String> info =  FreeCashUtil.getFreecashInfo();
        FchUser user = FchUser.builder().username(userName).password(password).address(info.get(1))
                .nickname(userName).privkey(info.get(0)).build();
        user = fchUserDao.save(user);
        return user;
    }
}
