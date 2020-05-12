package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class SeatVO {

    @ApiModelProperty(value = "座位ID")
    private Integer seatId;

    @ApiModelProperty(value = "场次ID")
    private Integer eventId;

    @ApiModelProperty(value = "模板区域ID")
    private Integer zoneId;

    @ApiModelProperty(value = "模板区域名称")
    private String zoneName;

    @ApiModelProperty(value = "票价ID")
    private Integer priceId;

    @ApiModelProperty(value = "票价")
    private BigDecimal price;

    @ApiModelProperty(value = "背景色")
    private String bgColor;

    @ApiModelProperty(value = "物理行坐标")
    private Integer rowInt;

    @ApiModelProperty(value = "物理列坐标")
    private Integer colInt;

    @ApiModelProperty(value = "逻辑行")
    private String rowName;

    @ApiModelProperty(value = "逻辑列")
    private String colName;

    @ApiModelProperty(value = "高")
    private Integer height;

    @ApiModelProperty(value = "宽")
    private Integer width;

    @ApiModelProperty(value = "标识前缀")
    private Integer onlyPrefix;

    @ApiModelProperty(value = "座位状态（1-可售,2-预留,3-已售,4-不可售）")
    private Integer seatStatus;
}
