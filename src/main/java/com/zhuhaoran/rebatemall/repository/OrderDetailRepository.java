package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName OrderDetailRepository
 * @date 2019/4/20
 * @description
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId (String orderId);

}
