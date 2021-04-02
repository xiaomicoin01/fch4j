package org.freecash.service;

import lombok.RequiredArgsConstructor;
import org.freecash.dao.IFchFileDao;
import org.freecash.domain.FchFile;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FchFileService {

    private final IFchFileDao fchFileDao;

    public FchFile add(FchFile fchFile){
        return fchFileDao.save(fchFile);
    }

    public FchFile get(int id){
        return fchFileDao.findById(id).orElse(null);
    }

    public void delete(FchFile fchFile){
        fchFileDao.delete(fchFile);
    }
}
