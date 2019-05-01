package com.zhuhaoran.rebatemall.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhuHaoran
 * @className ProductKeyGenerateTest
 * @date 2019/4/24
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductKeyGenerateTest {
    @Test
    public void getProductKey() {

        ProductKeyGenerate productKeyGenerate = new ProductKeyGenerate();
        String s = ProductKeyGenerate.getProductKey();

        String b = "";
        b += "234￥";
        b+=s;
        b += "123";
        String result = "";
        Pattern pattern = Pattern.compile("￥\\.+.{10}\\.￥");
        Matcher matcher = pattern.matcher(b);

        matcher.reset();
        boolean a =  matcher.find();
        String c = matcher.group();
        Integer d = matcher.start();
        Integer e = matcher.end();
        for (Integer i = d; i < e; i++) {
            result += b.charAt(i);
        }
        String aaaa = result;

        return;
    }

}