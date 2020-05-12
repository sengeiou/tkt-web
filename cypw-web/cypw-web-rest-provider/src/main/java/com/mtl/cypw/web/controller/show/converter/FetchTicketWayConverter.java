package com.mtl.cypw.web.controller.show.converter;

import com.mtl.cypw.domain.order.enums.DeliverTypeEnum;
import com.mtl.cypw.domain.show.dto.FetchTicketWayDTO;
import com.mtl.cypw.web.controller.show.vo.FetchTicketWayVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/3.
 */
@Component
public class FetchTicketWayConverter {
    public FetchTicketWayVO toVo(FetchTicketWayDTO dto) {
        if (dto == null) {
            return null;
        }
        FetchTicketWayVO vo = new FetchTicketWayVO();
        vo.setId(dto.getId());
        vo.setProgramId(dto.getProgramId());
        if (DeliverTypeEnum.getObject(dto.getDeliverType()) != null) {
            vo.setDeliverType(DeliverTypeEnum.getObject(dto.getDeliverType()).getName());
        }
        vo.setNeedIdcard(dto.getNeedIdcard());
        vo.setTips(dto.getTips());
        vo.setExpressFee(dto.getExpressFee());
        vo.setFetchAddress(dto.getFetchAddress());
        vo.setContactMobile(dto.getContactMobile());
        vo.setFetchTimeDesc(dto.getFetchTimeDesc());
        vo.setEnterpriseId(dto.getEnterpriseId());
        return vo;
    }

    public List<FetchTicketWayVO> toVo(List<FetchTicketWayDTO> list) {
        if (list == null) {
            return null;
        }
        List<FetchTicketWayVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }
}
