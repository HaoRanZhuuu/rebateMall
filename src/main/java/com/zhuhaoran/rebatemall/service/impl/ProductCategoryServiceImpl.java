package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;
import com.zhuhaoran.rebatemall.repository.ProductCategoryRepository;
import com.zhuhaoran.rebatemall.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductCategoryServiceImpl
 * @date 2019/4/7
 * @description
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findById(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryGrade(Integer categoryGrade) {
        return repository.findByCategoryGrade(categoryGrade);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public void deleteById(Integer categoryId) {
            repository.deleteById(categoryId);
    }

    @Override
    public List<ProductCategory> findByCategoryId(List<Integer> categoryIdList) {
        return repository.findByCategoryIdIn(categoryIdList);
    }
}
