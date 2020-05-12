package com.mtl.cypw.web.controller.member.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.dto.MemberAddressDTO;
import com.mtl.cypw.web.controller.member.vo.MemberAddressVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Component
public class MemberAddressConverter {
    private MemberAddressVO toVo(MemberAddressDTO dto) {
        if (dto == null) {
            return null;
        }
        MemberAddressVO vo = new MemberAddressVO();
        vo.setAddressId(dto.getAddressId());
        vo.setMemberId(dto.getMemberId());
        vo.setDeliveryName(dto.getDeliveryName());
        vo.setDeliveryMobile(dto.getDeliveryMobile());
        vo.setProvinceCode(dto.getProvinceCode());
        vo.setProvinceName(dto.getProvinceName());
        vo.setCityCode(dto.getCityCode());
        vo.setCityName(dto.getCityName());
        vo.setDistrictCode(dto.getDistrictCode());
        vo.setDistrictName(dto.getDistrictName());
        vo.setDeliveryAddress(dto.getDeliveryAddress());
        vo.setAddDate(dto.getAddDate());
        vo.setUpdateDate(dto.getUpdateDate());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setIsDefault(dto.getIsDefault());
        return vo;
    }

    private List<MemberAddressVO> toVo(List<MemberAddressDTO> list) {
        if (list == null) {
            return null;
        }
        List<MemberAddressVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<MemberAddressVO> toVo(TSingleResult<MemberAddressDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<MemberAddressVO> toVo(TMultiResult<MemberAddressDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }
}
