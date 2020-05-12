package com.mtl.cypw.web.service.report;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.domain.coupon.enums.CouponStateEnum;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.coupon.vo.PromotionCouponVO;
import com.mtl.cypw.web.controller.order.enums.OrderStateEnum;
import com.mtl.cypw.web.controller.report.vo.MemberReportVO;
import com.mtl.cypw.web.service.coupon.PromotionCouponService;
import com.mtl.cypw.web.service.order.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/13.
 */
@Service
public class ReportService {

    @Resource
    PromotionCouponService promotionCouponService;

    @Resource
    OrderService orderService;

    /**
     * 多少天内过期
     */
    @Value("${soonExpireRemindDay}")
    private Integer soonExpireDay = 3;

    public TSingleResult<MemberReportVO> getMemberReportVO() {
        if (Operator.getMemberId() == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_AUTHORITY.getCode(), ErrorCode.ERROR_COMMON_AUTHORITY.getMsg());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (soonExpireDay != null) {
            calendar.add(Calendar.DATE, soonExpireDay);
        }
        MemberReportVO vo = new MemberReportVO();
        TMultiResult<PromotionCouponVO> couponListResult = promotionCouponService.searchMemberCouponList(null, CouponStateEnum.NOT_USED, null, calendar.getTime());
        TMultiResult<PromotionCouponVO> exchangeCouponListResult = promotionCouponService.searchExchangeCouponList(null, CouponStateEnum.NOT_USED, null);
        TSingleResult<Integer> waitPayOrderCountResult = orderService.countOrder(OrderStateEnum.WAIT_PAY);
        TSingleResult<Integer> waitAttendOrderCountResult = orderService.countOrder(OrderStateEnum.WAIT_ATTEND);
        List<PromotionCouponVO> couponList = couponListResult.getData();
        if (couponList != null) {
            vo.setCouponNum(couponList.size());
        }
        List<PromotionCouponVO> exchangeCouponList = exchangeCouponListResult.getData();
        if (exchangeCouponList != null) {
            vo.setExchangeCouponNum(exchangeCouponList.size());
        }
        vo.setNotPayOrderNum(waitPayOrderCountResult.getData());
        vo.setWaitAttendOrderNum(waitAttendOrderCountResult.getData());
        return ResultBuilder.succTSingle(vo);
    }
}
