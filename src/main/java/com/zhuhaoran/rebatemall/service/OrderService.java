package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import com.zhuhaoran.rebatemall.dataobject.OrderMaster;
import com.zhuhaoran.rebatemall.dto.OrderDto;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName OrderService
 * @date 2019/4/20
 * @description
 */
public interface OrderService {

    public String create(OrderDto orderDto);

    public OrderMaster findByOrderId(String orderId);

    public List<OrderDetail> findListByOrderId (String orderId);

    public OrderMaster save (OrderMaster orderMaster);

    public List<OrderMaster> list(String userId);
}
