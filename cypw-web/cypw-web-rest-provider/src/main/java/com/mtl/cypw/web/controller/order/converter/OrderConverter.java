package com.mtl.cypw.web.controller.order.converter;

import com.google.common.collect.Lists;
import com.juqitech.converter.DataConverter;
import com.mtl.cypw.common.utils.FileUtil;
import com.mtl.cypw.domain.order.dto.*;
import com.mtl.cypw.domain.order.enums.OrderStatusEnum;
import com.mtl.cypw.web.controller.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 13:52
 */
@Slf4j
@Component
public class OrderConverter extends DataConverter<OrderDTO, OrderVO> {

    @Override
    public OrderVO convert(OrderDTO dto) {
        if (dto == null) {
            return null;
        }
        OrderVO vo = new OrderVO();
        vo.setOrderId(dto.getOrderId());
        vo.setOrderNo(dto.getOrderNo());
        vo.setOrderTitle(dto.getOrderTitle());
        vo.setMobileNo(dto.getMobileNo());
        vo.setOrderType(dto.getOrderType().getCode());
        vo.setOrderTypeDesc(dto.getOrderType().getName());
        vo.setOrderStatus(dto.getOrderStatus().getName());
        vo.setOrderStatusDesc(dto.getOrderStatus().getDesc());
        vo.setChannel(dto.getChannel().getName());
        if (dto.getDeliveryFee() != null) {
            vo.setDeliveryFee(dto.getDeliveryFee().getAmount());
        }
        vo.setDelivery(buildDelivery(dto.getDelivery()));

        vo.setGiftCardId(dto.getGiftCardId());
        if (dto.getGiftCardAmount() != null) {
            vo.setGiftCardAmount(dto.getGiftCardAmount().getAmount());
        }
        if (dto.getPayType() != null) {
            vo.setPayType(dto.getPayType().getPayName());
        } else {
            vo.setPayType("-");
        }
        // 交易流水号
        vo.setTransactionNo(dto.getTransactionFlowNo());
        if (dto.getPaidTime() != null) {
            vo.setPaidTime(dto.getPaidTime().getTime());
        }
        if (dto.getDiscountFee() != null) {
            vo.setDiscountFee(dto.getDiscountFee().getAmount());
        }
        if (dto.getOriginAmount() != null) {
            vo.setOriginAmount(dto.getOriginAmount().getAmount());
        }
        if (dto.getActualAmount() != null) {
            vo.setActualAmount(dto.getActualAmount().getAmount());
        }
        if (dto.getTicketAmount() != null) {
            vo.setTicketAmount(dto.getTicketAmount().getAmount());
        }

        vo.setFetchCode(dto.getFetchCode());
        vo.setFetchQrcode(dto.getFetchQrcode());
        if (StringUtils.isNotEmpty(dto.getFetchQrcode())) {
            String str = FileUtil.generateQrCodeToBase64(dto.getFetchQrcode(), "jpg", 240, 240);
            vo.setFetchQrCodeBase64(str);
        }

        vo.setOrderItems(buildOrderItems(dto.getOrderItems()));
        vo.setOrderTickets(buildOrderTickets(dto.getOrderTickets()));
        vo.setQuantity(dto.getOrderTickets().size());
        vo.setSnapshot(buildSnapshot(dto.getSnapshot()));
        vo.setSource(dto.getSource().getDisplayName());

        if (dto.getOrderTime() != null) {
            vo.setOrderTime(dto.getOrderTime().getTime());
        }
        if (dto.getCancelTime() != null) {
            vo.setCancelTime(dto.getCancelTime().getTime());
        }
        if (dto.getExpireTime() != null) {
            vo.setExpireTime(dto.getExpireTime().getTime());
            if (OrderStatusEnum.LOCKED.getCode() == dto.getOrderStatus().getCode()) {
                vo.setRemainingTime(dto.getExpireTime().getTime() - System.currentTimeMillis());
            }
        }
        if (dto.getTicketedTime() != null) {
            vo.setTicketedTime(dto.getTicketedTime().getTime());
        }
        vo.setWechatAppId(dto.getWechatAppId());
        vo.setWechatOpenId(dto.getWechatOpenId());
        vo.setRemark(dto.getRemark());
        return vo;
    }


    private OrderDeliveryVO buildDelivery(OrderDeliveryDTO object) {
        if (object == null) {
            return null;
        }
        OrderDeliveryVO vo = new OrderDeliveryVO();
        if (object.getDeliverType() != null) {
            vo.setDeliverType(object.getDeliverType().getName());
            vo.setDeliverTypeName(object.getDeliverType().getDesc());
        }
        if (object.getDeliveryStatus() != null) {
            vo.setDeliveryStatus(object.getDeliveryStatus().getName());
        }
        vo.setAddresseeName(object.getAddresseeName());
        vo.setAddresseeMobile(object.getAddresseeMobile());
        vo.setNeedIdcard(object.getNeedIdcard());
        if (object.getIdcardType() != null) {
            vo.setIdcardType(object.getIdcardType().getDesc());
        }
        vo.setAddresseeIdcard(object.getAddresseeIdcard());
        if (object.getDeliverTime() != null) {
            vo.setDeliverTime(object.getDeliverTime().getTime());
        }
        if (object.getDeliveredTime() != null) {
            vo.setDeliveredTime(object.getDeliveredTime().getTime());
        }
        vo.setLocaleContact(object.getLocaleContact());
        vo.setLocaleAddress(object.getLocaleAddress());
        vo.setExpressNo(object.getExpressNo());
        vo.setExpressCompany(object.getExpressCompany());
        vo.setProvinceName(object.getProvinceName());
        vo.setCityName(object.getCityName());
        vo.setDetailedAddress(object.getDetailedAddress());
        vo.setPostCode(object.getPostCode());
        if (object.getFetchedTime() != null) {
            vo.setFetchedTime(object.getFetchedTime().getTime());
        }
        return vo;
    }

    private List<OrderItemVO> buildOrderItems(List<OrderItemDTO> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return Collections.emptyList();
        }
        List<OrderItemVO> orderItemVoList = Lists.transform(orderItems, dto -> {
            OrderItemVO vo = new OrderItemVO();
            vo.setItemId(dto.getItemId());
            vo.setPriceId(dto.getPriceId());
            vo.setIsPackage(dto.getIsPackage());
            vo.setProductTitle(dto.getProductTitle());
            if (dto.getSkuType() != null) {
                vo.setSkuType(dto.getSkuType().getName());
            }
            vo.setQuantity(dto.getQuantity());
            if (StringUtils.isNotBlank(dto.getImageSrc())) {
                vo.setImageSrc(dto.getImageSrc());
            }
            if (StringUtils.isNotBlank(dto.getPriceDesc())) {
                vo.setPriceDesc(dto.getPriceDesc());
            }
            if (dto.getCostFee() != null) {
                vo.setCostFee(dto.getCostFee().getAmount());
            }
            if (dto.getUnitPrice() != null) {
                vo.setUnitPrice(dto.getUnitPrice().getAmount());
            }
            if (dto.getOriginPrice() != null) {
                vo.setOriginPrice(dto.getOriginPrice().getAmount());
            }
            if (dto.getDiscountFee() != null) {
                vo.setDiscountFee(dto.getDiscountFee().getAmount());
            }
            return vo;
        });
        return orderItemVoList;
    }

    private List<OrderTicketVO> buildOrderTickets(List<OrderTicketDTO> orderTickets) {
        if (CollectionUtils.isEmpty(orderTickets)) {
            return Collections.emptyList();
        }
        List<OrderTicketVO> orderTicketVoList = Lists.transform(orderTickets, dto -> {
            OrderTicketVO vo = new OrderTicketVO();
            vo.setTicketDesc(dto.getTicketDesc());
            vo.setOrderItemId(dto.getOrderItemId());
            vo.setOrderTicketId(dto.getOrderTicketId());
            vo.setPriceId(dto.getPriceId());
            vo.setSeatName(dto.getSeatName());
            vo.setZoneName(dto.getZoneName());
            if (dto.getTicketPrice() != null) {
                vo.setTicketPrice(dto.getTicketPrice().getAmount());
            }
            if (dto.getOriginPrice() != null) {
                vo.setOriginPrice(dto.getOriginPrice().getAmount());
            }
            if (StringUtils.isNotEmpty(dto.getQrCode())) {
                String str = FileUtil.generateQrCodeToBase64(dto.getQrCode(), "jpg", 240, 240);
                vo.setQrCodeBase64(str);
            }
            vo.setQrCode(dto.getQrCode());
            vo.setCheckCode(dto.getCheckCode());
            vo.setCheckStatus(dto.getCheckStatus());
            if (dto.getCheckTime() != null) {
                vo.setCheckTime(dto.getCheckTime().getTime());
            }
            return vo;
        });
        return orderTicketVoList;
    }

    private OrderTransactionSnapshotVO buildSnapshot(OrderTransactionSnapshotDTO snapshot) {
        if (snapshot == null) {
            return null;
        }
        OrderTransactionSnapshotVO vo = new OrderTransactionSnapshotVO();
        vo.setTheatreName(snapshot.getTheatreName());
        vo.setTheatreAddress(snapshot.getTheatreAddress());
        vo.setVenueId(snapshot.getVenueId());
        vo.setVenueName(snapshot.getVenueName());
        vo.setProjectId(snapshot.getProjectId());
        vo.setProjectName(snapshot.getProjectName());
        vo.setEventId(snapshot.getEventId());
        vo.setEventName(snapshot.getEventName());
        vo.setShowName(snapshot.getShowName());
        vo.setListPosterUrl(snapshot.getListPosterUrl());
        vo.setDetailPosterUrl(snapshot.getDetailPosterUrl());
        return vo;
    }
}
