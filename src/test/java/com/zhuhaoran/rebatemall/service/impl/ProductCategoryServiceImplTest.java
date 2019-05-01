package com.zhuhaoran.rebatemall.service.impl;

import static org.junit.Assert.*;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductCategoryServiceImplTest
 * @date 2019/4/7
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findById() {
        ProductCategory productCategory = productCategoryService.findById(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryGrade() {
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryGrade(1);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test

    public void save() {
        ProductCategory productCategory = productCategoryService.save(new ProductCategory("男装", 0, 1));
        Assert.assertEquals(new Integer(5),productCategory.getCategoryId());
    }

    @Test
    public void deleteById() {
        productCategoryService.deleteById(5);
    }

    @Test
    public void findByCategoryId() {
       List<ProductCategory> productCategoryList = productCategoryService.findByCategoryId(Arrays.asList(1,2,3,4));
       Assert.assertEquals(new Integer(2),new Integer(productCategoryList.size()));
    }
}