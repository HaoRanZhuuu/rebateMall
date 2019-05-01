package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.util.Date;

/**
 * @author ZhuHaoran
 * @className WalletMasterVo
 * @date 2019/4/28
 * @description
 */
@Data
public class WalletMasterVo {

    private String logId;

    private String logInfo;

    private Date createTime;

    private String money;
}
