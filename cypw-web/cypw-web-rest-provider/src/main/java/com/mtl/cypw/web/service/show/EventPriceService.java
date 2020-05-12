package com.mtl.cypw.web.service.show;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TPageResult;
import com.mtl.cypw.api.show.client.EventPriceApiClient;
import com.mtl.cypw.domain.show.dto.EventPriceDTO;
import com.mtl.cypw.domain.show.query.EventQuery;
import com.mtl.cypw.web.controller.show.converter.EventPriceConverter;
import com.mtl.cypw.web.controller.show.vo.EventPriceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Slf4j
@Service
public class EventPriceService {

    @Autowired
    EventPriceApiClient eventPriceApiClient;

    @Autowired
    EventPriceConverter eventPriceConverter;

    public TPageResult<EventPriceVO> searchEventPriceList(Integer eventId) {
        log.debug("查询演出场次列表，eventId：{}", eventId);
        EventQuery query = new EventQuery();
        query.setEventId(eventId);
        query.setIsEnable(1);

        QueryRequest<EventQuery> request = QueryRequest.build();
        request.setParam(query);
        TPageResult<EventPriceDTO> pageDto = eventPriceApiClient.searchEventPriceList(request);
        log.debug("查询结果，response：{}", JSONObject.toJSONString(pageDto));
        return eventPriceConverter.toVo(pageDto);
    }
}
