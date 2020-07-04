package org.freecash.dao;


import org.freecash.domain.Feip1v3;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IFeip1v3Dao extends PagingAndSortingRepository<Feip1v3, String>, JpaSpecificationExecutor<Feip1v3> {

    Optional<List<Feip1v3>> findAllByDataHashAndTxId(String dataHash,String txId);
}
