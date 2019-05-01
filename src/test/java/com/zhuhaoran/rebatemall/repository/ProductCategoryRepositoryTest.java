package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ZhuHaoran
 * @className ProductCategoryRepositoryTest
 * @date 2019/4/6
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = repository.findById(1).orElse(null);
        productCategory.setCategoryName("百货");
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }
}