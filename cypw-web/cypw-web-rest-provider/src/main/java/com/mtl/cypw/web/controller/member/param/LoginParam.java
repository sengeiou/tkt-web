package com.mtl.cypw.web.controller.member.param;

import com.mtl.cypw.domain.mpm.enums.LoginTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2019/11/28.
 */
@Data
public class LoginParam {

    @ApiModelProperty(value = "手机号")
    private String memberMobile;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "微信code")
    private String code;

    @ApiModelProperty(value = "向量")
    private String iv;

    @ApiModelProperty(value = "加密数据")
    private String encodeParam;

    @ApiModelProperty(value = "登录方式（NORMAL：正常登录，JINGYI：警艺登录，CMBAUTH：招行授权登录，MINIAPP：小程序登录）")
    private LoginTypeEnum loginType;

    public Boolean checkParam() {
        if (loginType == null) {
            loginType = LoginTypeEnum.NORMAL;
        }

        if (LoginTypeEnum.NORMAL.equals(loginType)) {
            if (StringUtils.isEmpty(memberMobile) || StringUtils.isEmpty(verifyCode)) {
                return false;
            }
        } else if (LoginTypeEnum.JINGYI.equals(loginType)) {
            if (StringUtils.isEmpty(encodeParam)) {
                return false;
            }
        } else if (LoginTypeEnum.CMBAUTH.equals(loginType)) {
            if (StringUtils.isEmpty(encodeParam)) {
                return false;
            }
        } else if (LoginTypeEnum.MINIAPP.equals(loginType)) {
            if (StringUtils.isNotEmpty(memberMobile)) {
                return true;
            }
            if (StringUtils.isEmpty(encodeParam) || StringUtils.isEmpty(iv) || StringUtils.isEmpty(code)) {
                return false;
            }
        }
        return true;
    }
}
