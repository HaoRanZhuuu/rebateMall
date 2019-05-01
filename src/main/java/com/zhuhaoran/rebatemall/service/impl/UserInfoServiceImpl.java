package com.zhuhaoran.rebatemall.service.impl;

import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import com.zhuhaoran.rebatemall.repository.UserInfoRepository;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ZhuHaoran
 * @className UserInfoServiceImpl
 * @date 2019/4/12
 * @description
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    @Override
    public UserInfo findById(String userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public UserInfo findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public boolean matchingPasswordByUserName(String userName, String password) {

        return !StringUtils.isEmpty(repository.findByUserNameAndPassword(userName, password));
    }
}
