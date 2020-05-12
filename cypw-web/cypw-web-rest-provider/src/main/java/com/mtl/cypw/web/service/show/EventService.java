package com.mtl.cypw.web.service.show;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.show.client.EventApiClient;
import com.mtl.cypw.domain.show.dto.EventDTO;
import com.mtl.cypw.domain.show.query.EventQuery;
import com.mtl.cypw.web.controller.show.converter.EventConverter;
import com.mtl.cypw.web.controller.show.vo.EventVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Slf4j
@Service
public class EventService {

    @Autowired
    EventApiClient eventApiClient;

    @Autowired
    EventConverter eventConverter;

    public TMultiResult<EventVO> searchEventList(QueryRequest<EventQuery> request) {
        log.debug("查询演出场次列表，request：{}", JSONObject.toJSONString(request));
        TMultiResult<EventDTO> dto = eventApiClient.searchEventList(request);
        log.debug("查询结果，response：{}", JSONObject.toJSONString(dto));
        return eventConverter.toVo(dto);
    }
}
