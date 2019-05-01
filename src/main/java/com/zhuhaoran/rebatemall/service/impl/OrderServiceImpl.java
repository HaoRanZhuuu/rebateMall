package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.OrderDetail;
import com.zhuhaoran.rebatemall.dataobject.OrderMaster;
import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import com.zhuhaoran.rebatemall.dto.CartDto;
import com.zhuhaoran.rebatemall.dto.OrderDto;
import com.zhuhaoran.rebatemall.enums.DefaultAddressEnum;
import com.zhuhaoran.rebatemall.enums.RebateTypeEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.repository.OrderDetailRepository;
import com.zhuhaoran.rebatemall.repository.OrderMasterRepository;
import com.zhuhaoran.rebatemall.repository.ProductInfoRepository;
import com.zhuhaoran.rebatemall.service.*;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className OrderServiceImpl
 * @date 2019/4/21
 * @description
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    DeliveryAddressService deliveryAddressService;

    @Autowired
    OrderDetailRepository detailRepository;

    @Autowired
    OrderMasterRepository masterRepository;

    @Autowired
    RebateDetailService rebateDetailService;

    @Autowired
    UserInfoService userInfoService;

    @Override
    @Transactional
    public String create(OrderDto orderDto) {

        /**验证商品存在*/
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        BigDecimal orderDiscount = new BigDecimal(BigInteger.ZERO);
        BigDecimal orderShoulepay = new BigDecimal(BigInteger.ZERO);
        String orderId = IdKeyGenerate.getIdKey();

        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if (StringUtils.isEmpty(productInfo)) {
                throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /**计算总价*/
            orderShoulepay = (productInfo.getProductPrice()
                    .subtract(productInfo.getProductDiscount()))
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            orderDiscount = (productInfo.getProductDiscount()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())))
                    .add(orderDiscount);
            /*生成orderDetail*/
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductDiscount(productInfo.getProductDiscount());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(IdKeyGenerate.getIdKey());
            detailRepository.save(orderDetail);
            if (orderDetail.getSellerId() != null) {
                rebateDetailService.create(orderDetail.getSellerId(),
                        orderDetail.getDetailId(),
                        RebateTypeEnum.SELL_PRODUCT.getCode(),
                        productInfo.getProductDiscount().multiply(new BigDecimal(orderDetail.getProductQuantity())),
                        "商品："+productInfo.getProductName()+"返利");
                if (userInfoService.findById(orderDetail.getSellerId()).getParentId() != null) {
                    rebateDetailService.create(userInfoService.findById(orderDetail.getSellerId()).getParentId(),
                            orderDetail.getDetailId(),
                            RebateTypeEnum.SELL_PRODUCT.getCode(),
                            productInfo.getProductDiscount().multiply(new BigDecimal(orderDetail.getProductQuantity()).multiply(new BigDecimal(0.5))),
                            "商品："+productInfo.getProductName()+"返利");
                }
            }


        }

        orderAmount = orderShoulepay.add(orderDiscount);
        /*生成orderMaster*/
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setUserId(orderDto.getUserId());
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderDiscount(orderDiscount);
        orderMaster.setOrderShouldpay(orderShoulepay);
        if (CollectionUtils.isEmpty(deliveryAddressService.list(orderDto.getUserId()))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        orderMaster.setAddressId(deliveryAddressService.findByIsDefault(orderDto.getUserId(), DefaultAddressEnum.IS_DEFAULT.getCode()).getAddressId());

        masterRepository.save(orderMaster);

        /*扣库存*/

        List<CartDto> cartDtoList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            cartDtoList.add(new CartDto(orderDetail.getProductId(),orderDetail.getProductQuantity()));
        }
        productInfoService.decreaseStock(cartDtoList);
        return orderMaster.getOrderId();
        /*

        //扣除库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;*/
    }

    @Override
    public OrderMaster findByOrderId(String orderId) {
        return masterRepository.findByOrderId(orderId);
    }

    @Override
    public OrderMaster save(OrderMaster orderMaster) {
        return masterRepository.save(orderMaster);
    }

    @Override
    public List<OrderDetail> findListByOrderId(String orderId) {
        return detailRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderMaster> list(String userId) {
        return masterRepository.findByUserId(userId);
    }
}
