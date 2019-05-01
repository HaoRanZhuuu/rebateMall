package com.zhuhaoran.rebatemall.service;

import com.zhuhaoran.rebatemall.dataobject.UserInfo;

/**
 * @author ZhuHaoran
 * @interfaceName UserInfoService
 * @date 2019/4/12
 * @description
 */
public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    UserInfo findByUserName(String userName);

    UserInfo findById(String userId);

    boolean matchingPasswordByUserName(String userName , String password);
}
