package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.WalletMaster;
import com.zhuhaoran.rebatemall.repository.WalletMasterRepository;
import com.zhuhaoran.rebatemall.service.WalletMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhuHaoran
 * @className WalletMasterServiceImpl
 * @date 2019/4/20
 * @description
 */
@Service
public class WalletMasterServiceImpl implements WalletMasterService {

    @Autowired
    WalletMasterRepository repository;
    @Override
    public WalletMaster save(WalletMaster walletMaster) {
        return repository.save(walletMaster);
    }

    @Override
    public WalletMaster findByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
