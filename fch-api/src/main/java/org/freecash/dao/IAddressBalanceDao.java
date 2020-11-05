package org.freecash.dao;


import org.freecash.domain.AddressBalance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 区块Dao
 * @author wanglint
 */
public interface IAddressBalanceDao extends PagingAndSortingRepository<AddressBalance, Integer>, JpaSpecificationExecutor<AddressBalance> {
	/**
	 * 根据地址查询地址余额
	 * @param address 地址
	 * @return 地址余额
	 */
	Optional<AddressBalance> findByAddress(String address);

	/**
	 * 根据地址查询地址余额
	 * @param addresses 地址
	 * @return 地址余额
	 */
	Optional<List<AddressBalance>> findAllByAddressIn(List<String> addresses);

	@Query("update AddressBalance set amount = :balance where address = :address")
	void updateBalance(@Param("address") String address, @Param("balance") BigDecimal balance);
}
