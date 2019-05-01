package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import com.zhuhaoran.rebatemall.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName ProductInfoService
 * @date 2019/4/7
 * @description
 */
public interface ProductInfoService {

    Page<ProductInfo> findAll(Pageable pageable);

    Page<ProductInfo> findUpAll(Pageable pageable);

    ProductInfo findById(String productId);

    Page<ProductInfo> findByLikeProductName(String name,Pageable pageable);

    Page<ProductInfo> findByCategoryId(Integer categoryId,Pageable pageable);

    List<ProductInfo> findByCategoryIdInList(List<Integer> categoryIdList);

    List<ProductInfo> findByProductStatus(Integer productStatus);

    ProductInfo save(ProductInfo productInfo);

    public void increaseStock(List<CartDto> cartDtoList);

    public void decreaseStock(List<CartDto> cartDtoList);

    public void deleteById(String productId);


}
