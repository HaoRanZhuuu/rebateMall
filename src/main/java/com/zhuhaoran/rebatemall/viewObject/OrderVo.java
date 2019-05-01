package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className OrderVo
 * @date 2019/4/27
 * @description
 */

@Data
public class OrderVo {

    private String orderId;//

    private BigDecimal orderAmount;//

    private BigDecimal orderDiscount;//

    private BigDecimal orderShouldpay;//

    private Integer quantity;

    private Integer orderStatus;//

    private Integer shippingStatus;//

    private String shippingCom;//

    private String shippingSn;//

    private Integer payStatus;//

    private String deliveryName;

    private String deliveryPhone;

    private String deliveryAddress;

    private String logId;

    private Date createTime;//

    private Date updateTime;//

    private List<OrderDetailVo> orderDetailVoList;

}
