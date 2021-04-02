package org.freecash.dao;


import org.freecash.domain.FchFile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFchFileDao extends PagingAndSortingRepository<FchFile, Integer>, JpaSpecificationExecutor<FchFile> {

}
