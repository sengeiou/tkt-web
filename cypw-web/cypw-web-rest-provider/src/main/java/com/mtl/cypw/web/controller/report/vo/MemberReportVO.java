package com.mtl.cypw.web.controller.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2019/12/13.
 */
@Data
public class MemberReportVO {

    @ApiModelProperty("待付款订单数")
    private Integer notPayOrderNum;

    @ApiModelProperty("待参加订单数")
    private Integer waitAttendOrderNum;

    @ApiModelProperty("优惠券数量")
    private Integer couponNum;

    @ApiModelProperty("兑换券数量")
    private Integer exchangeCouponNum;

    @ApiModelProperty("礼品卡数量")
    private Integer cardNum;

}
