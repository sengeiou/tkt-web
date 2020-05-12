package com.mtl.cypw.web.controller.coupon.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.domain.coupon.dto.PromotionDTO;
import com.mtl.cypw.domain.coupon.enums.PromotionBusinessTypeEnum;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.show.enums.ProgramTypeEnum;
import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@Component
public class PromotionConverter {

    public PromotionVO toVo(PromotionDTO dto) {
        if (dto == null) {
            return null;
        }
        PromotionVO vo = new PromotionVO();
        vo.setPromotionId(dto.getPromotionId());
        if (dto.getBusinessTypeId() != null) {
            vo.setBusinessType(PromotionBusinessTypeEnum.getObject(dto.getBusinessTypeId()));
        }
        if (dto.getPromotionTypeId() != null) {
            vo.setPromotionType(PromotionTypeEnum.getObject(dto.getPromotionTypeId()));
        }
        vo.setPromotionName(dto.getPromotionName());
        vo.setProgramPointId(dto.getProgramPointId());
        if (StringUtils.isNotEmpty(dto.getProgramTypeIds())) {
            List<String> typeIds = Arrays.asList(dto.getProgramTypeIds().split(","));
            List<ProgramTypeEnum> programTypeEnums = new ArrayList<>();
            typeIds.forEach(n -> {
                programTypeEnums.add(ProgramTypeEnum.getObject(Integer.parseInt(n)));
            });
            vo.setProgramTypes(programTypeEnums);
        }
        vo.setPromotionDiscount(dto.getPromotionDiscount());
        vo.setPromotionAmount(dto.getPromotionAmount());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setPromotionCode(dto.getPromotionCode());
        vo.setPromotionBeginDate(dto.getPromotionBeginDate());
        vo.setPromotionEndDate(dto.getPromotionEndDate());
        vo.setOrderAmountRestriction(dto.getOrderAmountRestriction());
        vo.setMinQtyRestriction(dto.getMinQtyRestriction());
        vo.setMaxQtyRestriction(dto.getMaxQtyRestriction());
        vo.setPaymentRestriction(dto.getPaymentRestriction());
        vo.setAvailableDay(dto.getAvailableDay());
        vo.setAvailableBeginDate(dto.getAvailableBeginDate());
        vo.setAvailableEndDate(dto.getAvailableEndDate());
        return vo;
    }

    public List<PromotionVO> toVo(List<PromotionDTO> list) {
        if (list == null) {
            return null;
        }
        List<PromotionVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TMultiResult<PromotionVO> toVo(TMultiResult<PromotionDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }
}
