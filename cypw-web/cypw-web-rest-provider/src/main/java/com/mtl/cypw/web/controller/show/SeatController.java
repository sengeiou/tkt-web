package com.mtl.cypw.web.controller.show;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.show.query.SeatMapQuery;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.show.vo.SeatMapVO;
import com.mtl.cypw.web.service.show.SeatMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@RestController
@Slf4j
@Api(value = "票星球-场次座位信息", tags = {"票星球-场次座位接口"})
@CrossOrigin
public class SeatController extends WebGenericController {

    @Autowired
    private SeatMapService seatMapService;

    @RequestMapping(value = "/pub/v1/seat/maps", method = RequestMethod.GET)
    @ApiOperation(value = "查询场次座位信息", httpMethod = "GET", response = SeatMapVO.class, notes = "查询场次座位信息")
    public TSingleResult<SeatMapVO> seatMap(
            @ApiParam(required = true, name = "eventId", value = "场次ID") @RequestParam Integer eventId,
            @ApiParam(required = false, name = "priceId", value = "票价ID") @RequestParam(required = false) Integer priceId,
            @ApiParam(required = false, name = "zoneId", value = "区域ID") @RequestParam(required = false) Integer zoneId) {
        SeatMapQuery query = new SeatMapQuery();
        query.setEventId(eventId);
        query.setPriceId(priceId);
        query.setZoneId(zoneId);
        QueryRequest<SeatMapQuery> request = new QueryRequest<>();
        request.setParam(query);
        return seatMapService.getSeatMap(request);
    }
}
