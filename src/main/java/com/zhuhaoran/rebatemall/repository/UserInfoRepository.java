package com.zhuhaoran.rebatemall.repository;

import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhuHaoran
 * @interfaceName UserInfoRepository
 * @date 2019/4/12
 * @description
 */
public interface UserInfoRepository extends JpaRepository<UserInfo , String> {


    UserInfo findByUserName (String userName);

    UserInfo findByUserNameAndPassword(String userName, String password);

}
