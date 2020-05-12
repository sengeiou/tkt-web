package com.mtl.cypw.test;

import com.juqitech.request.QueryRequest;
import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.domain.show.query.SeatMapQuery;
import com.mtl.cypw.web.service.show.SeatMapService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Johnathon.Yuan
 * @date 2020-01-14 14:57
 */
@Slf4j
public class SeatTest extends BaseTest {

    @Autowired
    private SeatMapService seatMapService;
    @Test
    public void testSeatMap() {
        SeatMapQuery query = new SeatMapQuery();
        query.setEventId(43);
        QueryRequest<SeatMapQuery> request = new QueryRequest<>();
        request.setParam(query);
        log.info(JsonUtils.toJson(seatMapService.getSeatMap(request)));
    }
}
