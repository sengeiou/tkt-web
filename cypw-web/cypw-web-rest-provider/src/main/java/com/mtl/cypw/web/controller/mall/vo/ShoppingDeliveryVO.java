package com.mtl.cypw.web.controller.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-10 14:16
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "衍生品商品交付方式")
public class ShoppingDeliveryVO {

    @ApiModelProperty(value = "取票方式", position = 1)
    private String deliverType;

    @ApiModelProperty(value = "温馨提示（快递公司）", position = 2)
    private String tips;

    @ApiModelProperty(value = "配送费", position = 3)
    private Integer expressFee;

    @ApiModelProperty(value = "订单免邮金额限制（0：不限制，其他则按订单金额计算）", position = 4)
    private Integer freeShippingRestrict;

    @ApiModelProperty(value = "取票地址", position = 5)
    private String fetchAddress;

    @ApiModelProperty(value = "联系电话", position = 6)
    private String contactMobile;

    @ApiModelProperty(value = "上门取票的服务时间说明", position = 7)
    private String fetchTimeDesc;

}
