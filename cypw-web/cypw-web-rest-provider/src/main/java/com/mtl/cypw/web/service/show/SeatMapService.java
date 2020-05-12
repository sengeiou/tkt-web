package com.mtl.cypw.web.service.show;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.show.client.SeatApiClient;
import com.mtl.cypw.domain.show.dto.SeatMapDTO;
import com.mtl.cypw.domain.show.query.SeatMapQuery;
import com.mtl.cypw.web.controller.show.converter.SeatMapConverter;
import com.mtl.cypw.web.controller.show.vo.SeatMapVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Slf4j
@Service
public class SeatMapService {

    @Autowired
    private SeatApiClient seatApiClient;

    @Autowired
    private SeatMapConverter seatMapConverter;

    public TSingleResult<SeatMapVO> getSeatMap(QueryRequest<SeatMapQuery> request) {
        log.debug("查询演出场次座位信息，request：{}", JSONObject.toJSONString(request));
        TSingleResult<SeatMapDTO> seatMap = seatApiClient.findSeatMap(request);
        if (!seatMap.success()) {
            return ResultBuilder.failTSingle(seatMap.getStatusCode(), seatMap.getComments());
        }
        return ResultBuilder.succTSingle(seatMapConverter.toVo(seatMap.getData()));
    }
}
