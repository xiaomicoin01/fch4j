package org.freecash.service;

import lombok.RequiredArgsConstructor;
import org.freecash.dao.IFchVinDao;
import org.freecash.domain.FchVin;
import org.freecash.domain.FchVout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
@RequiredArgsConstructor
public class FchVinService {

    private final IFchVinDao fchVinDao;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FchVin save(FchVin fchVin){
        return this.fchVinDao.save(fchVin);
    }

    public void saveAll(Collection<FchVin> fchVout){
        String sql = "INSERT INTO fch_vin(`n`, `txid`) VALUES (:n, :txId) ";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(fchVout);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }
    public void delete(FchVin fchVin){
        this.fchVinDao.deleteById(fchVin.getPid());
    }

    public List<FchVin> find(PageRequest page){
        Page<FchVin> res = this.fchVinDao.findAll(page);
        return res.hasContent() ? res.getContent() : new ArrayList<>();
    }
}
