package com.zhuhaoran.rebatemall.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhuHaoran
 * @className IdKeyGenerateTest
 * @date 2019/4/12
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdKeyGenerateTest {

    @Test
    public void getIdKey() {
        String s = IdKeyGenerate.getIdKey();
        return;
    }
}