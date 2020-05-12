package com.mtl.cypw.web.controller.coupon.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.domain.coupon.dto.PromotionCouponDTO;
import com.mtl.cypw.web.controller.coupon.vo.PromotionCouponVO;
import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Component
public class PromotionCouponConverter {

    private PromotionCouponVO toVo(PromotionCouponDTO dto) {
        if (dto == null) {
            return null;
        }
        PromotionCouponVO vo = new PromotionCouponVO();
        vo.setCouponId(dto.getCouponId());
        vo.setPromotionId(dto.getPromotionId());
        vo.setCouponCode(dto.getCouponCode());
        vo.setIsUsed(dto.getIsUsed());
        vo.setOrderNo(dto.getOrderNo());
        vo.setUseDate(dto.getUseDate());
        vo.setIsBinded(dto.getIsBinded());
        vo.setMemberId(dto.getMemberId());
        vo.setBindDate(dto.getBindDate());
        vo.setIsEnable(dto.getIsEnable());
        vo.setBeginDate(dto.getBeginDate());
        vo.setEndDate(dto.getEndDate());
        return vo;
    }

    public List<PromotionCouponVO> toVo(List<PromotionCouponDTO> list) {
        if (list == null) {
            return null;
        }
        List<PromotionCouponVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TMultiResult<PromotionCouponVO> toVo(TMultiResult<PromotionCouponDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }
}
