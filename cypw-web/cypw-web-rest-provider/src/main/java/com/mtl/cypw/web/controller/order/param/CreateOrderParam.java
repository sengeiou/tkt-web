package com.mtl.cypw.web.controller.order.param;

import com.juqitech.request.BaseParam;
import com.juqitech.service.enums.PlatformSource;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.order.enums.ChannelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 11:57
 */
@Data
public class CreateOrderParam extends BaseParam {

    @ApiModelProperty(value = "订单支付总金额", required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "订单商品总金额[购买商品明细]", required = true)
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "订单组合类型[1-仅票品, 2-衍生品]", required = true)
    private Integer orderType;

    @ApiModelProperty(value = "交付方式[OFFLINE-现场取票, EXPRESS-快递配送, SPOT_PICKING-自助取票, E_TICKET-电子票]", required = true)
    private String deliverType;

    @ApiModelProperty(value = "场次ID[票品下单]", required = false)
    private Integer eventId;

    @ApiModelProperty(value = "订单收货人信息", required = true)
    private OrderRecipientInfo recipientInfo;

    @ApiModelProperty(value = "优惠使用情况[普通优惠只能使用一张，兑换券可以使用多张]", required = false)
    private List<OrderCouponInfo> couponInfos;

    @ApiModelProperty(value = "非选座下单 sku 明细[暂不考虑组合模式下单]", required = true)
    private List<OrderSkuInfo> skuInfos;

    @ApiModelProperty(value = "选座下单明细[按票档维度分组]", required = false)
    private List<OrderTicketSeatInfo> seatGroupInfos;

    @ApiModelProperty(value = "下单手机", required = true)
    private String mobileNo;

    @ApiModelProperty(value = "会员ID", required = false)
    private Integer memberId;

    @ApiModelProperty(value = "商户ID", required = false)
    private Integer tenantId;

    @ApiModelProperty(value = "下单IP", required = false)
    private String clientIp;

    @ApiModelProperty(value = "下单渠道", required = false)
    private ChannelEnum channel;

    @ApiModelProperty(value = "下单平台", required = false)
    private PlatformSource src;

    @ApiModelProperty(value = "微信 appId, 本期忽略", required = false)
    private String weChatAppId;

    @ApiModelProperty(value = "微信 openId, 本期忽略", required = false)
    private String weChatOpenId;

    @Data
    public static class OrderSkuInfo {

        @ApiModelProperty(value = "SKUID, 票品即票价ID", required = true)
        private Integer skuId;

        @ApiModelProperty(value = "SKUTYPE, 即两种类型[1-普通票品, 3-衍生品]", required = true)
        private Integer type;

        @ApiModelProperty(value = "购买数量", required = true)
        private Integer count;

    }

    @Data
    public static class OrderTicketSeatInfo {

        @ApiModelProperty(value = "PriceId, 票品即票价ID", required = true)
        private Integer priceId;

        @ApiModelProperty(value = "选购座位列表", required = true)
        private List<OrderSeatInfo> seatInfos;

    }

    @Data
    public static class OrderSeatInfo {

        @ApiModelProperty(value = "SEATID, 座位ID", required = true)
        private Integer seatId;

        @ApiModelProperty(value = "SEATNAME, 即 逻辑行号:逻辑列表（rowName:colName）", required = true)
        private String seatName;

    }

    @Data
    public static class OrderCouponInfo {

        @ApiModelProperty(value = "优惠活动ID", required = true)
        private Integer promotionId;

        @ApiModelProperty(value = "优惠券码ID", required = true)
        private Integer couponId;

        @ApiModelProperty(value = "优惠类型（1-折扣券, 2-抵现券, 3-兑换券）", required = true)
        private PromotionTypeEnum couponType;

        @ApiModelProperty(value = "绑定的SKUID, 当 couponType 为兑换券时不能为空", required = false)
        private Integer skuId;
    }

    @Data
    public static class OrderRecipientInfo {

        @ApiModelProperty(value = "接收人姓名", required = true)
        private String recipientName;

        @ApiModelProperty(value = "接收人手机", required = true)
        private String recipientMobile;

        @ApiModelProperty(value = "接收人证件号类型[IDENTITY_CARD-身份证, PASS_PORT-护照]", required = false)
        private String identityType;

        @ApiModelProperty(value = "接收人证件号[当配送要求实名签收时不能为空]", required = false)
        private String recipientIdNo;

        @ApiModelProperty(value = "收货地址[deliverType=EXPRESS 时, 收货地址不能为空]", required = false)
        private Integer recipientAddressId;
    }

    @Override
    public void checkParam() {

    }

}
