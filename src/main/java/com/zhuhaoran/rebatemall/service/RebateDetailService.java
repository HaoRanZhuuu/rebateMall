package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.RebateDetail;
import com.zhuhaoran.rebatemall.enums.RebateStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @interfaceName RebateDetailService
 * @date 2019/4/20
 * @description
 */
public interface RebateDetailService {

    public boolean create(String sellerId,String detailId,Integer rebateType,BigDecimal rebateMoney,String rebateInfo);

    public RebateDetail findByUserIdAndDetailId(String userId,String detailId);

    public RebateDetail save(RebateDetail rebateDetail);
}
