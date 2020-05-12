package com.mtl.cypw.web.service.coupon;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdListRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.coupon.client.PromotionApiClient;
import com.mtl.cypw.api.coupon.client.PromotionCouponApiClient;
import com.mtl.cypw.api.coupon.client.PromotionPriceApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.domain.coupon.dto.PromotionCouponDTO;
import com.mtl.cypw.domain.coupon.dto.PromotionDTO;
import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.coupon.param.MemberCouponParam;
import com.mtl.cypw.domain.coupon.param.PromotionCouponQueryParam;
import com.mtl.cypw.domain.coupon.param.PromotionQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.coupon.converter.PromotionConverter;
import com.mtl.cypw.web.controller.coupon.converter.PromotionCouponConverter;
import com.mtl.cypw.web.controller.coupon.vo.PromotionCouponVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Service
@Slf4j
public class PromotionCouponService {

    @Resource
    PromotionCouponApiClient client;

    @Resource
    PromotionApiClient promotionApiClient;

    @Resource
    PromotionPriceApiClient promotionPriceApiClient;

    @Resource
    PromotionCouponConverter converter;

    @Resource
    PromotionConverter promotionConverter;

    /**
     * 用户领取优惠券
     *
     * @param promotionId 优惠券id
     * @return
     */
    public TSingleResult<Boolean> addPromotionCoupon(Integer promotionId) {
        MemberCouponParam param = new MemberCouponParam();
        param.setMemberId(Operator.getMemberId());
        param.setPromotionId(promotionId);
        GenericRequest<MemberCouponParam> request = new GenericRequest();
        request.setParam(param);
        return client.addPromotionCoupon(request);
    }

    /**
     * 用户领取兑换券
     *
     * @param couponCode 兑换码
     * @return
     */
    public TSingleResult<Boolean> addExchangeCoupon(String couponCode) {
        MemberCouponParam param = new MemberCouponParam();
        param.setMemberId(Operator.getMemberId());
        param.setCouponCode(couponCode);
        GenericRequest<MemberCouponParam> request = new GenericRequest();
        request.setParam(param);
        return client.addExchangeCoupon(request);
    }


    public TMultiResult<PromotionCouponVO> searchMemberCouponList(Integer businessType, CouponStateEnum couponState, Double orderAmountRestriction) {
        return searchMemberCouponList(businessType, couponState, orderAmountRestriction, null);
    }

    public TMultiResult<PromotionCouponVO> searchMemberCouponList(Integer businessType, CouponStateEnum couponState, Double orderAmountRestriction, Date endEndDate) {
        if (Operator.getMemberId() == null) {
            return ResultBuilder.failTMulti(ErrorCode.ERROR_COMMON_AUTHORITY.getCode(), ErrorCode.ERROR_COMMON_AUTHORITY.getMsg());
        }
        List<Integer> promotionTypeList = new ArrayList<>();
        promotionTypeList.add(PromotionTypeEnum.CASH_COUPON.getCode());
        promotionTypeList.add(PromotionTypeEnum.DISCOUNT_COUPON.getCode());
        List<PromotionDTO> promotionList = getPromotionList(businessType, promotionTypeList, orderAmountRestriction, null);
        if (promotionList == null || promotionList.size() == 0) {
            return ResultBuilder.succTMulti(Collections.emptyList());
        }
        List<Integer> promotionIds = new ArrayList<>();
        Map<Integer, PromotionDTO> promotionDtoMap = promotionList.stream().collect(Collectors.toMap(PromotionDTO::getPromotionId, e -> e));
        promotionList.forEach(n -> promotionIds.add(n.getPromotionId()));
        List<PromotionCouponDTO> dtos = getPromotionCouponList(couponState, promotionIds, endEndDate);
        List<PromotionCouponVO> vos = converter.toVo(dtos);
        if (vos != null && vos.size() > 0) {
            vos.forEach(n -> {
                n.setPromotionVO(promotionConverter.toVo(promotionDtoMap.get(n.getPromotionId())));
                n.setCouponState(couponState);
            });
        }
        return ResultBuilder.succTMulti(vos);
    }

    public TMultiResult<PromotionCouponVO> searchExchangeCouponList(Integer businessType, CouponStateEnum couponState, String eventId) {
        if (Operator.getMemberId() == null) {
            return ResultBuilder.failTMulti(ErrorCode.ERROR_COMMON_AUTHORITY.getCode(), ErrorCode.ERROR_COMMON_AUTHORITY.getMsg());
        }
        List<Integer> promotionTypeList = new ArrayList<>();
        promotionTypeList.add(PromotionTypeEnum.EXCHANGE_COUPON.getCode());

        List<String> eventIds = Lists.newArrayList();
        if (StringUtils.isNotBlank(eventId)) {
            eventIds = Splitter.on(",").splitToList(eventId);
        }
        List<PromotionDTO> promotionList = getPromotionList(businessType, promotionTypeList, null, eventIds);
        if (CollectionUtils.isEmpty(promotionList)) {
            return ResultBuilder.succTMulti(Collections.emptyList());
        }
        List<Integer> promotionIds = new ArrayList<>();
        Map<Integer, PromotionDTO> promotionDtoMap = promotionList.stream().collect(Collectors.toMap(PromotionDTO::getPromotionId, e -> e));
        promotionList.forEach(n -> promotionIds.add(n.getPromotionId()));
        List<PromotionCouponDTO> dtos = getPromotionCouponList(couponState, promotionIds);
        List<PromotionCouponVO> vos = converter.toVo(dtos);
        if (vos != null && vos.size() > 0) {
            vos.forEach(n -> {
                n.setPromotionVO(promotionConverter.toVo(promotionDtoMap.get(n.getPromotionId())));
                n.setCouponState(couponState);
            });
        }
        return ResultBuilder.succTMulti(vos);
    }

    private List<PromotionDTO> getPromotionList(Integer businessType, List<Integer> promotionTypeList, Double orderAmountRestriction, List<String> eventIds) {
        PromotionQueryParam query = new PromotionQueryParam();
        if (businessType != null && businessType > 0) {
            query.setBusinessTypeId(businessType);
        }
        query.setPromotionTypeList(promotionTypeList);
        query.setOrderAmountRestriction(orderAmountRestriction);
        if (orderAmountRestriction != null || CollectionUtils.isNotEmpty(eventIds)) {
            query.setIsAvailableTime(1);
        }
        if (CollectionUtils.isNotEmpty(eventIds)) {
            //查询场次可用优惠券
            IdListRequest promotionPriceIdRequest = new IdListRequest(eventIds);
            TMultiResult<Integer> promotionIdList2 = promotionPriceApiClient.searchPromotionIdsByEventId(promotionPriceIdRequest);
            List<Integer> ids = promotionIdList2.getData();
            if (ids == null || ids.size() == 0) {
                //不查指定演出类型优惠券
                query.setNotPromotionPointTypes(Arrays.asList(2));
            }
            query.setProgramPromotionIds(ids);
        }
        QueryRequest<PromotionQueryParam> request = QueryRequest.build();
        request.setParam(query);
        TMultiResult<PromotionDTO> result = promotionApiClient.searchPromotionList(request);
        return result.getData();

    }

    private List<PromotionCouponDTO> getPromotionCouponList(CouponStateEnum couponState, List<Integer> promotionIds) {
        return getPromotionCouponList(couponState, promotionIds, null);
    }

    private List<PromotionCouponDTO> getPromotionCouponList(CouponStateEnum couponState, List<Integer> promotionIds, Date endEndDate) {
        //查询用户已有优惠券
        GenericRequest<PromotionCouponQueryParam> promotionCouponRequest = new GenericRequest<>();
        PromotionCouponQueryParam query = new PromotionCouponQueryParam();
        query.setMemberId(Operator.getMemberId());
        query.setCouponStateEnum(couponState);
        query.setEndEndDate(endEndDate);
        if (promotionIds != null && promotionIds.size() > 0) {
            query.setPromotionIds(promotionIds);
        }
        promotionCouponRequest.setParam(query);
        TMultiResult<PromotionCouponDTO> promotionList = client.searchPromotionCouponList(promotionCouponRequest);
        return promotionList.getData();
    }
}
