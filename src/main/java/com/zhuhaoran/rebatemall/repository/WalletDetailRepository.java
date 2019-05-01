package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.WalletDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ZhuHaoran
 * @interfaceName WalletDetailRepository
 * @date 2019/4/20
 * @description
 */
public interface WalletDetailRepository extends JpaRepository<WalletDetail, String> {

    List<WalletDetail> findByWalletId(String walletId);
}
