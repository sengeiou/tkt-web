package com.mtl.cypw.web.controller.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2020/3/12.
 */
@Data
public class MachineVO {

    @ApiModelProperty(value = "核销用户ID")
    private Integer machineId;
    @ApiModelProperty(value = "商户ID")
    private Integer enterpriseId;
    @ApiModelProperty(value = "密钥")
    private String machineKey;
    @ApiModelProperty(value = "MAC地址")
    private String macAddress;
    @ApiModelProperty(value = "用户访问令牌")
    private String accessToken;
}
