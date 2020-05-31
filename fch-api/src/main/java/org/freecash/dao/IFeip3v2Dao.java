package org.freecash.dao;


import org.freecash.domain.Feip3v2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IFeip3v2Dao extends PagingAndSortingRepository<Feip3v2, String>, JpaSpecificationExecutor<Feip3v2> {

	List<Feip3v2> getAllByNickNameAndStatus(String nickName, boolean status);
	List<Feip3v2> getAllByNickName(String nickName);
	List<Feip3v2> getAllByAddressAndStatus(String address, boolean status);
}
