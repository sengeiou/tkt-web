package com.mtl.cypw.web.controller.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Data
public class SignInVO {

    @ApiModelProperty(value = "订单id")
    private Integer orderId;
    @ApiModelProperty(value = "项目id")
    private Integer programId;
    @ApiModelProperty(value = "场次id")
    private Integer eventId;
    @ApiModelProperty(value = "场次时间")
    private Date eventDate;
    @ApiModelProperty(value = "剧院id")
    private Integer theatreId;
    @ApiModelProperty(value = "签到地址")
    private String theatreAddress;
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;

}
