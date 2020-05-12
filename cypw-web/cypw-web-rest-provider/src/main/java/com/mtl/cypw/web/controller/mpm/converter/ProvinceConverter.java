package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.ProvinceDTO;
import com.mtl.cypw.web.controller.mpm.vo.ProvinceVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang
 * @date 2019-11-27.
 */
@Component
public class ProvinceConverter {

    private ProvinceVO toVo(ProvinceDTO dto) {
        if (dto == null) {
            return null;
        }
        ProvinceVO vo = new ProvinceVO();
        vo.setProvinceCode(dto.getProvinceCode());
        vo.setProvinceName(dto.getProvinceName());
        vo.setProvinceEname(dto.getProvinceEname());
        return vo;
    }

    private List<ProvinceVO> toVo(List<ProvinceDTO> list) {
        if (list == null) {
            return null;
        }
        List<ProvinceVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<ProvinceVO> toVo(TSingleResult<ProvinceDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<ProvinceVO> toVo(TMultiResult<ProvinceDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }
}
