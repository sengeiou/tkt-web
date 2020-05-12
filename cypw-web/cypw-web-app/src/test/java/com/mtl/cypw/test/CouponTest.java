package com.mtl.cypw.test;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.coupon.vo.PromotionCouponVO;
import com.mtl.cypw.web.controller.report.vo.MemberReportVO;
import com.mtl.cypw.web.service.coupon.PromotionCouponService;
import com.mtl.cypw.web.service.coupon.PromotionService;
import com.mtl.cypw.web.service.report.ReportService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tang.
 * @date 2019/12/4.
 */
public class CouponTest extends BaseTest {

    @Resource
    PromotionService promotionService;

    @Resource
    PromotionCouponService promotionCouponService;


    @Resource
    ReportService reportService;


    @Test
    public void getPromotion() {
        Operator.setMemberId(80001032);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 33);
        TMultiResult<PromotionCouponVO> promotions = promotionCouponService.searchMemberCouponList(null, CouponStateEnum.NOT_USED, 80.1, calendar.getTime());
        System.out.println(">>>>>>size=" + promotions.getData().size());
        System.out.println(">>>>>>" + JSONObject.toJSONString(promotions.getData()));
        Operator.remove();
    }

    @Test
    public void addPromotionCoupon() {
        Operator.setMemberId(80001032);
        promotionCouponService.addPromotionCoupon(13);
    }

    @Test
    public void getMemberReport() {
        Operator.setMemberId(80001032);
        TSingleResult<MemberReportVO> vo = reportService.getMemberReportVO();
        System.out.println(">>>>>" + JSONObject.toJSONString(vo));
    }
}
