package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName ProductCategoryService
 * @date 2019/4/7
 * @description 类目service接口
 */
public interface ProductCategoryService {

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryGrade(Integer categoryGrade);

    List<ProductCategory> findByCategoryId(List<Integer> categoryIdList);

    ProductCategory save(ProductCategory productCategory);

    void deleteById(Integer categoryId);



}
