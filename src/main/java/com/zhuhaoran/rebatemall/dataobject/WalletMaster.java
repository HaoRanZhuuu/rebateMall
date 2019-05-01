package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className WalletMaster
 * @date 2019/4/20
 * @description
 */
@Data
@Entity
public class WalletMaster {

    @Id
    /**钱包id*/
    private String walletId;

    /**用户id*/
    private String userId;

    /**支付密码*/
    private String password;

    /*钱包余额*/
    private BigDecimal walletBalance;

}
