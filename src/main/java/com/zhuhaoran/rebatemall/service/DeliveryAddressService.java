package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.DeliveryAddress;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName DeliveryAddressService
 * @date 2019/4/20
 * @description
 */
public interface DeliveryAddressService {

    public DeliveryAddress findByIsDefault(String userId , Integer code);

    public DeliveryAddress save (DeliveryAddress deliveryAddress);

    public List<DeliveryAddress> list (String userId);

    public DeliveryAddress findById (String userId,String addressId);

    public void delete (DeliveryAddress deliveryAddress);
}
