package com.mtl.cypw.web.controller.show.converter;

import com.mtl.cypw.domain.show.dto.ActorDTO;
import com.mtl.cypw.web.controller.show.vo.ActorVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Component
public class ActorConverter {

    public ActorVO toVo(ActorDTO dto) {
        if (dto == null) {
            return null;
        }
        ActorVO vo = new ActorVO();
        vo.setId(dto.getId());
        vo.setProgramId(dto.getProgramId());
        vo.setActorName(dto.getActorName());
        vo.setActorImage(dto.getActorImage());
        return vo;
    }

    public List<ActorVO> toVo(List<ActorDTO> list) {
        if (list == null) {
            return null;
        }
        List<ActorVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

}
