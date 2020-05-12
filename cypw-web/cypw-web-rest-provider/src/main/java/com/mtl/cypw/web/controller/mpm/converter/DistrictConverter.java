package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.DistrictDTO;
import com.mtl.cypw.web.controller.mpm.vo.DistrictVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang
 * @date 2019-11-27.
 */
@Component
public class DistrictConverter {

    private DistrictVO toVo(DistrictDTO dto) {
        if (dto == null) {
            return null;
        }
        DistrictVO vo = new DistrictVO();
        vo.setDistrictCode(dto.getDistrictCode());
        vo.setCityCode(dto.getCityCode());
        vo.setDistrictBriefName(dto.getDistrictBriefName());
        vo.setDistrictName(dto.getDistrictName());
        return vo;
    }

    private List<DistrictVO> toVo(List<DistrictDTO> list) {
        if (list == null) {
            return null;
        }
        List<DistrictVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<DistrictVO> toVo(TSingleResult<DistrictDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<DistrictVO> toVo(TMultiResult<DistrictDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));

    }
}
