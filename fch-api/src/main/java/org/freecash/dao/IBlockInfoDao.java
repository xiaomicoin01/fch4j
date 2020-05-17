package org.freecash.dao;


import org.freecash.domain.BlockInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IBlockInfoDao extends PagingAndSortingRepository<BlockInfo, String>, JpaSpecificationExecutor<BlockInfo> {
	/**
	 * 根据key查询区块信息
	 * @param key key值
	 * @return 区块信息
	 */
	Optional<BlockInfo> getByKey(String key);
}
