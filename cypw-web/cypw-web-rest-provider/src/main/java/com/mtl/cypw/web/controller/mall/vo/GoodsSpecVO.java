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
@ApiModel(description = "商品规格详情")
public class GoodsSpecVO extends BaseEntityInfo {

    @ApiModelProperty(value = "规格票价ID", position = 1)
    private Integer skuId;

    @ApiModelProperty(value = "衍生品商品ID", position = 2)
    private Integer goodsId;

    @ApiModelProperty(value = "规格名称", position = 3)
    private String priceTitle;

    @ApiModelProperty(value = "规格详情图片", position = 4)
    private String imageSrc;

    @ApiModelProperty(value = "市场价", position = 5)
    private BigDecimal priceOrigin;

    @ApiModelProperty(value = "售价", position = 6)
    private BigDecimal priceValue;

    @ApiModelProperty(value = "可售库存", position = 7)
    private Integer stockQty;

    @ApiModelProperty(value = "是否已上架", position = 8)
    private Boolean enable;

    @ApiModelProperty(value = "最少购买数", position = 9)
    private Integer minQty;


}
