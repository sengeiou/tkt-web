package com.mtl.cypw.web.controller.coupon.vo;

import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.domain.coupon.enums.PromotionBusinessTypeEnum;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.show.enums.ProgramTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Data
public class PromotionVO {

    @ApiModelProperty("优惠id")
    private Integer promotionId;
    @ApiModelProperty("业务类型,1：票品类，2:衍生品类")
    private PromotionBusinessTypeEnum businessType;
    @ApiModelProperty("优惠类型,1：折扣券，2:现金券，3:兑换券")
    private PromotionTypeEnum promotionType;
    @ApiModelProperty("活动名称")
    private String promotionName;
    @ApiModelProperty("优惠限制，0：全部演出可用，1:指定分类可用，2:指定演出可用")
    private Integer programPointId;
    @ApiModelProperty("可用分类，类型")
    private List<ProgramTypeEnum> programTypes;
    @ApiModelProperty("折扣率")
    private BigDecimal promotionDiscount;
    @ApiModelProperty("抵扣现金")
    private BigDecimal promotionAmount;
    @ApiModelProperty("企业id")
    private Integer enterpriseId;
    @ApiModelProperty("编号")
    private String promotionCode;
    @ApiModelProperty("优惠券可领取，开始时间")
    private Date promotionBeginDate;
    @ApiModelProperty("优惠券可领取，结束时间")
    private Date promotionEndDate;
    @ApiModelProperty("订单金额大于..可用")
    private BigDecimal orderAmountRestriction;
    @ApiModelProperty("购买数量大于..可用")
    private Integer minQtyRestriction;
    @ApiModelProperty("购买数量小于..可用")
    private Integer maxQtyRestriction;
    @ApiModelProperty("指定支付方式可用")
    private String paymentRestriction;
    @ApiModelProperty("是否已领取")
    private Integer isReceive = 0;
    @ApiModelProperty("用户优惠券使用状态")
    private CouponStateEnum couponState;
    @ApiModelProperty("领取后多少天内有效")
    private Integer availableDay;
    @ApiModelProperty("优惠券有效期开始时间")
    private Date availableBeginDate;
    @ApiModelProperty("优惠券有效期截止时间")
    private Date availableEndDate;
}
