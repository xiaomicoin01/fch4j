package org.freecash.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.freecash.dao.IFeip3Dao;
import org.freecash.domain.Feip3;
import org.freecash.dto.CidRequest;
import org.freecash.dto.CidResponse;
import org.freecash.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wanglint
 **/
@Service
@RequiredArgsConstructor
public class Feip3Service {

    private final IFeip3Dao feip3Dao;

    public Feip3 getFeip3(String nickName){
        List<Feip3> res = feip3Dao.getAllByNameAndStatus(nickName,true);
        if(res == null || res.size() == 0){
            return null;
        }
        return res.get(0);
    }

    public Feip3 getFeip3ByAddress(String address){
        List<Feip3> res = feip3Dao.getAllByAddressAndStatus(address,true);
        if(res == null || res.size() == 0){
            return null;
        }
        return res.get(0);
    }

    public CidResponse query(CidRequest request){
        Page<Feip3> result =  feip3Dao.findAll((root, criteriaQuery, cb) ->{
            List<Predicate> predicates = Lists.newArrayList();
            if(StringUtil.notEmpty(request.getNickName())){
                Predicate p = cb.like(root.get("name").as(String.class), "%"+request.getNickName().replaceAll("_","\\_")+"%");
                predicates.add(p);
            }
            if(StringUtil.notEmpty(request.getAddress())){
                Predicate p = cb.equal(root.get("address").as(String.class), request.getAddress());
                predicates.add(p);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        } , PageRequest.of(request.getPageNumber()-1, request.getPageSize(), Sort.Direction.DESC, "createDate"));
        if(result.hasContent()){
            List<CidResponse.Cid> cids = result.getContent().stream().map(item -> {
                CidResponse.Cid cid = CidResponse.Cid.builder().build();
                BeanUtils.copyProperties(item, cid);
                cid.setNickName(item.getName());
                cid.setTxId(item.getTxHash());
                return cid;
            }).collect(Collectors.toList());
            return  CidResponse.builder().count(result.getTotalElements())
                    .pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                    .data(cids).build();
        }else{
            return  CidResponse.builder().count(0L)
                    .pageNumber(request.getPageNumber()).pageSize(request.getPageSize())
                    .data(Lists.newArrayList()).build();
        }

    }
}
