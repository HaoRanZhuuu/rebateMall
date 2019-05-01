package com.zhuhaoran.rebatemall.exception;

import com.zhuhaoran.rebatemall.enums.ResultEnum;
import lombok.Getter;

/**
 * @author ZhuHaoran
 * @className mallException
 * @date 2019/4/13
 * @description
 */

@Getter
public class MallException extends RuntimeException{

    private Integer code;

    public MallException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public MallException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
