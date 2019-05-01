package com.zhuhaoran.rebatemall.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className ProductListVo
 * @date 2019/4/10
 * @description
 */
@Data
public class ProductVo {


    private Integer page;

    private Integer size;

    private String userId;

    @JsonProperty("product")
    private List<ProductInfoVo> productInfoVoList;
}
