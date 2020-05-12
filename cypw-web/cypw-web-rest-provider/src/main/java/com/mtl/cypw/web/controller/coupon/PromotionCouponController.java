package com.mtl.cypw.web.controller.coupon;

import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.coupon.vo.PromotionCouponVO;
import com.mtl.cypw.web.service.coupon.PromotionCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/5.
 */
@RestController
@Slf4j
@Api(value = "票星球-优惠券", tags = {"票星球-优惠券接口"})
@CrossOrigin
public class PromotionCouponController extends WebGenericController {

    @Resource
    PromotionCouponService promotionCouponService;

    @RequestMapping(value = "/buyer/v1/promotion/coupon/create/{promotionId}", method = RequestMethod.GET)
    @ApiOperation(value = "领取优惠券", httpMethod = "GET", response = Boolean.class, notes = "领取优惠券")
    public TSingleResult<Boolean> addPromotionCoupon(@ApiParam(required = true, name = "promotionId", value = "优惠券ID") @PathVariable("promotionId") Integer promotionId) {

        return promotionCouponService.addPromotionCoupon(promotionId);
    }


    @RequestMapping(value = "/buyer/v1/promotion/coupon/exchange/{couponCode}", method = RequestMethod.GET)
    @ApiOperation(value = "获取兑换券", httpMethod = "GET", response = Boolean.class, notes = "获取兑换券")
    public TSingleResult<Boolean> addExchangeCoupon(@ApiParam(required = true, name = "couponCode", value = "兑换码") @PathVariable("couponCode") String couponCode) {

        return promotionCouponService.addExchangeCoupon(couponCode);
    }

    @RequestMapping(value = "/buyer/v1/promotion/coupon/member", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户已有优惠券列表", httpMethod = "GET", response = PromotionCouponVO.class, notes = "查询用户已有优惠券列表")
    public TMultiResult<PromotionCouponVO> searchMemberCouponList(
            @ApiParam(required = true, name = "businessType", value = "业务类型(0：全部，1:票品类，2：衍生品类)") @RequestParam(defaultValue = "1") Integer businessType,
            @ApiParam(required = true, name = "couponState", value = "优惠券使用状态") @RequestParam CouponStateEnum couponState,
            @ApiParam(name = "orderAmount", value = "订单金额") @RequestParam(required = false) Double orderAmount) {

        return promotionCouponService.searchMemberCouponList(businessType, couponState, orderAmount);
    }


    @RequestMapping(value = "/buyer/v1/promotion/exchange/coupon", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户兑换券列表", httpMethod = "GET", response = PromotionCouponVO.class, notes = "查询用户兑换券列表")
    public TMultiResult<PromotionCouponVO> searchExchangeCouponList(
            @ApiParam(required = true, name = "businessType", value = "业务类型(0：全部，1:票品类，2：衍生品类)") @RequestParam(defaultValue = "1") Integer businessType,
            @ApiParam(required = true, name = "couponState", value = "优惠券使用状态") @RequestParam CouponStateEnum couponState,
            @ApiParam(name = "eventId", value = "场次ID") @RequestParam(required = false) String eventId) {

        return promotionCouponService.searchExchangeCouponList(businessType, couponState, eventId);
    }
}
