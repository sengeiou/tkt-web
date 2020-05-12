package com.mtl.cypw.web.controller.show.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.ZoneDTO;
import com.mtl.cypw.domain.show.dto.EventPriceDTO;
import com.mtl.cypw.domain.show.dto.SeatDTO;
import com.mtl.cypw.web.controller.show.vo.EventPriceVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tang.
 * @date 2019/11/20.
 */
@Component
public class EventPriceConverter {

    @Autowired
    private SeatConverter seatConverter;

    public EventPriceVO toVo(EventPriceDTO dto) {
        if (dto == null) {
            return null;
        }
        EventPriceVO vo = new EventPriceVO();
        vo.setPriceId(dto.getPriceId());
        vo.setEventId(dto.getEventId());
        vo.setPriceOrigin(dto.getPriceOrigin());
        vo.setPriceValue(dto.getPriceValue());
        vo.setPriceClass(dto.getPriceClass());
        if (StringUtils.isEmpty(dto.getPriceTitle())) {
            vo.setPriceTitle(dto.getPriceValue() + "");
        } else {
            vo.setPriceTitle(dto.getPriceTitle());
        }
        vo.setStockQty(dto.getStockQty());
        vo.setIsEnable(dto.getIsEnable());
        vo.setMinQty(dto.getMinQty());
        vo.setPriceColor(dto.getPriceColor());
        return vo;
    }

    public List<EventPriceVO> toVo(List<EventPriceDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        List<EventPriceVO> list = new ArrayList<>();
        dtoList.forEach(n -> list.add(toVo(n)));
        return list;
    }

    public TSingleResult<EventPriceVO> toVo(TSingleResult<EventPriceDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TPageResult<EventPriceVO> toVo(TPageResult<EventPriceDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }


    public List<EventPriceVO> toVo4Seat(List<EventPriceDTO> priceDTOList, List<SeatDTO> seatDTOList, List<ZoneDTO> zoneDTOList) {
        if (CollectionUtils.isEmpty(priceDTOList)) {
            return Collections.emptyList();
        }
        Map<Integer, List<SeatDTO>> seatGroupMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(seatDTOList)) {
            seatGroupMap = seatDTOList.stream().collect(Collectors.groupingBy(SeatDTO::getPriceId));
        }
        List<EventPriceVO> list = Lists.newArrayList();
        for (EventPriceDTO priceDTO : priceDTOList) {
            EventPriceVO vo = toVo(priceDTO);
            if (vo == null) {
                continue;
            }
            List<SeatDTO> seats = seatGroupMap.get(priceDTO.getPriceId());
            if (CollectionUtils.isNotEmpty(seats)) {
                vo.setSeats(seatConverter.toDto(seats, priceDTOList, zoneDTOList));
            }
            list.add(vo);
        }
        return list;
    }
}
