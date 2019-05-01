package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.RebateDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhuHaoran
 * @interfaceName RebateDetailRepository
 * @date 2019/4/20
 * @description
 */
public interface RebateDetailRepository extends JpaRepository<RebateDetail, String> {

    RebateDetail findByUserIdAndDetailId(String userId,String detailId);

}
