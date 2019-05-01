package com.zhuhaoran.rebatemall.dataobject;

import com.zhuhaoran.rebatemall.enums.RebateStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className RebateDetail
 * @date 2019/4/20
 * @description
 */

@Data
@Entity
@DynamicUpdate
public class RebateDetail {

    @Id
    /**返利明细id*/
    private String rebateId;

    /**订单id*/
    private String detailId;

    /**用户id*/
    private String userId;

    /**分销类型*/
    private Integer rebateType;

    /**分销状态*/
    private Integer rebateStatus = RebateStatusEnum.WAIT.getCode();

    /**返利金额*/
    private BigDecimal rebateMoney;

    /**分销备注*/
    private String rebateInfo;

    private Date createTime;

    private Date updateTime;
}
