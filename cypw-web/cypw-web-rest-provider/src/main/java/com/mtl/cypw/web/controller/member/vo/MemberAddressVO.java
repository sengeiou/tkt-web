package com.mtl.cypw.web.controller.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class MemberAddressVO {

    @ApiModelProperty(value = "地址ID")
    private Integer addressId;
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;
    @ApiModelProperty(value = "收货名称")
    private String deliveryName;
    @ApiModelProperty(value = "收货电话")
    private String deliveryMobile;
    @ApiModelProperty(value = "省.代码")
    private Integer provinceCode;
    @ApiModelProperty(value = "省.名称")
    private String provinceName;
    @ApiModelProperty(value = "市.代码")
    private Integer cityCode;
    @ApiModelProperty(value = "市.名称")
    private String cityName;
    @ApiModelProperty(value = "区.代码")
    private Integer districtCode;
    @ApiModelProperty(value = "区.名称")
    private String districtName;
    @ApiModelProperty(value = "收货地址")
    private String deliveryAddress;
    @ApiModelProperty(value = "添加时间")
    private Date addDate;
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
    @ApiModelProperty(value = "商户ID")
    private Integer enterpriseId;
    @ApiModelProperty(value = "是否为默认地址")
    private Byte isDefault;
}
