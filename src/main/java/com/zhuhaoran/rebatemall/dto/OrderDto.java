package com.zhuhaoran.rebatemall.dto;

import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import com.zhuhaoran.rebatemall.enums.OrderStatusEnum;
import com.zhuhaoran.rebatemall.enums.PayStatusEnum;
import com.zhuhaoran.rebatemall.enums.ShippingStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className OrderDto
 * @date 2019/4/21
 * @description
 */

@Data
public class OrderDto {

    /**订单ID*/
    private String orderId;

    /**用户id*/
    private String userId;/**/

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**订单优惠金额*/
    private BigDecimal orderDiscount;

    /**应付金额*/
    private BigDecimal orderShouldpay;

    /**订单状态*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**快递状态*/
    private Integer shippingStatus = ShippingStatusEnum.WAIT.getCode();

    /**快递公司名称*/
    private String shippingCom;

    /**快递单号*/
    private String shippingSn;

    /**支付状态*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**收货地址id*/
    private String addressId;/**/

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
