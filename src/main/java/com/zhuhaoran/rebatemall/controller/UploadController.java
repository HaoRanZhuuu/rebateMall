package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.utils.FTPFileUtil;
import com.zhuhaoran.rebatemall.utils.ResultVoUtil;
import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhuHaoran
 * @className UploadController
 * @date 2019/4/30
 * @description
 */

@Controller
public class UploadController {

    @CrossOrigin
    @GetMapping("/go")
    public String go() {
        return "upload.html";
    }
    @CrossOrigin
    @ResponseBody
    @PostMapping("/upload")
    /**文件上传*/
    public ResultVo upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boolean flag = FTPFileUtil.upLoadFile(fileName, inputStream);
        if (flag) {
            Map map = new HashMap();
            map.put("filename", fileName);
            return ResultVoUtil.success(map);
        } else {
            return ResultVoUtil.error(100,"error");
        }

    }
}
