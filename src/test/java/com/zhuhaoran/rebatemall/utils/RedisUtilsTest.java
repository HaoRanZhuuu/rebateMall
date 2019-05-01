package com.zhuhaoran.rebatemall.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhuHaoran
 * @className RedisUtilsTest
 * @date 2019/4/25
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilsTest {

    @Autowired RedisUtils redisUtils;
    @Test
    public void init() {
    }

    /*@Test
    public void setRedisByHashMap() {
        redisUtils.setRedisByHashMap();
    }*/
}