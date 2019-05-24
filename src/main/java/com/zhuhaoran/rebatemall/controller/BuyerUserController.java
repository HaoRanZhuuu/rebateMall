package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.dataobject.UserInfo;
import com.zhuhaoran.rebatemall.enums.IdentificationStatusEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.form.LoginForm;
import com.zhuhaoran.rebatemall.form.RegisterForm;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import com.zhuhaoran.rebatemall.utils.IdKeyGenerate;
import com.zhuhaoran.rebatemall.utils.InviteKeyGenerate;
import com.zhuhaoran.rebatemall.utils.RedisUtils;
import com.zhuhaoran.rebatemall.utils.ResultVoUtil;
import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhuHaoran
 * @className UserController
 * @date 2019/4/13
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class BuyerUserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtils redisUtils;

    /**用户登录*/
    @CrossOrigin
    @PostMapping("/login")
    public ResultVo<String> login (@Valid LoginForm LoginForm , BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("登录失败，参数错误，userLoginForm={}",LoginForm);
            throw new MallException(ResultEnum.VARIABLE_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo userInfo = userInfoService.findByUserName(LoginForm.getUsername());
        if (StringUtils.isEmpty(userInfo)) {
            log.error("用户名不正确");
            throw new MallException(ResultEnum.USERNAME_OR_PASSWORD_ERROE);
        }
        if (userInfoService.matchingPasswordByUserName(LoginForm.getUsername(),LoginForm.getPassword()) == false) {
            log.error("密码不正确");
            throw new MallException(ResultEnum.USERNAME_OR_PASSWORD_ERROE);
        }
        /**
         * Todo 加入redais缓存，保存登录状态。
         */
        Map map = new HashMap();
        map.put("userId",userInfo.getUserId());
        return ResultVoUtil.success(map);
    }

    /**用户注册*/
    @CrossOrigin
    @PostMapping("/register")
    public ResultVo<String> register(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        //前端校验重复密码后将用户名密码返回后端
        UserInfo userInfoResult = userInfoService.findByUserName(registerForm.getUsername());
        if (!StringUtils.isEmpty(userInfoResult)) {
            log.error("用户名已被注册，请更换用户名或重新登陆");
            throw new MallException(2,"用户名已被注册，请更换用户名或重新登陆");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(IdKeyGenerate.getIdKey());
        userInfo.setUserName(registerForm.getUsername());
        userInfo.setPassword(registerForm.getPassword());
        UserInfo result = userInfoService.save(userInfo);
        if (result.getUserName() != userInfo.getUserName()) {
            log.error("注册失败");
            throw new MallException(ResultEnum.UNKNOWN_ERROR);
        }
        Map map = new HashMap();
        map.put("userName", userInfo.getUserName());
        return ResultVoUtil.success(map);
    }

    /**用户认证*/
    @CrossOrigin
    @PutMapping("/permission")
    public ResultVo permission(String userId) {
        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (userInfoService.findById(userId).getIsSpecial() == IdentificationStatusEnum.IS_IDENTIFICATION.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId).getWalletId())) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        UserInfo userInfo = userInfoService.findById(userId);
        userInfo.setIsSpecial(IdentificationStatusEnum.IS_IDENTIFICATION.getCode());
        userInfoService.save(userInfo);
        return ResultVoUtil.success();
    }

    /**生成用户邀请口令*/
    @CrossOrigin
    @GetMapping("/invite")
    public ResultVo invite(String userId) {
        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (userInfoService.findById(userId).getIsSpecial() == IdentificationStatusEnum.NOT_IDENTIFICATION.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sellerId", userId);
        String key = InviteKeyGenerate.getInviteKey();
        redisUtils.setRedisByHashMap(key, map);
        String shareWord = "复制口令到搜索栏进行认证，分享商品获得奖励金，口令：" + key;
        Map result = new HashMap();
        result.put("shareWord", shareWord);
        return ResultVoUtil.success(result);
    }

    /**通过邀请口令认证*/
    @CrossOrigin
    @PostMapping("/inviteBySeller")
    public ResultVo inviteBySeller(String sellerId, String userId) {
        if (userId.isEmpty() || sellerId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId)) || StringUtils.isEmpty(userInfoService.findById(sellerId))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (userInfoService.findById(userId).getIsSpecial() == IdentificationStatusEnum.IS_IDENTIFICATION.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        UserInfo userInfo = userInfoService.findById(userId);
        userInfo.setIsSpecial(IdentificationStatusEnum.IS_IDENTIFICATION.getCode());
        userInfo.setParentId(sellerId);
        userInfoService.save(userInfo);
        return ResultVoUtil.success();
    }
}
