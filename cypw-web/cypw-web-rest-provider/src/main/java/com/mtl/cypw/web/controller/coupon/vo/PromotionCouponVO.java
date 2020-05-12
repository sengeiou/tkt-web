package com.mtl.cypw.web.controller.coupon.vo;

import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Data
public class PromotionCouponVO {
    @ApiModelProperty("couponId")
    private Integer couponId;
    @ApiModelProperty("优惠活动id")
    private Integer promotionId;
    @ApiModelProperty("卡券编码")
    private String couponCode;
    @ApiModelProperty("是否使用")
    private Integer isUsed;
    @ApiModelProperty("使用订单")
    private String orderNo;
    @ApiModelProperty("使用时间")
    private Date useDate;
    @ApiModelProperty("是否绑定会员")
    private Integer isBinded;
    @ApiModelProperty("绑定会员")
    private Integer memberId;
    @ApiModelProperty("绑定时间")
    private Date bindDate;
    @ApiModelProperty("是否启用")
    private Integer isEnable;
    @ApiModelProperty("用户优惠券使用状态")
    private CouponStateEnum couponState;
    @ApiModelProperty("优惠券详细信息")
    private PromotionVO promotionVO;
    @ApiModelProperty("优惠券领取时间")
    private Date beginDate;
    @ApiModelProperty("优惠券领取后过期时间")
    private Date endDate;

}
