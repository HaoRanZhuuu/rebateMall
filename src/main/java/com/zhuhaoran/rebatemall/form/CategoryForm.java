package com.zhuhaoran.rebatemall.form;

import lombok.Data;

/**
 * @author ZhuHaoran
 * @className CategoryForm
 * @date 2019/4/30
 * @description
 */

@Data
public class CategoryForm {

    private String categoryName;

    private Integer parentId;

    private Integer categoryGrade;

}
