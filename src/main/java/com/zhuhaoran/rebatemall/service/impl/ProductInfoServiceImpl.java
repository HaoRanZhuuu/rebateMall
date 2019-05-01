package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import com.zhuhaoran.rebatemall.dto.CartDto;
import com.zhuhaoran.rebatemall.enums.ProductStatusEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.repository.ProductInfoRepository;
import com.zhuhaoran.rebatemall.service.ProductInfoService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductInfoServiceImpl
 * @date 2019/4/7
 * @description
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {


    @Autowired
    private ProductInfoRepository repository;

    @Override
    public Page<ProductInfo> findByLikeProductName(String name,Pageable pageable) {

        return repository.findAllByProductNameContaining(name,pageable);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<ProductInfo> findUpAll(Pageable pageable) {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode(),pageable);
    }

    @Override
    public ProductInfo findById(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public Page<ProductInfo> findByCategoryId(Integer categoryId,Pageable pageable) {
        return repository.findByCategoryId(categoryId,pageable);
    }

    @Override
    public List<ProductInfo> findByCategoryIdInList(List<Integer> categoryIdList) {
        return repository.findByCategoryIdIn(categoryIdList);
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        PageRequest pageRequest = PageRequest.of(0,2147483647);
        return repository.findByProductStatus(productStatus,pageRequest).getContent();

    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void deleteById(String productId) {
            repository.deleteById(productId);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findById(cartDto.getProductId()).orElse(null);
            if (StringUtils.isEmpty(productInfo)) {
                throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findById(cartDto.getProductId()).orElse(null);
            if (StringUtils.isEmpty(productInfo)) {
                throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result < 0) {
                throw new MallException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
