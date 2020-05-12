package com.mtl.cypw.web.controller.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/3/18.
 */
@Data
public class UserVO {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("用户登录名")
    private String loginName;
    @ApiModelProperty("用户名称")
    private String personName;
    @ApiModelProperty("用户电话")
    private String personMobile;
    @ApiModelProperty("用户创建时间")
    private Date addDate;
    @ApiModelProperty("是否拥有所有权限")
    private Integer isAdministrator;
    @ApiModelProperty("企业id")
    private Integer enterpriseId;
    @ApiModelProperty("是否有打印权限")
    private Integer isPrint;
    @ApiModelProperty(value = "用户访问令牌")
    private String accessToken;
}
