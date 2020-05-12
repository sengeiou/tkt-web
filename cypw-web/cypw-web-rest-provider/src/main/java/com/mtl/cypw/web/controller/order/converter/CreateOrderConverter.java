package com.mtl.cypw.web.controller.order.converter;

import com.google.common.collect.Lists;
import com.juqitech.converter.DataConverter;
import com.mtl.cypw.common.enums.SkuTypeEnum;
import com.mtl.cypw.common.utils.Money;
import com.mtl.cypw.domain.order.enums.DeliverTypeEnum;
import com.mtl.cypw.domain.order.enums.IdentityTypeEnum;
import com.mtl.cypw.domain.order.enums.OrderTypeEnum;
import com.mtl.cypw.domain.order.param.OrderCouponParam;
import com.mtl.cypw.domain.order.param.UniformSeatParam;
import com.mtl.cypw.domain.order.param.UniformSeatTicketParam;
import com.mtl.cypw.domain.order.param.UniformSkuParam;
import com.mtl.cypw.domain.order.param.UserCreateOrderParam;
import com.mtl.cypw.web.controller.order.param.CreateOrderParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 13:52
 */
@Slf4j
@Component
public class CreateOrderConverter extends DataConverter<CreateOrderParam, UserCreateOrderParam> {

    @Override
    public UserCreateOrderParam convert(CreateOrderParam object) {
        UserCreateOrderParam param = new UserCreateOrderParam();
        param.setSrc(object.getSrc());
        param.setChannel(object.getChannel());
        param.setClientIp(object.getClientIp());
        param.setShowId(object.getEventId());
        param.setEnterpriseId(object.getTenantId());
        param.setWeChatAppId(object.getWeChatAppId());
        param.setWeChatOpenId(object.getWeChatOpenId());
        param.setMemberId(object.getMemberId());
        param.setTotalPrice(new Money(object.getTotalPrice()));
        param.setOrderAmount(new Money(object.getOrderAmount()));
        param.setMobileNo(object.getMobileNo());
        param.setDeliverType(DeliverTypeEnum.valueOf(object.getDeliverType()));
        param.setOrderType(OrderTypeEnum.getObject(object.getOrderType()));
        CreateOrderParam.OrderRecipientInfo recipientInfo = object.getRecipientInfo();
        if (recipientInfo != null) {
            param.setIdentityType(IdentityTypeEnum.getObject(recipientInfo.getIdentityType()));
            param.setRecipientIdNo(recipientInfo.getRecipientIdNo());
            param.setRecipientMobile(recipientInfo.getRecipientMobile());
            param.setRecipientName(recipientInfo.getRecipientName());
            if (recipientInfo.getRecipientAddressId() != null) {
                param.setRecipientAddressId(recipientInfo.getRecipientAddressId());
            }
        }
        List<CreateOrderParam.OrderSkuInfo> skuInfos = object.getSkuInfos();
        if (CollectionUtils.isNotEmpty(skuInfos)) {
            List<UniformSkuParam> skuParams = Lists.transform(skuInfos, orderSkuInfo -> {
                UniformSkuParam skuParam = new UniformSkuParam();
                skuParam.setSkuId(orderSkuInfo.getSkuId());
                skuParam.setType(SkuTypeEnum.getObject(orderSkuInfo.getType()));
                skuParam.setCount(orderSkuInfo.getCount());
                return skuParam;
            });
            param.setSkuRequests(skuParams);
        }
        List<CreateOrderParam.OrderCouponInfo> couponInfos = object.getCouponInfos();
        if (CollectionUtils.isNotEmpty(couponInfos)) {
            List<OrderCouponParam> couponParams = Lists.transform(couponInfos, couponInfo -> {
                OrderCouponParam couponParam = new OrderCouponParam();
                couponParam.setPromotionId(couponInfo.getPromotionId());
                couponParam.setCouponType(couponInfo.getCouponType());
                couponParam.setCouponId(couponInfo.getCouponId());
                couponParam.setSkuId(couponInfo.getSkuId());
                return couponParam;
            });
            param.setOrderCouponParams(couponParams);
        }
        List<CreateOrderParam.OrderTicketSeatInfo> seatGroupInfos = object.getSeatGroupInfos();
        if (CollectionUtils.isNotEmpty(seatGroupInfos)) {
            List<UniformSeatTicketParam> seatTicketParams = Lists.transform(seatGroupInfos, seatTicketInfo -> {
                UniformSeatTicketParam seatTicketParam = new UniformSeatTicketParam();
                seatTicketParam.setTicketId(seatTicketInfo.getPriceId());
                if (CollectionUtils.isNotEmpty(seatTicketInfo.getSeatInfos())) {
                    List<UniformSeatParam> seatParams = Lists.transform(seatTicketInfo.getSeatInfos(), orderSeatInfo -> {
                        UniformSeatParam seatParam = new UniformSeatParam();
                        seatParam.setSeatId(orderSeatInfo.getSeatId());
                        seatParam.setSeatName(orderSeatInfo.getSeatName());
                        return seatParam;
                    });
                    seatTicketParam.setSeats(seatParams);
                }
                return seatTicketParam;
            });
            param.setUniformSeatRequests(seatTicketParams);
        }
        return param;
    }
}
