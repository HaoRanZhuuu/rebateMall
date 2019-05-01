package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName ProductInfoRepository
 * @date 2019/4/7
 * @description
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    Page<ProductInfo> findAllByProductNameContaining (String name, Pageable pageable);

    Page<ProductInfo> findByProductStatus (Integer productStatus,Pageable pageable);

    List<ProductInfo> findByCategoryIdIn (List<Integer> categoryIdList);

    Page<ProductInfo> findByCategoryId (Integer categoryId,Pageable pageable);
}
