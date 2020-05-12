package com.mtl.cypw.web.controller.show;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.common.enums.CommonStateEnum;
import com.mtl.cypw.domain.show.query.EventQuery;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.show.vo.EventVO;
import com.mtl.cypw.web.service.show.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@RestController
@Slf4j
@Api(value = "票星球-演出场次信息", tags = {"票星球-演出场次接口"})
@CrossOrigin
public class EventController extends WebGenericController {

    @Resource
    EventService eventService;

    @RequestMapping(value = "/pub/v1/events/{projectId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询演出场次列表", httpMethod = "GET", response = EventVO.class, notes = "查询演出场次列表")
    public TMultiResult<EventVO> searchEventList(@ApiParam(required = true, name = "projectId", value = "演出ID") @PathVariable("projectId") Integer projectId) {
        EventQuery query = new EventQuery();
        query.setProgramId(projectId);
        query.setIsEnable(CommonStateEnum.VALID.getCode());
        query.setLessSaleDateBegin(new Date());
        query.setGreaterSaleDateEnd(new Date());

        QueryRequest<EventQuery> request = QueryRequest.build();
        request.setParam(query);
        return eventService.searchEventList(request);
    }
}
