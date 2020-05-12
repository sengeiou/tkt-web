package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.CityDTO;
import com.mtl.cypw.web.controller.mpm.vo.CityVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang
 * @date 2019-11-27.
 */
@Component
public class CityConverter {


    private CityVO toVo(CityDTO dto) {
        if (dto == null) {
            return null;
        }
        CityVO vo = new CityVO();
        vo.setCityCode(dto.getCityCode());
        vo.setProvinceCode(dto.getProvinceCode());
        vo.setCityEname(dto.getCityEname());
        vo.setCityName(dto.getCityName());
        return vo;
    }

    private List<CityVO> toVo(List<CityDTO> list) {
        if (list == null) {
            return null;
        }
        List<CityVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<CityVO> toVo(TSingleResult<CityDTO> dto) {
        TSingleResult<CityVO> vo = new TSingleResult<>();
        vo.setData(toVo(dto.getData()));
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<CityVO> toVo(TMultiResult<CityDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }
}
