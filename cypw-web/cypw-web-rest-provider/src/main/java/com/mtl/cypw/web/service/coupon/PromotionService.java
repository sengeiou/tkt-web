package com.mtl.cypw.web.service.coupon;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.coupon.client.PromotionApiClient;
import com.mtl.cypw.api.coupon.client.PromotionCouponApiClient;
import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.coupon.param.PromotionCouponQueryParam;
import com.mtl.cypw.domain.coupon.param.PromotionQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.coupon.converter.PromotionConverter;
import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Service
@Slf4j
public class PromotionService {

    @Resource
    PromotionApiClient client;

    @Resource
    PromotionCouponApiClient promotionCouponApiClient;

    @Resource
    PromotionConverter converter;

    public TMultiResult<PromotionVO> searchPromotionList(Integer enterpriseId, Integer businessType) {
        PromotionQueryParam query = new PromotionQueryParam();
        if(Operator.getMemberId() != null){
            List<Integer> ids = getPromotionIdList(CouponStateEnum.ALREADY_USED);
            query.setNotPromotionIds(ids);
        }
        query.setEnterpriseId(enterpriseId);
        List<Integer> promotionTypeList = new ArrayList<>();
        promotionTypeList.add(PromotionTypeEnum.DISCOUNT_COUPON.getCode());
        promotionTypeList.add(PromotionTypeEnum.CASH_COUPON.getCode());
        query.setPromotionTypeList(promotionTypeList);
        query.setDistributionType(0);
        query.setIsReceiveTime(1);
        if (businessType != null && businessType > 0) {
            query.setBusinessTypeId(businessType);
        }
        QueryRequest<PromotionQueryParam> request = QueryRequest.build();
        request.setParam(query);
        TMultiResult<PromotionVO> result = converter.toVo(client.searchPromotionList(request));
        List<PromotionVO> vos = result.getData();
        if (vos != null && vos.size() != 0 && Operator.getMemberId() != null) {
            List<Integer> ids = getPromotionIdList(null);
            if (ids != null && ids.size() != 0) {
                vos.forEach(n -> {
                    if (ids.contains(n.getPromotionId())) {
                        n.setIsReceive(1);
                    }
                });
            }
        }
        return result;
    }

    private List<Integer> getPromotionIdList(CouponStateEnum couponState) {
        //查询用户已有优惠券
        GenericRequest<PromotionCouponQueryParam> promotionCouponRequest = new GenericRequest<>();
        PromotionCouponQueryParam param = new PromotionCouponQueryParam();
        param.setMemberId(Operator.getMemberId());
        if (CouponStateEnum.ALREADY_USED.equals(couponState)) {
            param.setIsUsed(CouponStateEnum.ALREADY_USED.getCode());
        }
        if (CouponStateEnum.NOT_USED.equals(couponState) || CouponStateEnum.EXPIRED.equals(couponState)) {
            param.setIsUsed(CouponStateEnum.NOT_USED.getCode());
        }
        promotionCouponRequest.setParam(param);
        TMultiResult<Integer> promotionIdList = promotionCouponApiClient.searchPromotionIdListByMemberId(promotionCouponRequest);
        return promotionIdList.getData();
    }
}
