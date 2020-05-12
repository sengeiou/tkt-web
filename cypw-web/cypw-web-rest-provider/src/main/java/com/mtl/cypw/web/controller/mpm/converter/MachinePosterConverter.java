package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.MachinePosterDTO;
import com.mtl.cypw.web.controller.mpm.vo.MachinePosterVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/16.
 */
@Component
public class MachinePosterConverter {

    public MachinePosterVO toVo(MachinePosterDTO pojo) {
        if (pojo == null) {
            return null;
        }
        MachinePosterVO vo = new MachinePosterVO();
        vo.setPosterId(pojo.getPosterId());
        vo.setPosterName(pojo.getPosterName());
        vo.setPosterImage(pojo.getPosterImage());
        vo.setSortOrder(pojo.getSortOrder());
        return vo;
    }

    public List<MachinePosterVO> toVo(List<MachinePosterDTO> list) {
        if (list == null) {
            return null;
        }
        List<MachinePosterVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public TSingleResult<MachinePosterVO> toVo(TSingleResult<MachinePosterDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<MachinePosterVO> toVo(TMultiResult<MachinePosterDTO> dtoList) {
        return ResultBuilder.succTMulti(toVo(dtoList.getData()));
    }
}
