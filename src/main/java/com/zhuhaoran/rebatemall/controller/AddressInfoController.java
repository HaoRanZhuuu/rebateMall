package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.dataobject.DeliveryAddress;
import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import com.zhuhaoran.rebatemall.enums.DefaultAddressEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.form.AddressForm;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import com.zhuhaoran.rebatemall.service.impl.DeliveryAddressServiceImpl;
import com.zhuhaoran.rebatemall.service.impl.UserInfoServiceImpl;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import com.zhuhaoran.rebatemall.utils.ResultVoUtil;
import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhuHaoran
 * @className AddressInfoController
 * @date 2019/4/22
 * @description
 */

@RestController
@RequestMapping("/buyer/address")
public class AddressInfoController {

    @Autowired
    DeliveryAddressServiceImpl deliveryAddressService;

    @Autowired
    UserInfoServiceImpl userInfoService;

    /*获取收货地址表*/
    @CrossOrigin
    @GetMapping("/list")
    public ResultVo<List<DeliveryAddress>> list(String userId) {

        if (StringUtils.isEmpty(userId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        UserInfo userInfo = userInfoService.findById(userId);
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }

        List<DeliveryAddress> deliveryAddressList = deliveryAddressService.list(userId);
        if (CollectionUtils.isEmpty(deliveryAddressList)) {
            return ResultVoUtil.error(100,"收货地址为空");
        }
        return ResultVoUtil.success(deliveryAddressList);

    }

    /*获取默认收货地址*/
    @CrossOrigin
    @GetMapping("/getDefault")
    public ResultVo<DeliveryAddress> getDefault(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }

        if (CollectionUtils.isEmpty(deliveryAddressService.list(userId))) {
            return ResultVoUtil.error(100, "收货地址为空");
        }
        return ResultVoUtil.success(deliveryAddressService.findByIsDefault(userId, DefaultAddressEnum.IS_DEFAULT.getCode()));
    }

    /*新增收货地址*/
    @CrossOrigin
    @PostMapping("/add")
    public ResultVo<Map<String,String>> add(@Valid AddressForm addressForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }

        if (StringUtils.isEmpty(userInfoService.findById(addressForm.getUserId()))) {
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }
        DeliveryAddress address = new DeliveryAddress();
        address.setAddressId(IdKeyGenerate.getIdKey());
        address.setUserId(addressForm.getUserId());
        address.setDeliveryAddress(addressForm.getDeliveryAddress());
        address.setDeliveryName(addressForm.getDeliveryName());
        address.setDeliveryPhone(addressForm.getDeliveryPhone());
        if (StringUtils.isEmpty(deliveryAddressService.findByIsDefault(addressForm.getUserId(),DefaultAddressEnum.IS_DEFAULT.getCode()))) {
            address.setIsDefault(DefaultAddressEnum.IS_DEFAULT.getCode());
        } else {
            address.setIsDefault(DefaultAddressEnum.NOT_DEFAULT.getCode());
        }
        DeliveryAddress result = deliveryAddressService.save(address);
        if (StringUtils.isEmpty(result)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        Map<String, String> map = new HashMap<>();
        map.put("addressId", result.getAddressId());

        return ResultVoUtil.success(map);
    }

    /*删除收货地址*/
    @CrossOrigin
    @DeleteMapping("/delete")
    public ResultVo delete(String userId,String addressId) {

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(addressId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(deliveryAddressService.findById(userId, addressId))) {
            throw new MallException(ResultEnum.ADDRESS_NOT_EXIST);
        }
        if (deliveryAddressService.findById(userId, addressId).getIsDefault() == DefaultAddressEnum.IS_DEFAULT.getCode()) {
            return ResultVoUtil.error(100,"不能删除默认地址");
        }

        DeliveryAddress deliveryAddress = deliveryAddressService.findById(userId, addressId);
        deliveryAddressService.delete(deliveryAddress);
        return ResultVoUtil.success();
    }

    /*修改收货地址*/
    @CrossOrigin
    @PutMapping("/alter")
    public ResultVo alter(@Valid AddressForm addressForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(addressForm.getAddressId())) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(deliveryAddressService.findById(addressForm.getUserId(), addressForm.getAddressId()))) {
            throw new MallException(ResultEnum.ADDRESS_NOT_EXIST);
        }
        DeliveryAddress deliveryAddress = deliveryAddressService.findById(addressForm.getUserId(), addressForm.getAddressId());
        deliveryAddress.setDeliveryPhone(addressForm.getDeliveryPhone());
        deliveryAddress.setDeliveryName(addressForm.getDeliveryName());
        deliveryAddress.setDeliveryAddress(addressForm.getDeliveryAddress());
        DeliveryAddress result = deliveryAddressService.save(deliveryAddress);
        if (StringUtils.isEmpty(result)) {
            throw new MallException(ResultEnum.UNKNOWN_ERROR);
        }
        return ResultVoUtil.success();
    }

    /*设为默认地址*/
    @CrossOrigin
    @PutMapping("/setDefault")
    public ResultVo setDefault(String userId,String addressId) {

        if (StringUtils.isEmpty(userId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(addressId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }
        if (StringUtils.isEmpty(deliveryAddressService.findById(userId,addressId))) {
            throw new MallException(ResultEnum.ADDRESS_NOT_EXIST);
        }
        if (deliveryAddressService.findById(userId,addressId).getIsDefault() == DefaultAddressEnum.IS_DEFAULT.getCode()) {
            return ResultVoUtil.error(100, "已经为默认地址");
        }
        DeliveryAddress temp = deliveryAddressService.findByIsDefault(userId, DefaultAddressEnum.IS_DEFAULT.getCode());
        temp.setIsDefault(DefaultAddressEnum.NOT_DEFAULT.getCode());
        deliveryAddressService.save(temp);
        DeliveryAddress deliveryAddress = deliveryAddressService.findById(userId,addressId);
        deliveryAddress.setIsDefault(DefaultAddressEnum.IS_DEFAULT.getCode());
        deliveryAddressService.save(deliveryAddress);
        return ResultVoUtil.success();
    }

}
