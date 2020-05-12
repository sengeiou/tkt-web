package com.mtl.cypw.web.controller.order.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-10 15:52
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "商品副本")
public class OrderTransactionSnapshotVO extends BaseEntityInfo {

    private Integer orderSnapshotId;

    @ApiModelProperty(value = "场馆ID", position = 1)
    private Integer venueId;

    @ApiModelProperty(value = "场馆名称", position = 2)
    private String venueName;

    @ApiModelProperty(value = "项目ID", position = 3)
    private Integer projectId;

    @ApiModelProperty(value = "项目名称", position = 4)
    private String projectName;

    @ApiModelProperty(value = "详情页海报", position = 5)
    private String detailPosterUrl;

    @ApiModelProperty(value = "列表页海报", position = 6)
    private String listPosterUrl;

    @ApiModelProperty(value = "场次ID", position = 7)
    private Integer eventId;

    @ApiModelProperty(value = "场次名称", position = 8)
    private String eventName;

    @ApiModelProperty(value = "演出时间", position = 9)
    private String showName;

    @ApiModelProperty(value = "剧院名称", position = 10)
    private String theatreName;

    @ApiModelProperty(value = "剧院地址", position = 11)
    private String theatreAddress;

}
