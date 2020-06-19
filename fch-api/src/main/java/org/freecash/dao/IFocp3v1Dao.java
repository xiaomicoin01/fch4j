package org.freecash.dao;


import org.freecash.domain.Focp3v3;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 知识
 * @author wanglint
 */
public interface IFocp3v1Dao extends PagingAndSortingRepository<Focp3v3, String>, JpaSpecificationExecutor<Focp3v3> {

}
