package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className OrderDetail
 * @date 2019/4/20
 * @description
 */

@DynamicUpdate
@Entity
@Data
public class OrderDetail {

    @Id
    /*订单详情id*/
    private String detailId;

    /**订单id*/
    private String orderId;

    /**商品id*/
    private String productId;/**/

    /**推荐者用户id*/
    private String sellerId;/**/

    /**商品名*/
    private String productName;

    /**商品售价*/
    private BigDecimal productPrice;

    /**商品优惠价格*/
    private BigDecimal productDiscount;

    /**商品数量*/
    private Integer productQuantity;/**/

    /**商品缩略图*/
    private String productIcon;

    private Date createTime;

    private Date updateTime;
}
