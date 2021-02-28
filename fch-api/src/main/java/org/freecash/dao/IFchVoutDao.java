package org.freecash.dao;

import org.freecash.domain.FchVout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * UTXO模型
 * @author wanglint
 */
public interface IFchVoutDao extends PagingAndSortingRepository<FchVout, Integer>, JpaSpecificationExecutor<FchVout> {

    int deleteAllByTxIdAndN(String txId, Integer n);

    Page<FchVout> findAllByAddressAndTxIdNotIn(String address, List<String> txIds,Pageable page);
    Page<FchVout> findAllByTxIdNotIn(List<String> txIds,Pageable page);
    List<FchVout> findAllByPidIn(List<Integer> ids);
    List<FchVout> findAllByAddress(String address);
}
