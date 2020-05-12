package com.mtl.cypw.web.controller.show.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.domain.mpm.constant.CacheConstant;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.domain.show.dto.ProgramDTO;
import com.mtl.cypw.web.controller.show.vo.ProgramVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/20.
 */
@Component
public class ProgramConverter {

    public ProgramVO toVo(ProgramDTO dto) {
        if (dto == null) {
            return null;
        }
        ProgramVO vo = new ProgramVO();
        vo.setId(dto.getProgramId());
        vo.setType(dto.getProgramTypeId());
        vo.setName(dto.getProgramTitle());
        vo.setBrief(dto.getProgramTitleBrief());
        vo.setStatus(dto.getSaleStatus().toString());
        vo.setPosterUrl(dto.getListImage());
        vo.setVenueId(dto.getVenueId());

        vo.setFirstShowTime(dto.getSaleDateBegin() != null ? dto.getSaleDateBegin().getTime() : System.currentTimeMillis());
        vo.setLastShowTime(dto.getSaleDateEnd() != null ? dto.getSaleDateEnd().getTime() : System.currentTimeMillis());
        vo.setSupportSeatFlag(dto.getSupportSeatFlag() == 1 ? true : false);
        vo.setSupportETicket(dto.getSupportETicket() == 1 ? true : false);
        vo.setIsOfficial(dto.getIsOfficial() == 1 ? true : false);
//        vo.setDiscount("优惠描述");
        vo.setCurrentOnSaleMinPrice(dto.getProgramPrice());
        vo.setProgramTimeDescribe(dto.getProgramTime());
        vo.setRecommended(dto.getProgramBrief());
        vo.setLocationName(dto.getLocationName());
        vo.setLocationAddress(dto.getLocationAddress());
        vo.setServiceMobile(dto.getServiceMobile());
        vo.setTags(dto.getTags());
        if (dto.getFetchTicketWayList() != null) {
            FetchTicketWayConverter converter = new FetchTicketWayConverter();
            vo.setFetchTicketWayList(converter.toVo(dto.getFetchTicketWayList()));
        }
        return vo;
    }

    private List<ProgramVO> toVo(List<ProgramDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        List<ProgramVO> list = new ArrayList<>();
        dtoList.forEach(n -> list.add(toVo(n)));
        return list;
    }

    public TSingleResult<ProgramVO> toVo(TSingleResult<ProgramDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TPageResult<ProgramVO> toVo(TPageResult<ProgramDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }
}
