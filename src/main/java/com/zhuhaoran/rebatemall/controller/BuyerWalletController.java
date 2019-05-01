package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.dataobject.*;
import com.zhuhaoran.rebatemall.enums.OrderStatusEnum;
import com.zhuhaoran.rebatemall.enums.PayStatusEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.enums.WalletLogTypeEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.service.OrderService;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import com.zhuhaoran.rebatemall.service.WalletDetailServer;
import com.zhuhaoran.rebatemall.service.WalletMasterService;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import com.zhuhaoran.rebatemall.utils.ResultVoUtil;
import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import com.zhuhaoran.rebatemall.viewObject.WalletMasterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhuHaoran
 * @className BuyerWalletController
 * @date 2019/4/26
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
public class BuyerWalletController {

    @Autowired
    WalletMasterService walletMasterService;

    @Autowired
    WalletDetailServer walletDetailServer;

    @Autowired
    OrderService orderService;

    @Autowired
    UserInfoService userInfoService;

    /**获取当前余额信息*/
    @GetMapping("/get")
    public ResultVo get(String userId) {
        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        WalletMaster walletMaster = walletMasterService.findByUserId(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("walletId", walletMaster.getWalletId());
        map.put("walletBalance", walletMaster.getWalletBalance());
        return ResultVoUtil.success(map);
    }

    /**创建钱包账户*/
    @PostMapping("/create")
    public ResultVo create(String userId, String password) {
        if (userId.isEmpty() || password.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        WalletMaster walletMaster = new WalletMaster();
        walletMaster.setWalletId(IdKeyGenerate.getIdKey());
        walletMaster.setUserId(userId);
        walletMaster.setPassword(password);
        walletMaster.setWalletBalance(new BigDecimal(BigInteger.ZERO));
        walletMasterService.save(walletMaster);
        UserInfo userInfo = userInfoService.findById(userId);
        userInfo.setWalletId(walletMaster.getWalletId());
        userInfoService.save(userInfo);
        Map<String, String> map = new HashMap<String, String>();
        map.put("walletId", walletMaster.getWalletId());
        return ResultVoUtil.success(map);
    }

    /**支付订单*/
    @PutMapping("/pay")
    public ResultVo pay(String userId, String password, String orderId) {
        WalletMaster walletMaster = walletMasterService.findByUserId(userId);
        OrderMaster orderMaster = orderService.findByOrderId(orderId);
        if (userId.isEmpty() || password.isEmpty() || orderId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(walletMaster)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (!walletMaster.getPassword().equals(password)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(orderMaster)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (orderMaster.getPayStatus() == PayStatusEnum.SUCCESS.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (orderMaster.getOrderStatus() == OrderStatusEnum.CANCEL.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (walletMaster.getWalletBalance().compareTo(orderMaster.getOrderShouldpay()) == -1) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        walletMaster.setWalletBalance(walletMaster.getWalletBalance().subtract(orderMaster.getOrderShouldpay()));
        walletMasterService.save(walletMaster);
        String logId = IdKeyGenerate.getIdKey();
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMaster.setLogId(logId);
        orderService.save(orderMaster);
        WalletDetail walletDetail = new WalletDetail();
        walletDetail.setLogBalance(walletMaster.getWalletBalance());
        walletDetail.setLogId(logId);
        List<OrderDetail> orderDetails = orderService.findListByOrderId(orderId);
        if (orderDetails.size() == 1) {
            walletDetail.setLogInfo(orderDetails.get(0).getProductName());
        } else {
            walletDetail.setLogInfo(orderDetails.get(0).getProductName() + "等商品");
        }
        walletDetail.setWalletId(walletMaster.getWalletId());
        walletDetail.setLogMoney(orderMaster.getOrderShouldpay());
        walletDetail.setLogType(WalletLogTypeEnum.EXPEND.getCode());
        walletDetailServer.save(walletDetail);

        return ResultVoUtil.success();
    }

    /**金额充值*/
    @PostMapping("/add")
    public ResultVo add(String userId, String password, BigDecimal money) {
        if (userId.isEmpty() || password.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        WalletMaster walletMaster = walletMasterService.findByUserId(userId);
        if (StringUtils.isEmpty(walletMaster)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (!walletMaster.getPassword().equals(password)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        walletMaster.setWalletBalance(walletMaster.getWalletBalance().add(money));
        walletMasterService.save(walletMaster);
        return ResultVoUtil.success();
    }

    /**获取钱包明细信息*/
    @GetMapping("/list")
    public ResultVo list(String userId) {
        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        WalletMaster walletMaster = walletMasterService.findByUserId(userId);
        List<WalletDetail> walletDetailList = walletDetailServer.getListByWalletId(walletMaster.getWalletId());
        List<WalletMasterVo> walletMasterVoList = new ArrayList<>();
        for (WalletDetail walletDetail : walletDetailList) {
            WalletMasterVo walletMasterVo = new WalletMasterVo();
            walletMasterVo.setCreateTime(walletDetail.getCreateTime());
            walletMasterVo.setLogInfo(walletDetail.getLogInfo());
            String money;
            if (walletDetail.getLogType() == WalletLogTypeEnum.INCOME.getCode()) {
                money = "+" + walletDetail.getLogMoney();
            } else {
                money = "-" + walletDetail.getLogMoney();
            }
            walletMasterVo.setLogId(walletDetail.getLogId());
            walletMasterVo.setMoney(money);
            walletMasterVoList.add(walletMasterVo);
        }
        return ResultVoUtil.success(walletMasterVoList);
    }

    /**获取钱包明细详情*/
    @GetMapping("/detail")
    public ResultVo detail(String logId) {
        return ResultVoUtil.success(walletDetailServer.getById(logId));

    }
}
