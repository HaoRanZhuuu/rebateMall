package com.zhuhaoran.rebatemall.service.impl;

import static org.junit.Assert.*;

import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author ZhuHaoran
 * @className UserInfoServiceImplTest
 * @date 2019/4/12
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Test
    public void save() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(IdKeyGenerate.getIdKey());
        userInfo.setUserName("zhr961120");
        userInfo.setPassword("961120");
        UserInfo result = userInfoService.save(userInfo);
        return;
    }

    @Test
    public void findByUserName() {
        UserInfo userInfo = userInfoService.findByUserName("zhr96112");
        return;
    }

    @Test
    public void matchingPasswordByUserName() {
        boolean b = userInfoService.matchingPasswordByUserName("zhr961120","961120");
        return;
    }
}