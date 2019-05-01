package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductCategoryRepository
 * @date 2019/4/6
 * @description 商品类目的dao接口
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryIdIn(List<Integer> categoryIdList);

    List<ProductCategory> findByCategoryGrade(Integer categoryGrade);
}
