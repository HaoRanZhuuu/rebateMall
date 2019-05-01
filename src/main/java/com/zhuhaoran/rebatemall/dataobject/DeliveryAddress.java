package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ZhuHaoran
 * @className DeliveryAddress
 * @date 2019/4/20
 * @description
 */

@Entity
@Data
public class DeliveryAddress {

    @Id
    /**收货地址id*/
    private String addressId;

    /**用户id*/
    private String userId;

    /**收货人名称*/
    private String deliveryName;

    /**收货人手机*/
    private String deliveryPhone;

    /**收货人地址*/
    private String deliveryAddress;

    /**是否默认地址*/
    private Integer isDefault;
}
