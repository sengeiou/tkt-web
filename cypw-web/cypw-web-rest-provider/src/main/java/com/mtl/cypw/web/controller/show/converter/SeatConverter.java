package com.mtl.cypw.web.controller.show.converter;

import com.google.common.collect.Maps;
import com.mtl.cypw.domain.mpm.dto.ZoneDTO;
import com.mtl.cypw.domain.show.dto.EventPriceDTO;
import com.mtl.cypw.domain.show.dto.SeatDTO;
import com.mtl.cypw.domain.show.enums.SeatStatusEnum;
import com.mtl.cypw.domain.show.enums.SeatTypeEnum;
import com.mtl.cypw.web.controller.show.vo.SeatVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Johnathon.Yuan
 * @date 2020-01-10 19:56
 */
@Component
public class SeatConverter {

    public List<SeatVO> toDto(List<SeatDTO> seats, List<EventPriceDTO> prices, List<ZoneDTO> zones) {
        if (CollectionUtils.isEmpty(seats)) {
            return Collections.emptyList();
        }
        Map<Integer, ZoneDTO> zoneMap = Maps.newHashMap();
        Map<Integer, EventPriceDTO> priceMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(prices)) {
            priceMap = prices.stream().collect(Collectors.toMap(EventPriceDTO::getPriceId, e -> e));
        }
        if (CollectionUtils.isNotEmpty(zones)) {
            zoneMap = zones.stream().collect(Collectors.toMap(ZoneDTO::getZoneId, e -> e));
        }
        List<SeatVO> voList = new ArrayList<>();
        for (SeatDTO seat : seats) {
            SeatVO vo = new SeatVO();
            ZoneDTO zone = zoneMap.get(seat.getZoneId());
            vo.setSeatId(seat.getSeatId());
            vo.setEventId(seat.getEventId());
            vo.setZoneId(seat.getZoneId());
            vo.setZoneName(zone.getZoneName());
            vo.setPriceId(seat.getPriceId());
            if (seat.getPriceId() > 0) {
                vo.setPrice(priceMap.get(seat.getPriceId()).getPriceValue());
            } else {
                vo.setPrice(BigDecimal.ZERO);
            }
            vo.setBgColor(seat.getBgColor());
            vo.setRowInt(zone.getBeginY() + (seat.getRowInt() - 1) * (zone.getSeatHeight() + zone.getSeatMargin()));
            vo.setRowName(seat.getRowName());
            vo.setColInt(zone.getBeginX() + (seat.getColInt() - 1) * (zone.getSeatWidth() + zone.getSeatMargin()));
            vo.setColName(seat.getColName());
            vo.setHeight(zone.getSeatHeight());
            vo.setWidth(zone.getSeatWidth());
            vo.setOnlyPrefix(seat.getOnlyPrefix());
            if (SeatTypeEnum.supportedOffline().contains(seat.getSeatType())) {
                vo.setSeatStatus(SeatStatusEnum.UNSALEABLE.getCode());
            } else {
                vo.setSeatStatus(seat.getSeatStatus());
            }
            voList.add(vo);
        }
        return voList;
    }
}
