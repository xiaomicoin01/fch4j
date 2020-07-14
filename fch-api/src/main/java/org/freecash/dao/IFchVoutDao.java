package org.freecash.dao;

import org.freecash.domain.FchVout;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * UTXO模型
 * @author wanglint
 */
public interface IFchVoutDao extends PagingAndSortingRepository<FchVout, String>, JpaSpecificationExecutor<FchVout> {

    int deleteAllByTxIdAndN(String txId, Integer n);
}
