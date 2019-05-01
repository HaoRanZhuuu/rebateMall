package com.zhuhaoran.rebatemall.dataobject;

import com.zhuhaoran.rebatemall.enums.IdentificationStatusEnum;
import com.zhuhaoran.rebatemall.enums.NewUserStatusEnum;
import com.zhuhaoran.rebatemall.enums.UserSexEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className UserInfo
 * @date 2019/4/12
 * @description
 */

@Entity
@Data
public class UserInfo {

    @Id
    private String userId;

    private String userName;

    private String userLogo;

    private String password;

    private Integer userSex = UserSexEnum.SECRET.getCode();

    private String email;

    private String phone;

    private String parentId;

    private Integer isSpecial = IdentificationStatusEnum.NOT_IDENTIFICATION.getCode();

    private Integer isNew = NewUserStatusEnum.IS_NEW.getCode();

    private String walletId;

    public UserInfo() {
    }


}
