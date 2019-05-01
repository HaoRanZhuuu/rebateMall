package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className Cate1stVo
 * @date 2019/4/30
 * @description
 */

@Data
public class Cate1stVo {

    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private Integer categoryGrade;

    private List<Cate2edVo> cate2edVoList;
}
