package org.freecash.service;

import org.freecash.dao.IFchVinDao;
import org.freecash.domain.FchVin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wanglint
 * @date 2020/5/29 10:29
 **/
@Service
public class FchVinService {
    @Resource
    private IFchVinDao fchVinDao;

    public FchVin save(FchVin fchVin){
        return this.fchVinDao.save(fchVin);
    }

    public void delete(FchVin fchVin){
        this.fchVinDao.deleteById(fchVin.getPid());
    }

    public List<FchVin> find(PageRequest page){
        Page<FchVin> res = this.fchVinDao.findAll(page);
        return res.hasContent() ? res.getContent() : new ArrayList<>();
    }
}
