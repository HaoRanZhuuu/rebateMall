package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhuHaoran
 * @className WalletDetail
 * @date 2019/4/20
 * @description
 */
@Data
@Entity
@DynamicUpdate
public class WalletDetail {

    @Id
    /**钱包明细id*/
    private String logId;

    /**钱包id*/
    private String walletId;

    /**明细类型*/
    private Integer logType;

    /**明细金额*/
    private BigDecimal logMoney;

    /**当前余额*/
    private BigDecimal logBalance;

    /**明细备注*/
    private String logInfo;

    private Date createTime;

    private Date updateTime;
}
