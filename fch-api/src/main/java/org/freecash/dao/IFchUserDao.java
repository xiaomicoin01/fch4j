package org.freecash.dao;


import org.freecash.domain.FchUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IFchUserDao extends PagingAndSortingRepository<FchUser, Integer>, JpaSpecificationExecutor<FchUser> {

    Optional<FchUser> findByUsername(String userName);
    Optional<FchUser> findByNickname(String nickName);
    Optional<FchUser> findByAddress(String address);
    Optional<FchUser> findByUsernameAndPassword(String userName, String password);
}
