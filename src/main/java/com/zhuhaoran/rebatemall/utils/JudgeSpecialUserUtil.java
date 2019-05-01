package com.zhuhaoran.rebatemall.utils;

import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import com.zhuhaoran.rebatemall.enums.IdentificationStatusEnum;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import com.zhuhaoran.rebatemall.viewObject.ProductInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className JudgeSpecialUserUtil
 * @date 2019/4/15
 * @description
 */
@Component
public class JudgeSpecialUserUtil {
    @Autowired
    private UserInfoService userInfoServicetemp;
    @Autowired
    private static UserInfoService userInfoService;
    @PostConstruct
    public void init(){
        userInfoService = userInfoServicetemp;
    }

    public static boolean judgeSpecialUser(String userId){

        UserInfo userInfo = userInfoService.findById(userId);

        if (userInfo.getIsSpecial() == IdentificationStatusEnum.IS_IDENTIFICATION.getCode()){
            return true;
        }
        else
            return false;
    }

    public static List<ProductInfoVo> ProductInfoList2ProductInfoVoList(List<ProductInfo> productInfoList,String userId){
        List<ProductInfoVo> productInfoVoList = new ArrayList<>();
        if (judgeSpecialUser(userId)) {
            for (ProductInfo productinfo:productInfoList) {
                ProductInfoVo productInfoVo = new ProductInfoVo();
                BeanUtils.copyProperties(productinfo, productInfoVo);
                productInfoVoList.add(productInfoVo);
            }
            return productInfoVoList;
        } else {
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVo productInfoVo = new ProductInfoVo();
                productInfoVo.setProductId(productInfo.getProductId());
                productInfoVo.setProductName(productInfo.getProductName());
                productInfoVo.setProductPrice(productInfo.getProductPrice());
                productInfoVo.setProductIcon(productInfo.getProductIcon());
                productInfoVoList.add(productInfoVo);
            }
            return productInfoVoList;
        }

    }
}
