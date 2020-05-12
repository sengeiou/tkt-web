package com.mtl.cypw.web.controller.show.converter;

import com.mtl.cypw.domain.mpm.dto.TemplateDTO;
import com.mtl.cypw.web.controller.show.vo.TemplateVO;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-01-09 15:39
 */
@Component
public class TemplateConverter {

    public TemplateVO toDto(TemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        TemplateVO vo = new TemplateVO();
        vo.setTemplateId(dto.getTemplateId());
        vo.setTemplateName(dto.getTemplateName());
        vo.setMapTypeId(dto.getMapTypeId());
        vo.setOneSvg(dto.getOneSvg());
        vo.setTemplateMap(dto.getTemplateMap());
        return vo;
    }

}
