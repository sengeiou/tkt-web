package com.mtl.cypw.web.controller.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2020/3/18.
 */
@Data
public class AdminUserLoginParam {

    @ApiModelProperty(value = "用户名", required = true)
    private String loginName;

    @ApiModelProperty(value = "密码", required = true)
    private String loginPass;

    @ApiModelProperty(value = "是否强制登录,0：否，1：是，默认为0")
    private int isForce;

    public Boolean checkParam() {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPass)) {
            return false;
        }
        return true;
    }
}

