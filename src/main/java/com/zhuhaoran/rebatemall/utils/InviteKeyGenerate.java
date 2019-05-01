package com.zhuhaoran.rebatemall.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhuHaoran
 * @className InviteKeyGenerate
 * @date 2019/4/28
 * @description
 */
public class InviteKeyGenerate {

    public static synchronized String getInviteKey () {

        String val = "￥￥";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) {
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (nextInt + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(str)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val += "￥";
        return val;
    }

    public static synchronized Boolean matchKey(String str) {

        Pattern pattern = Pattern.compile("￥￥+.{10}￥");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static synchronized String getInviteKeyFromString(String str) {

        Pattern pattern = Pattern.compile("￥￥+.{10}￥");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        Integer start = matcher.start();
        Integer end = matcher.end();
        String result = "";
        for (int i = start; i < end; i++) {
            result += str.charAt(i);
        }
        return result;


    }
}
