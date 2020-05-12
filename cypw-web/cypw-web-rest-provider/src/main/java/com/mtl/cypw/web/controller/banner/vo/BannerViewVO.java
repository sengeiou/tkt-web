package com.mtl.cypw.web.controller.banner.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-18 15:52
 */
@Data
@ApiModel(description = "轮播(Banner)")
public class BannerViewVO {

    @ApiModelProperty(value = "编号", position = 1)
    private Integer bannerId;

    @ApiModelProperty(value = "名称", position = 2)
    private String bannerName;

    @ApiModelProperty(value = "图片", position = 3)
    private String bannerImage;

    @ApiModelProperty(value = "链接地址", position = 4)
    private String bannerUrl;

    @ApiModelProperty(value = "链接类型，外部/内部(1-外部, 2-内部)", position = 5)
    private Integer linkType;

    @ApiModelProperty(value = "资源类型，演出/衍生品(1-票品订单, 2-衍生品订单)", position = 6)
    private Integer bannerType;

    @ApiModelProperty(value = "资源类型Id", position = 7)
    private Integer resourceId;

    @ApiModelProperty(value = "序号", position = 8)
    private Integer sortOrder;

    @ApiModelProperty(value = "banner描述", position = 9)
    private String bannerDesc;

}
