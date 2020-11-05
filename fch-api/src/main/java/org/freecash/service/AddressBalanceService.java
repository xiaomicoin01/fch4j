package org.freecash.service;

import com.google.common.collect.Lists;
import org.freecash.dao.IAddressBalanceDao;
import org.freecash.domain.AddressBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AddressBalanceService {
    @Autowired
    private IAddressBalanceDao addressBalanceDao;

    /**
     * 根据地址列表查询余额
     * @param addresses 地址列表
     * @return 余额列表
     */
    public List<AddressBalance> getBalances(List<String> addresses){
        return addressBalanceDao.findAllByAddressIn(addresses).orElse(Lists.newArrayList());
    }

    @Transactional
    public void updateBalance(String address, BigDecimal balance){
        addressBalanceDao.updateBalance(address, balance);
    }
    public void save(AddressBalance addressBalance){
        addressBalanceDao.save(addressBalance);
    }
}
