package com.mtl.cypw.web.controller.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Data
public class MemberSignInVO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer memberId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("项目id")
    private Integer programId;

    @ApiModelProperty("场次id")
    private Integer eventId;

    @ApiModelProperty("剧院id")
    private Integer theatreId;

    @ApiModelProperty("签到地址")
    private String signInAddress;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;
    /**
     * 签到类型
     * 1：正常签到，2：迟到签到，3：异地签到，4：异地迟到签到
     */
    @ApiModelProperty("签到类型")
    private Integer type;

    @ApiModelProperty("商户id")
    private Integer enterpriseId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("签到时间")
    private Date signInTime;
}
