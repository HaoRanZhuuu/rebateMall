package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.DeliveryAddress;
import com.zhuhaoran.rebatemall.repository.DeliveryAddressRepository;
import com.zhuhaoran.rebatemall.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.dom.DOMResult;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className DeliveryAddressServiceImpl
 * @date 2019/4/20
 * @description
 */
@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {


    @Autowired
    private DeliveryAddressRepository repository;

    @Override
    public DeliveryAddress findByIsDefault(String userId,Integer code) {
        return  repository.findByUserIdAndIsDefault(userId, code);
    }

    @Override
    public DeliveryAddress save(DeliveryAddress deliveryAddress) {
        return repository.save(deliveryAddress);
    }

    @Override
    public List<DeliveryAddress> list(String userId) {
        return repository.findByUserIdOrderByAddressIdDesc(userId);
    }

    @Override
    public DeliveryAddress findById(String userId,String addressId) {
        return repository.findByUserIdAndAddressId(userId, addressId);
    }

    @Override
    public void delete(DeliveryAddress deliveryAddress) {
        repository.delete(deliveryAddress);
    }
}
