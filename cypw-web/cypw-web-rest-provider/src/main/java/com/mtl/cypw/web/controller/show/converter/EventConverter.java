package com.mtl.cypw.web.controller.show.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.show.dto.EventDTO;
import com.mtl.cypw.web.controller.show.vo.EventVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/20.
 */
@Component
public class EventConverter {

    public EventVO toVo(EventDTO dto) {
        if (dto == null) {
            return null;
        }
        EventVO vo = new EventVO();
        vo.setEventId(dto.getEventId());
        vo.setProgramId(dto.getProgramId());
        vo.setEventTitle(dto.getEventTitle());
        vo.setEventDate(dto.getEventDate() != null ? dto.getEventDate().getTime() : System.currentTimeMillis());

        return vo;
    }

    public List<EventVO> toVo(List<EventDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        List<EventVO> list = new ArrayList<>();
        dtoList.forEach(n -> list.add(toVo(n)));
        return list;
    }

    public TSingleResult<EventVO> toVo(TSingleResult<EventDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<EventVO> toVo(TMultiResult<EventDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }

    public TPageResult<EventVO> toVo(TPageResult<EventDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }
}
