package org.freecash.dao;


import org.freecash.domain.Feip15;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IFeip15Dao extends PagingAndSortingRepository<Feip15, String>, JpaSpecificationExecutor<Feip15> {

    Optional<List<Feip15>> findAllByCid(String cid);
}
