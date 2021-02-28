package org.freecash.dao;

import org.freecash.domain.FchUserTxRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFchUserTxRecordDao extends PagingAndSortingRepository<FchUserTxRecord, Integer>, JpaSpecificationExecutor<FchUserTxRecord> {
    
}
