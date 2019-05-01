package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.WalletMaster;

/**
 * @author ZhuHaoran
 * @interfaceName WalletMasterService
 * @date 2019/4/20
 * @description
 */
public interface WalletMasterService {

    public WalletMaster save(WalletMaster walletMaster);

    public WalletMaster findByUserId(String userId);
}
