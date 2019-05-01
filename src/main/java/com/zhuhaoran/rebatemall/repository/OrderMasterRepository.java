package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName OrderMasterRepository
 * @date 2019/4/20
 * @description
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    OrderMaster findByOrderId (String orderId);

    List<OrderMaster> findByUserId (String userId);
}
