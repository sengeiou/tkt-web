package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/1/6.
 */
@Data
public class ActivityVO {
    @ApiModelProperty("活动id")
    private Integer activityId;
    @ApiModelProperty("活动名称")
    private String activityName;
    @ApiModelProperty("活动简述")
    private String activityBrief;
    @ApiModelProperty("主页图片")
    private String activityImage;
    @ApiModelProperty("开始时间")
    private Date beginDate;
    @ApiModelProperty("结束时间")
    private Date endDate;
    @ApiModelProperty("活动详情展示类型")
    private Integer typeId;
    @ApiModelProperty("关联活动")
    private String activityUrl;
    @ApiModelProperty("是否有效")
    private Integer isEnable;
    @ApiModelProperty("企业id")
    private Integer enterpriseId;
    @ApiModelProperty("创建时间")
    private Date addDate;
    @ApiModelProperty("图文内容")
    private String activityContent;
}
