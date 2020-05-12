package com.mtl.cypw.web.controller.show.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.show.dto.ProgramDTO;
import com.mtl.cypw.web.controller.show.vo.ProgramDetailVO;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Component
public class ProgramDetailConverter {

    @Resource
    private ProgramConverter programConverter;

    private ProgramDetailVO toVo(ProgramDTO dto) {
        if (dto == null) {
            return null;
        }
        ProgramDetailVO vo = new ProgramDetailVO();
        BeanUtils.copyProperties(programConverter.toVo(dto), vo);
        vo.setContent(dto.getProgramContent());
        vo.setTicketInfo(dto.getProgramNotice());
        vo.setSponserTitle(dto.getSponserTitle());
        vo.setSponserIntroduce(dto.getSponserIntroduce());
        vo.setSupportSeatFlag(BooleanUtils.toBooleanObject(dto.getSupportSeatFlag()));
        return vo;
    }

    public TSingleResult<ProgramDetailVO> toVo(TSingleResult<ProgramDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }
}
