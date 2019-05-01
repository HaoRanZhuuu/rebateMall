package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.RebateDetail;
import com.zhuhaoran.rebatemall.enums.RebateStatusEnum;
import com.zhuhaoran.rebatemall.repository.RebateDetailRepository;
import com.zhuhaoran.rebatemall.service.RebateDetailService;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className RebateDetailServiceImpl
 * @date 2019/4/20
 * @description
 */

@Service
public class RebateDetailServiceImpl implements RebateDetailService {

    @Autowired
    RebateDetailRepository repository;

    @Override
    public boolean create(String sellerId, String detailId, Integer rebateType, BigDecimal rebateMoney, String rebateInfo) {
        RebateDetail rebateDetail = new RebateDetail();
        rebateDetail.setRebateId(IdKeyGenerate.getIdKey());
        rebateDetail.setUserId(sellerId);
        rebateDetail.setDetailId(detailId);
        rebateDetail.setRebateType(rebateType);
        rebateDetail.setRebateMoney(rebateMoney);
        rebateDetail.setRebateInfo(rebateInfo);
        try {
            repository.save(rebateDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /*private String rebateId;
        private String detailId;
        private String userId;
        private Integer rebateType;
        private Integer rebateStatus = RebateStatusEnum.WAIT.getCode();
        private BigDecimal rebateMoney;
        private String rebateInfo;
        private Date createTime;
        private Date updateTime;*/
    }

    @Override
    public RebateDetail findByUserIdAndDetailId(String userId, String detailId) {
        return repository.findByUserIdAndDetailId(userId,detailId);
    }

    @Override
    public RebateDetail save(RebateDetail rebateDetail) {
        return repository.save(rebateDetail);
    }
}
