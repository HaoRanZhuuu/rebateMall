package com.zhuhaoran.rebatemall.service.impl;

import static org.junit.Assert.*;

import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductInfoServiceImplTest
 * @date 2019/4/8
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    ProductInfoServiceImpl productInfoService;

    /*@Test
    public void findByLikeProductName() {
        List<ProductInfo> productInfoList = productInfoService.findByLikeProductName("");
        return;
    }*/

    @Test
    public void findAll() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByCategoryId() {
    }

    @Test
    public void findByCategoryIdInList() {
        List<ProductInfo> productInfoList = productInfoService.findByCategoryIdInList( Arrays.asList(1,2,3));
        return;
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoService.findByProductStatus(0);
        return;
    }

    @Test
    public void save() {
        productInfoService.save(new ProductInfo("126", "123", new BigDecimal(123), new BigDecimal(123), 123, "123", "123", 1, 2));
    }

    @Test
    public void deleteById() {
        productInfoService.deleteById("123");
    }

    /*@Test
    public void findUpByNum() {
        List<ProductInfo> productInfoList = productInfoService.findUpByNum(1,2);
        return;
    }*/
}