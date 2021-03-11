package org.freecash.dao;

import org.freecash.domain.FchUserTxRecord;
import org.freecash.enm.TxTypeEnum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IFchUserTxRecordDao extends PagingAndSortingRepository<FchUserTxRecord, Integer>, JpaSpecificationExecutor<FchUserTxRecord> {

    Optional<List<FchUserTxRecord>> findAllByTxIdAndType(String txId, TxTypeEnum type);
}
