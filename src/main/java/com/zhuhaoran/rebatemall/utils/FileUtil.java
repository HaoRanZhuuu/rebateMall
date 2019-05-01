package com.zhuhaoran.rebatemall.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ZhuHaoran
 * @className FileUtil
 * @date 2019/4/30
 * @description
 */
public class FileUtil {
    public static void uploadFile(byte[] file ,String filePath ,String fileName) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            System.out.println(filePath);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
