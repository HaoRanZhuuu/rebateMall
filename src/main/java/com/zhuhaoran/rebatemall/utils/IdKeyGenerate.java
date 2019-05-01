package com.zhuhaoran.rebatemall.utils;

import java.util.Random;

/**
 * @author ZhuHaoran
 * @className IdKeyGenerate
 * @date 2019/4/12
 * @description
 */
public class IdKeyGenerate {
    public static synchronized String getIdKey(){

        Random random = new Random();
        Integer num = random.nextInt(9000000)+1000000;
        return System.currentTimeMillis()+String.valueOf(num);
    }

    public static synchronized Integer getCateId() {
        Random random = new Random();
        Integer num = random.nextInt(900000)+100000;
        return num;
    }
}
