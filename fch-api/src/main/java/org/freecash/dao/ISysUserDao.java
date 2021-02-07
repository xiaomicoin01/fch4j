package org.freecash.dao;


import org.freecash.domain.SysUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface ISysUserDao extends PagingAndSortingRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser> {

    Optional<SysUser> findByUsername(String userName);
    Optional<SysUser> findByUsernameAndPassword(String userName,String password);
}
