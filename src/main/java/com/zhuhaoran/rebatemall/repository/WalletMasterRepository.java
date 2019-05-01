package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhuHaoran
 * @interfaceName WalletMasterRepository
 * @date 2019/4/20
 * @description
 */
public interface WalletMasterRepository extends JpaRepository<WalletMaster, String> {

    WalletMaster findByUserId (String userId);

}
