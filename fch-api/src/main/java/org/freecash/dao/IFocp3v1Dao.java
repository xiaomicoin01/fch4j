package org.freecash.dao;


import org.freecash.domain.Focp3v1;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 知识
 * @author wanglint
 */
public interface IFocp3v1Dao extends PagingAndSortingRepository<Focp3v1, String>, JpaSpecificationExecutor<Focp3v1> {

}
