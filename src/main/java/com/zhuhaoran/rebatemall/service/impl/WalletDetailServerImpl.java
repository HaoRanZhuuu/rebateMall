package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.WalletDetail;
import com.zhuhaoran.rebatemall.repository.WalletDetailRepository;
import com.zhuhaoran.rebatemall.service.WalletDetailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhuHaoran
 * @className WalletDetailServerImpl
 * @date 2019/4/27
 * @description
 */
@Service
public class WalletDetailServerImpl implements WalletDetailServer {

    @Autowired
    WalletDetailRepository repository;

    @Override
    public WalletDetail save(WalletDetail walletDetail) {
        return repository.save(walletDetail);
    }

    @Override
    public List<WalletDetail> getListByWalletId(String walletId) {
        return repository.findByWalletId(walletId);
    }

    @Override
    public WalletDetail getById(String logId) {
        return repository.findById(logId).orElse(null);
    }
}
