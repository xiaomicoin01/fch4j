package org.freecash.dao;


import org.freecash.domain.Feip3;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IFeip3Dao extends PagingAndSortingRepository<Feip3, Integer>, JpaSpecificationExecutor<Feip3> {

	List<Feip3> getAllByNameAndStatus(String nickName, boolean status);
	List<Feip3> getAllByName(String nickName);
	List<Feip3> getAllByAddressAndStatus(String address, boolean status);
}
