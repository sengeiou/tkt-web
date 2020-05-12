package com.mtl.cypw.web.controller.mpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Data
public class TheatreVO {
    @ApiModelProperty("剧院id")
    private Integer theatreId;
    @ApiModelProperty("剧院名称")
    private String theatreName;
    @ApiModelProperty("剧院地址")
    private String theatreAddress;
    @ApiModelProperty("剧院描述")
    private String theatreContent;
    @ApiModelProperty("剧院所在城市")
    private Integer cityCode;
    @ApiModelProperty("剧院图片")
    private String theatreImage;
    @ApiModelProperty("经纬度")
    private String longitudeAndLatitude;
}
