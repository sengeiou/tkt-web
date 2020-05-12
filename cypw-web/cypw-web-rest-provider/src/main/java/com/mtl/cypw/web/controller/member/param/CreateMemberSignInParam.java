package com.mtl.cypw.web.controller.member.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Data
public class CreateMemberSignInParam {
    @ApiModelProperty(value = "订单id")
    private Integer orderId;
    @ApiModelProperty(value = "项目id", required = true)
    private Integer programId;
    @ApiModelProperty(value = "场次id", required = true)
    private Integer eventId;
    @ApiModelProperty(value = "剧院id", required = true)
    private Integer theatreId;
    @ApiModelProperty(value = "签到地址", required = true)
    private String signInAddress;
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "签到类型，1：正常签到，2：迟到签到，3：异地签到，4：异地迟到签到")
    private Integer type;
    @ApiModelProperty(value = "备注")
    private String remark;
}
