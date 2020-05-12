package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.TheatreDTO;
import com.mtl.cypw.web.controller.mpm.vo.TheatreVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Component
public class TheatreConverter {

    public TheatreVO toVo(TheatreDTO dto) {
        if (dto == null) {
            return null;
        }
        TheatreVO vo = new TheatreVO();
        vo.setTheatreId(dto.getTheatreId());
        vo.setTheatreName(dto.getTheatreName());
        vo.setTheatreAddress(dto.getTheatreAddress());
        vo.setTheatreContent(dto.getTheatreContent());
        vo.setCityCode(dto.getCityCode());
        vo.setTheatreImage(dto.getTheatreImage());
        vo.setLongitudeAndLatitude(dto.getLongitudeAndLatitude());
        vo.setTheatreName(dto.getTheatreName());
        return vo;
    }

    public List<TheatreVO> toVo(List<TheatreDTO> list) {
        if (list == null) {
            return null;
        }
        List<TheatreVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public TSingleResult<TheatreVO> toVo(TSingleResult<TheatreDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<TheatreVO> toVo(TMultiResult<TheatreDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }
}
