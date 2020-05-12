package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.EnterpriseProgramTypeDTO;
import com.mtl.cypw.web.controller.mpm.vo.ProgramTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/31.
 */
@Component
public class ProgramTypeConverter {

    public ProgramTypeVO toVo(EnterpriseProgramTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        ProgramTypeVO vo = new ProgramTypeVO();
        vo.setProgramTypeId(dto.getProgramTypeId());
        vo.setProgramTypeTitle(dto.getProgramTypeTitle());
        vo.setSortOrder(dto.getSortOrder());
        return vo;
    }

    public List<ProgramTypeVO> toVo(List<EnterpriseProgramTypeDTO> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        List<ProgramTypeVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<ProgramTypeVO> toVo(TSingleResult<EnterpriseProgramTypeDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<ProgramTypeVO> toVo(TMultiResult<EnterpriseProgramTypeDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }
}
