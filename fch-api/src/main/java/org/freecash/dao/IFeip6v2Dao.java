package org.freecash.dao;


import org.freecash.domain.Feip6v2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * 区块Dao
 *
 * @author wanglint
 */
public interface IFeip6v2Dao extends PagingAndSortingRepository<Feip6v2, String>, JpaSpecificationExecutor<Feip6v2> {

    void deleteAllByAuthFromAddressAndAuthToAddress(String fromAddress, String toAddress);
    void deleteAllByAuthFromAddress(String address);

    Optional<List<Feip6v2>> findAllByAuthFromAddressAndAuthToAddress(String fromAddress, String toAddress);


}
