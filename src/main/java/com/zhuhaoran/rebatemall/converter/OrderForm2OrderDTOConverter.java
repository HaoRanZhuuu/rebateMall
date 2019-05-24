package com.zhuhaoran.rebatemall.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import com.zhuhaoran.rebatemall.dto.OrderDto;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.form.OrderForm;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className OrderForm2OrderDTOConverter
 * @date 2019/4/21
 * @description
 */
public class OrderForm2OrderDTOConverter {

    public static OrderDto converter(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(orderForm.getUserId());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }



}
