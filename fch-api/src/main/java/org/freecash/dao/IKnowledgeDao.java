package org.freecash.dao;


import org.freecash.domain.Feip3v2;
import org.freecash.domain.Knowledge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 知识
 * @author wanglint
 */
public interface IKnowledgeDao extends PagingAndSortingRepository<Knowledge, String>, JpaSpecificationExecutor<Knowledge> {

}
