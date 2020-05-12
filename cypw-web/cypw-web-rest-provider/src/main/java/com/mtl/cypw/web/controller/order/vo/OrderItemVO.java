package com.mtl.cypw.web.controller.order.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-10 15:52
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "订单票品明细")
public class OrderItemVO extends BaseEntityInfo {

    @ApiModelProperty(value = "商品名称", position = 1)
    private String productTitle;

    @ApiModelProperty(value = "明细ID（场次/商品）", position = 2)
    private Integer itemId;

    @ApiModelProperty(value = "商品类别", position = 3)
    private String skuType;

    @ApiModelProperty(value = "票价ID", position = 4)
    private Integer priceId;

    @ApiModelProperty(value = "价格描述", position = 4)
    private String priceDesc;

    @ApiModelProperty(value = "详情图片（兼容衍生品）", position = 4)
    private String imageSrc;

    @ApiModelProperty(value = "是否套票", position = 5)
    private Boolean isPackage;

    @ApiModelProperty(value = "商品市场价", position = 6)
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品单价", position = 7)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "商品数量", position = 8)
    private Integer quantity;

    @ApiModelProperty(value = "优惠总额", position = 9)
    private BigDecimal discountFee;

    @ApiModelProperty(value = "实付总额", position = 10)
    private BigDecimal costFee;

}
