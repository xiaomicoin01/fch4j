package org.freecash.dao;

import org.freecash.domain.FchVout;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * UTXO模型
 * @author wanglint
 */
public interface IFchVoutDao extends PagingAndSortingRepository<FchVout, String>, JpaSpecificationExecutor<FchVout> {

    /**
     * 修改消费状态
     * @param txId 交易ID
     * @param n 索引
     * @param spent 消费状态
     */
    @Modifying
    @Query("update FchVout a set a.spent = (:spent) where a.txId = (:txId) and a.n = (:n)")
    void changeSpentStatus(@Param("txId")String txId,@Param("n")int n,@Param("spent")boolean spent);

    void deleteAllBySpent(boolean spent);
}
