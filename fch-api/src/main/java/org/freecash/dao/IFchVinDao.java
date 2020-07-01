package org.freecash.dao;

import org.freecash.domain.FchVin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * UTXO模型
 * @author wanglint
 */
public interface IFchVinDao extends PagingAndSortingRepository<FchVin, String>, JpaSpecificationExecutor<FchVin> {

}
