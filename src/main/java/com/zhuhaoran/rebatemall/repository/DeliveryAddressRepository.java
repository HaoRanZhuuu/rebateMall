package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.DeliveryAddress;
import com.zhuhaoran.rebatemall.enums.DefaultAddressEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName DeliveryAddressRepository
 * @date 2019/4/20
 * @description
 */
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, String> {

    DeliveryAddress findByUserIdAndIsDefault (String userId,Integer code);

    DeliveryAddress findByUserIdAndAddressId (String userId,String addressId);

    List<DeliveryAddress> findByUserIdOrderByAddressIdDesc (String userId);

}
