package org.freecash.dao;

import org.freecash.domain.Feip17;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author wanglint
 */
public interface IFeip17Dao extends PagingAndSortingRepository<Feip17, String>, JpaSpecificationExecutor<Feip17> {

}
