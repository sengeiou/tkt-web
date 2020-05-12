package com.mtl.cypw.web.controller.member.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.dto.CheckInUserDTO;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/2/12.
 */
@Component
public class CheckInUserConverter {

    private CheckInUserVO toVo(CheckInUserDTO dto) {
        if (dto == null) {
            return null;
        }
        CheckInUserVO vo = new CheckInUserVO();
        vo.setId(dto.getId());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setUserName(dto.getUserName());
        vo.setPersonName(dto.getPersonName());
        vo.setBeginDate(dto.getBeginDate());
        vo.setEndDate(dto.getEndDate());
        return vo;
    }

    private List<CheckInUserVO> toVo(List<CheckInUserDTO> list) {
        if (list == null) {
            return null;
        }
        List<CheckInUserVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<CheckInUserVO> toVo(TSingleResult<CheckInUserDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<CheckInUserVO> toVo(TMultiResult<CheckInUserDTO> dtoList) {
        return ResultBuilder.succTMulti(toVo(dtoList.getData()));
    }
}
