package com.mtl.cypw.web.controller.order.vo;

import com.juqitech.service.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 11:02
 */
@Getter
@Setter
@ToString
@ApiModel(description = "订单明细")
public class OrderVO extends BaseVO {

    @ApiModelProperty(value = "订单ID", position = 1)
    private Integer orderId;

    @ApiModelProperty(value = "订单编号", position = 2)
    private String orderNo;

    @ApiModelProperty(value = "订单标题", position = 3)
    private String orderTitle;

    @ApiModelProperty(value = "订单市场价值总额", position = 4)
    private BigDecimal originAmount;

    @ApiModelProperty(value = "实际支付总额", position = 5)
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "订单总金额", position = 6)
    private BigDecimal ticketAmount;

    @ApiModelProperty(value = "礼品卡消费总额", position = 7)
    private BigDecimal giftCardAmount;

    @ApiModelProperty(value = "快递费", position = 8)
    private BigDecimal deliveryFee;

    @ApiModelProperty(value = "优惠总金额", position = 9)
    private BigDecimal discountFee;

    @ApiModelProperty(value = "票券总数", position = 9)
    private Integer quantity;

    @ApiModelProperty(value = "订单类型（CODE）", position = 10)
    private Integer orderType;

    @ApiModelProperty(value = "订单类型（NAME）", position = 10)
    private String orderTypeDesc;

    @ApiModelProperty(value = "订单状态（CODE）", position = 11)
    private String orderStatus;

    @ApiModelProperty(value = "订单状态（NAME）", position = 11)
    private String orderStatusDesc;

    @ApiModelProperty(value = "配送方式", position = 12)
    private String deliverType;

    @ApiModelProperty(value = "支付方式", position = 13)
    private String payType;

    @ApiModelProperty(value = "支付流水号", position = 13)
    private String transactionNo;

    @ApiModelProperty(value = "订单二维码（脱敏）", position = 14)
    private String fetchQrcode;

    @ApiModelProperty(value = "订单取票码（脱敏）", position = 15)
    private String fetchCode;

    @ApiModelProperty(value = "下单手机号", position = 16)
    private String mobileNo;

    @ApiModelProperty(value = "微信 OpenId", position = 17)
    private String wechatOpenId;

    @ApiModelProperty(value = "微信 AppId", position = 18)
    private String wechatAppId;

    @ApiModelProperty(value = "下单渠道", position = 19)
    private String channel;

    @ApiModelProperty(value = "下单平台", position = 20)
    private String source;

    @ApiModelProperty(value = "礼品卡ID", position = 21)
    private Integer giftCardId;

    @ApiModelProperty(value = "订单创建时间", position = 22)
    private Long orderTime;

    @ApiModelProperty(value = "订单取消时间", position = 23)
    private Long cancelTime;

    @ApiModelProperty(value = "订单支付成功时间", position = 24)
    private Long paidTime;

    @ApiModelProperty(value = "订单过期时间", position = 25)
    private Long expireTime;

    @ApiModelProperty(value = "出票成功时间", position = 26)
    private Long ticketedTime;

    @ApiModelProperty(value = "备注", position = 27)
    private String remark;

    @ApiModelProperty(value = "订单商品明细", position = 28)
    private List<OrderItemVO> orderItems;

    @ApiModelProperty(value = "订单票品明细", position = 29)
    private List<OrderTicketVO> orderTickets;

    @ApiModelProperty(value = "订单交付方式", position = 30)
    private OrderDeliveryVO delivery;

    @ApiModelProperty(value = "订单交易快照", position = 31)
    private OrderTransactionSnapshotVO snapshot;

    @ApiModelProperty(value = "剩余支付时间", position = 32)
    private Long remainingTime;

    @ApiModelProperty(value = "订单二维码（脱敏）Base64", position = 33)
    private String fetchQrCodeBase64;
}
