package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.converter.OrderForm2OrderDTOConverter;
import com.zhuhaoran.rebatemall.dataobject.*;
import com.zhuhaoran.rebatemall.dto.OrderDto;
import com.zhuhaoran.rebatemall.enums.*;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.form.OrderForm;
import com.zhuhaoran.rebatemall.service.*;
import com.zhuhaoran.rebatemall.service.impl.OrderServiceImpl;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import com.zhuhaoran.rebatemall.utils.ResultVoUtil;
import com.zhuhaoran.rebatemall.viewObject.OrderDetailVo;
import com.zhuhaoran.rebatemall.viewObject.OrderVo;
import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.ext.MacArabic;

import javax.validation.Valid;
import java.util.*;

/**
 * @author ZhuHaoran
 * @className OrderController
 * @date 2019/4/21
 * @description
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    DeliveryAddressService addressService;

    @Autowired
    RebateDetailService rebateDetailService;

    @Autowired
    WalletMasterService walletMasterService;

    @Autowired
    WalletDetailServer walletDetailServer;

    @Autowired
    UserInfoService userInfoService;

    /**创建订单*/
    @CrossOrigin
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }

        OrderDto orderDto = OrderForm2OrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            throw new MallException(ResultEnum.CART_EMPTY);
        }

        String result = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("OrderId", result);
        return ResultVoUtil.success(map);
    }

    /**完成订单*/
    @CrossOrigin
    @PutMapping("/finish")
    public ResultVo finish(String userId, String orderId) {
        OrderMaster orderMaster = orderService.findByOrderId(orderId);
        if (userId.isEmpty() || orderId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(orderMaster)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (!orderMaster.getUserId().equals(userId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (orderMaster.getOrderStatus().equals(OrderStatusEnum.FINISH.getCode()) ||
                orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode()) ||
                orderMaster.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode()) ||
                orderMaster.getShippingStatus().equals(ShippingStatusEnum.WAIT.getCode())) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderService.save(orderMaster);

        List<OrderDetail> orderDetailList = orderService.findListByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getSellerId()!=null) {
                RebateDetail rebateDetail = rebateDetailService.findByUserIdAndDetailId(orderDetail.getSellerId(), orderDetail.getDetailId());
                rebateDetail.setRebateStatus(RebateStatusEnum.FINISH.getCode());
                rebateDetailService.save(rebateDetail);

                WalletMaster walletMaster = walletMasterService.findByUserId(orderDetail.getSellerId());
                walletMaster.setWalletBalance(walletMaster.getWalletBalance().add(rebateDetail.getRebateMoney()));
                walletMasterService.save(walletMaster);
                WalletDetail walletDetail = new WalletDetail();
                walletDetail.setLogId(IdKeyGenerate.getIdKey());
                walletDetail.setLogType(WalletLogTypeEnum.INCOME.getCode());
                walletDetail.setLogMoney(rebateDetail.getRebateMoney());
                walletDetail.setWalletId(walletMaster.getWalletId());
                walletDetail.setLogInfo("分销分红");
                walletDetail.setLogBalance(walletMaster.getWalletBalance());
                walletDetailServer.save(walletDetail);

                String parentId = userInfoService.findById(orderDetail.getSellerId()).getParentId();
                if (parentId != null) {
                    WalletMaster walletMasterParent = walletMasterService.findByUserId(userInfoService.findById(orderDetail.getSellerId()).getParentId());
                    RebateDetail rebateDetailParent = rebateDetailService.findByUserIdAndDetailId(parentId, orderDetail.getDetailId());
                    rebateDetailParent.setRebateStatus(RebateStatusEnum.FINISH.getCode());
                    walletMasterParent.setWalletBalance(walletMasterParent.getWalletBalance().add(rebateDetailParent.getRebateMoney()));
                    walletMasterService.save(walletMaster);
                    WalletDetail walletDetailParent = new WalletDetail();
                    walletDetailParent.setLogId(IdKeyGenerate.getIdKey());
                    walletDetailParent.setLogType(WalletLogTypeEnum.INCOME.getCode());
                    walletDetailParent.setLogBalance(walletMasterParent.getWalletBalance());
                    walletDetailParent.setLogInfo("分销分红");
                    walletDetailParent.setWalletId(walletMasterParent.getWalletId());
                    walletDetailParent.setLogMoney(rebateDetailParent.getRebateMoney());
                    walletDetailServer.save(walletDetailParent);
                }
            }
        }
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @GetMapping("/list")
    public ResultVo list(String userId) {

        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(orderService.list(userId))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        List<OrderVo> orderVoList = new ArrayList<>();
        for (OrderMaster orderMaster : orderService.list(userId)) {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(orderMaster, orderVo);
            DeliveryAddress address = addressService.findById(userId, orderMaster.getAddressId());
            orderVo.setDeliveryAddress(address.getDeliveryAddress());
            orderVo.setDeliveryName(address.getDeliveryName());
            orderVo.setDeliveryPhone(address.getDeliveryPhone());
            List<OrderDetailVo> orderDetailVoList = new ArrayList<>();
            Integer num = 0;
            for (OrderDetail orderDetail : orderService.findListByOrderId(orderMaster.getOrderId())) {
                OrderDetailVo orderDetailVo = new OrderDetailVo();
                orderDetailVo.setProductDiscount(orderDetail.getProductDiscount());
                orderDetailVo.setProductIcon(orderDetail.getProductIcon());
                orderDetailVo.setProductId(orderDetail.getProductId());
                orderDetailVo.setProductName(orderDetail.getProductName());
                orderDetailVo.setProductPrice(orderDetail.getProductPrice());
                orderDetailVo.setProductQuantity(orderDetail.getProductQuantity());
                orderDetailVoList.add(orderDetailVo);
                num += orderDetail.getProductQuantity();
            }
            orderVo.setQuantity(num);
            orderVo.setOrderDetailVoList(orderDetailVoList);
            orderVoList.add(orderVo);
        }
        Collections.reverse(orderVoList);
        return ResultVoUtil.success(orderVoList);

    }

    @CrossOrigin
    @PostMapping("/cancel")
    public ResultVo cancel(String userId, String orderId) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(orderId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId)) || StringUtils.isEmpty(orderService.findByOrderId(orderId))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        OrderMaster orderMaster = orderService.findByOrderId(orderId);
        if (orderMaster.getOrderStatus() == OrderStatusEnum.NEW.getCode() && orderMaster.getPayStatus() == PayStatusEnum.WAIT.getCode()) {
            orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
            orderService.save(orderMaster);
        } else if (orderMaster.getOrderStatus() == OrderStatusEnum.NEW.getCode() && orderMaster.getPayStatus() == PayStatusEnum.WAIT.getCode() && orderMaster.getShippingStatus() == ShippingStatusEnum.WAIT.getCode()) {
            orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
            orderService.save(orderMaster);
            WalletMaster walletMaster = walletMasterService.findByUserId(userId);
            WalletDetail walletDetail = new WalletDetail();
            walletMaster.setWalletBalance(walletMaster.getWalletBalance().add(orderMaster.getOrderShouldpay()));
            walletMasterService.save(walletMaster);
            walletDetail.setWalletId(walletMaster.getWalletId());
            walletDetail.setLogMoney(orderMaster.getOrderShouldpay());
            walletDetail.setLogInfo("退款");
            walletDetail.setLogBalance(walletMaster.getWalletBalance());
            walletDetail.setLogType(WalletLogTypeEnum.INCOME.getCode());
            walletDetail.setLogId(IdKeyGenerate.getIdKey());
            walletDetailServer.save(walletDetail);
        }
        return ResultVoUtil.success();
    }

    // TODO: 2019/4/29 订单详情

    @CrossOrigin
    @PutMapping("/delivery")
    public ResultVo delivery (String shippingCom,String shippingSn,String orderId) {
        OrderMaster orderMaster = orderService.findByOrderId(orderId);
        if (orderMaster.getShippingStatus() == ShippingStatusEnum.SHIPPING.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (orderMaster.getPayStatus() == PayStatusEnum.WAIT.getCode() || orderMaster.getOrderStatus() == OrderStatusEnum.CANCEL.getCode() || orderMaster.getOrderStatus() == OrderStatusEnum.FINISH.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        orderMaster.setShippingStatus(ShippingStatusEnum.SHIPPING.getCode());
        orderMaster.setShippingCom(shippingCom);
        orderMaster.setShippingSn(shippingSn);
        orderService.save(orderMaster);
        return ResultVoUtil.success();
    }


}
