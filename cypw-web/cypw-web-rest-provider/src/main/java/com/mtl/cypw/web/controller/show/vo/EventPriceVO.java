package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class EventPriceVO {

    @ApiModelProperty(value = "票价ID")
    private Integer priceId;
    @ApiModelProperty(value = "场次ID")
    private Integer eventId;
    @ApiModelProperty(value = "原价")
    private String priceOrigin;
    @ApiModelProperty(value = "销售价")
    private BigDecimal priceValue;
    @ApiModelProperty(value = "票所在区域")
    private String priceClass;
    @ApiModelProperty(value = "总库存")
    private Integer totalQty;
    @ApiModelProperty(value = "价格别名")
    private String priceTitle;
    @ApiModelProperty(value = "库存数量")
    private Integer stockQty;
    @ApiModelProperty(value = "已售数量")
    private Integer soldQty;
    @ApiModelProperty(value = "有效性")
    private Integer isEnable;
    @ApiModelProperty(value = "最少购票数")
    private Integer minQty;
    @ApiModelProperty(value = "票价颜色")
    private String priceColor;

    @ApiModelProperty(value = "座位列表")
    private List<SeatVO> seats;
}
