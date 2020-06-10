package org.freecash.dao;

import org.freecash.domain.BlockInfo;
import org.freecash.domain.FchVout;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IFchVoutDao extends PagingAndSortingRepository<FchVout, String>, JpaSpecificationExecutor<FchVout> {
    void deleteByTxIdAndN(String txId,int n);
}
