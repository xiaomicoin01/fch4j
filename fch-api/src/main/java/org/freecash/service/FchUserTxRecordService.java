package org.freecash.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.freecash.dao.IFchUserTxRecordDao;
import org.freecash.domain.FchUserTxRecord;
import org.freecash.dto.FchUserTxRecordRequest;
import org.freecash.dto.FchUserTxRecordResponse;
import org.freecash.enm.TxTypeEnum;
import org.freecash.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FchUserTxRecordService {
    private final IFchUserTxRecordDao fchUserTxRecordDao;

    public void save(FchUserTxRecord record){
        fchUserTxRecordDao.save(record);
    }

    public List<FchUserTxRecord> getRecord(String txId, TxTypeEnum type){
        return fchUserTxRecordDao.findAllByTxIdAndType(txId,type).orElse(null);
    }

    public FchUserTxRecordResponse query(FchUserTxRecordRequest request){
        Page<FchUserTxRecord> result =  fchUserTxRecordDao.findAll((root, criteriaQuery, cb) ->{
            List<Predicate> predicates = Lists.newArrayList();
            if(StringUtil.notEmpty(request.getAddress())){
                Predicate p = cb.equal(root.get("fromAddress").as(String.class), request.getAddress());
                predicates.add(p);
                p = cb.equal(root.get("toAddress").as(String.class), request.getAddress());
                predicates.add(p);
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        } , PageRequest.of(request.getPageNumber()-1, request.getPageSize(), Sort.Direction.DESC, "pid"));
        if(result.hasContent()){
            List<FchUserTxRecordResponse.Record> cids = result.getContent().stream().map(item -> {
                FchUserTxRecordResponse.Record cid = FchUserTxRecordResponse.Record.builder().build();
                BeanUtils.copyProperties(item, cid);
                cid.setAddress(item.getToAddress());
                cid.setTxDate(new Date(item.getTxDate().longValue()*1000));
                return cid;
            }).collect(Collectors.toList());
            return  FchUserTxRecordResponse.builder().count(result.getTotalElements())
                    .pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                    .records(cids).build();
        }else{
            return  FchUserTxRecordResponse.builder().count(0L)
                    .pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                    .records(Lists.newArrayList()).build();
        }
    }
}
