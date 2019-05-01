package com.zhuhaoran.rebatemall.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhuHaoran
 * @className RegisterForm
 * @date 2019/4/14
 * @description
 */
@Data
public class RegisterForm {


    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

}