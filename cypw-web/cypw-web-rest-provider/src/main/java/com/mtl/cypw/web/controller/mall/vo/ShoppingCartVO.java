package com.mtl.cypw.web.controller.mall.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 11:41
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "购物车信息")
public class ShoppingCartVO extends BaseEntityInfo {

    @ApiModelProperty(value = "购物车ID", position = 1)
    private Integer shoppingCartId;

    @ApiModelProperty(value = "用户ID", position = 2)
    private Integer memberId;

    @ApiModelProperty(value = "商品ID", position = 3)
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称", position = 4)
    private String goodsName;

    @ApiModelProperty(value = "商品链接", position = 5)
    private String goodsImgSrc;

    @ApiModelProperty(value = "SKU 类型", position = 6)
    private Integer skuType;

    @ApiModelProperty(value = "SKU ID", position = 7)
    private Integer skuId;

    @ApiModelProperty(value = "SKU 名称", position = 8)
    private String skuName;

    @ApiModelProperty(value = "售卖价（单价）", position = 9)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "指导价（原价）", position = 10)
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "购买数量", position = 11)
    private Integer quantity;

    @ApiModelProperty(value = "每单限制", position = 11)
    private Integer limitCnt;

    @ApiModelProperty(value = "购物车有效状态", position = 12)
    private Boolean enable;

}
