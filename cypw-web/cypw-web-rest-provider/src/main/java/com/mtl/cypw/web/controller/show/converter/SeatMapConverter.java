package com.mtl.cypw.web.controller.show.converter;

import com.mtl.cypw.domain.show.dto.SeatMapDTO;
import com.mtl.cypw.web.controller.show.vo.SeatMapVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-01-10 19:09
 */
@Component
public class SeatMapConverter {

    @Autowired
    private EventConverter eventConverter;

    @Autowired
    private TemplateConverter templateConverter;

    @Autowired
    private EventPriceConverter eventPriceConverter;

    public SeatMapVO toVo(SeatMapDTO dto) {
        if (dto == null) {
            return null;
        }
        SeatMapVO vo = new SeatMapVO();
        vo.setEvent(eventConverter.toVo(dto.getEvent()));
        vo.setTemplate(templateConverter.toDto(dto.getTemplate()));
        vo.setPrices(eventPriceConverter.toVo4Seat(dto.getPrices(), dto.getSeats(), dto.getZones()));
        //vo.setSeats(seatConverter.toDto(dto.getSeats(), dto.getPrices(), dto.getZones()));
        return vo;
    }
}
