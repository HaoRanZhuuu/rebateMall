package com.zhuhaoran.rebatemall.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhuHaoran
 * @className FTPFileUtil
 * @date 2019/4/30
 * @description
 */
@Component
public class FTPFileUtil {

    private static final String FTP_IP = "127.0.0.1";
    private static final int FTP_PORT = 21;
    private static final String FTP_USERNAME = "zhr";
    private static final String FTP_PASSWORD = "961120";
    private static final String FTP_FILEPATH = "/img";

    public static boolean upLoadFile(String originFileName, InputStream inputStream) {
        boolean success = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            int reply;
            ftpClient.connect(FTP_IP, FTP_PORT);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(FTP_FILEPATH);
            ftpClient.changeWorkingDirectory(FTP_FILEPATH);
            ftpClient.storeFile(originFileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;

    }

}
