package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className ProductCategory
 * @date 2019/4/6
 * @description 商品类目表
 */

@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 类目id. */
    private Integer categoryId;

    /* 类目名称. */
    private String categoryName;

    /* 上一类id. */
    private Integer parentId;

    /* 类目所在级别. */
    private Integer categoryGrade;

    /* 创建时间. */
    private Date createTime;

    /* 修改时间. */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer parentId, Integer categoryGrade) {
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.categoryGrade = categoryGrade;
    }
}
