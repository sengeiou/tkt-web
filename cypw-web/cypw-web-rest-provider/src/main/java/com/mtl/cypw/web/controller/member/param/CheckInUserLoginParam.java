package com.mtl.cypw.web.controller.member.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2020/2/12.
 */
@Data
public class CheckInUserLoginParam {

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String userPass;

    public Boolean checkParam() {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPass)) {
            return false;
        }
        return true;
    }
}
