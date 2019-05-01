package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className Cate2edVo
 * @date 2019/4/30
 * @description
 */

@Data
public class Cate2edVo {
    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private Integer categoryGrade;

    private List<Cate3thVo> cate3thVoList;
}
