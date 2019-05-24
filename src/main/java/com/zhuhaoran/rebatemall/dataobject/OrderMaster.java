package com.zhuhaoran.rebatemall.dataobject;

import com.zhuhaoran.rebatemall.enums.OrderStatusEnum;
import com.zhuhaoran.rebatemall.enums.PayStatusEnum;
import com.zhuhaoran.rebatemall.enums.ShippingStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className OrderMaster
 * @date 2019/4/20
 * @description
 */

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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
    private Integer shippingStatus = ShippingStatusEnum.SHIPPING.getCode();

    /**快递公司名称*/
    private String shippingCom;

    /**快递单号*/
    private String shippingSn;

    /**支付状态*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**收货地址id*/
    private String addressId;/**/

    private String logId;

    private Date createTime;

    private Date updateTime;
}
