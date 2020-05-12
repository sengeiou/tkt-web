package com.mtl.cypw.web.controller.mall.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 11:41
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "商品详情")
public class GoodsDetailVO extends BaseEntityInfo {

    @ApiModelProperty(value = "商品ID", position = 1)
    private Integer goodsId;

    @ApiModelProperty(value = "商品编码", position = 2)
    private String goodsCode;

    @ApiModelProperty(value = "商品名称", position = 3)
    private String goodsName;

    @ApiModelProperty(value = "商品简述", position = 4)
    private String goodsBrief;

    @ApiModelProperty(value = "商品图片（入口图)", position = 5)
    private String goodsImage;

    @ApiModelProperty(value = "售卖开始时间", position = 6)
    private Date beginDate;

    @ApiModelProperty(value = "售卖结束时间", position = 7)
    private Date endDate;

    @ApiModelProperty(value = "每单限制数量", position = 8)
    private Integer limitCnt;

    @ApiModelProperty(value = "是否可售卖", position = 9)
    private Boolean saleable;

    @ApiModelProperty(value = "排序", position = 10)
    private Integer sortOrder;

    @ApiModelProperty(value = "商品最低售价", position = 11)
    private BigDecimal bottomPrice;

    @ApiModelProperty(value = "购买须知", position = 12)
    private String purchaseNotice;

    @ApiModelProperty(value = "图文内容", position = 12)
    private String content;

}
