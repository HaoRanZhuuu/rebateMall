package com.zhuhaoran.rebatemall.form;

import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className OrderForm
 * @date 2019/4/21
 * @description
 */

@Data
public class OrderForm {
    @NotEmpty(message = "用户id不能为空")
    /**用户id*/
    private String userId;/**/

    @NotEmpty(message = "购物车不能为空")
    /*商品id和数量*/
    private String  items;
}
