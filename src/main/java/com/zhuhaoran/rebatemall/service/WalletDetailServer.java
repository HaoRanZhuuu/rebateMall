package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.WalletDetail;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName WalletDetailServer
 * @date 2019/4/27
 * @description
 */
public interface WalletDetailServer {

    public WalletDetail save (WalletDetail walletDetail);

    public List<WalletDetail> getListByWalletId (String walletId);

    public WalletDetail getById(String logId);
}
