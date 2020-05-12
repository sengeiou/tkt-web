package com.mtl.cypw.web.controller.member.vo;

import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/2/12.
 */
@Data
public class CheckInUserVO {

    @ApiModelProperty(value = "核销用户ID")
    private Integer id;
    @ApiModelProperty(value = "商户ID")
    private Integer enterpriseId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "真实名称")
    private String personName;
    @ApiModelProperty(value = "有效期开始时间")
    private Date beginDate;
    @ApiModelProperty(value = "有效期结束时间")
    private Date endDate;
    @ApiModelProperty(value = "用户访问令牌")
    private String accessToken;
}
