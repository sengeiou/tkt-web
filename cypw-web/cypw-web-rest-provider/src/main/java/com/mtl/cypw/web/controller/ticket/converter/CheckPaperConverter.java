package com.mtl.cypw.web.controller.ticket.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.ticket.dto.TicketPaperDTO;
import com.mtl.cypw.web.controller.ticket.vo.TicketPaperVO;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-02-17 17:46
 */
@Component
public class CheckPaperConverter extends DataConverter<TicketPaperDTO, TicketPaperVO> {

    @Override
    public TicketPaperVO convert(TicketPaperDTO input) {
        if (input == null) {
            return null;
        }
        TicketPaperVO vo = new TicketPaperVO();
        vo.setPassed(input.getPassed());
        vo.setPassMessage(input.getPassMessage());
        vo.setTicketId(input.getTicketId());
        vo.setProgramName(input.getProgramName());
        vo.setEventName(input.getEventName());
        vo.setIdCard(input.getIdCard());
        vo.setIdCardName(input.getIdCardName());
        vo.setBindingIdCard(input.getBindingIdCard());
        vo.setBindingIdCardName(input.getBindingIdCardName());
        vo.setCheckTime(input.getCheckTime());
        vo.setCheckCode(input.getCheckCode());
        vo.setCheckUserName(input.getCheckUserName());
        vo.setCheckEntry(input.getCheckEntry());
        vo.setTicketPrice(input.getTicketPrice());
        vo.setVenueName(input.getVenueName());
        vo.setZoneName(input.getZoneName());
        vo.setSeatName(input.getSeatName());
        vo.setMobileNo(input.getMobileNo());
        vo.setCustomerName(input.getCustomerName());
        return vo;
    }

}
