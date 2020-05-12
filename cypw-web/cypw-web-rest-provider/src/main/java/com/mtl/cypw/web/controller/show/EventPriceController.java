package com.mtl.cypw.web.controller.show;

import com.juqitech.response.TPageResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.show.vo.EventPriceVO;
import com.mtl.cypw.web.service.show.EventPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@RestController
@Slf4j
@Api(value = "票星球-场次票价信息", tags = {"票星球-场次票价接口"})
@CrossOrigin
public class EventPriceController extends WebGenericController {

    @Resource
    EventPriceService eventPriceService;

    @RequestMapping(value = "/pub/v1/prices/{eventId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询场次票价列表", httpMethod = "GET", response = EventPriceVO.class, notes = "查询场次票价列表")
    public TPageResult<EventPriceVO> searchEventPriceList(@ApiParam(required = true, name = "eventId", value = "场次ID") @PathVariable("eventId") Integer eventId) {

        return eventPriceService.searchEventPriceList(eventId);
    }
}
