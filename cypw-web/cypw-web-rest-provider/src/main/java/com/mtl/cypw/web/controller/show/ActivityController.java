package com.mtl.cypw.web.controller.show;

import com.juqitech.request.PaginationParam;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.show.enums.TimeTypeEnum;
import com.mtl.cypw.web.controller.show.vo.ActivityVO;
import com.mtl.cypw.web.service.show.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/1/6.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-活动接口"})
@CrossOrigin
public class ActivityController {

    @Resource
    ActivityService activityService;

    @RequestMapping(value = "/pub/v1/activitys/page", method = RequestMethod.GET)
    @ApiOperation(value = "查询活动列表(分页查询)", httpMethod = "GET", response = ActivityVO.class, notes = "查询活动列表(分页查询)")
    public TPageResult<ActivityVO> searchActivityPage(@ApiParam(name = "timeTypeEnum", value = "时间类型") @RequestParam(required = false) TimeTypeEnum timeTypeEnum,
                                                      @ApiParam(required = true, name = "pageNo", value = "页码") @RequestParam int pageNo,
                                                      @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam int pageSize) {

        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setOffset(pageNo);
        paginationParam.setLength(pageSize);

        TPageResult<ActivityVO> result = activityService.searchActivityPage(timeTypeEnum, paginationParam);
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return result;
    }

    @RequestMapping(value = "/pub/v1/activitys", method = RequestMethod.GET)
    @ApiOperation(value = "查询活动列表", httpMethod = "GET", response = ActivityVO.class, notes = "查询活动列表")
    public TMultiResult<ActivityVO> searchActivity(@ApiParam(name = "timeTypeEnum", value = "时间类型") @RequestParam(required = false) TimeTypeEnum timeTypeEnum) {

        return activityService.searchActivity(timeTypeEnum);
    }

    @RequestMapping(value = "/pub/v1/get/activity", method = RequestMethod.GET)
    @ApiOperation(value = "查询活动", httpMethod = "GET", response = ActivityVO.class, notes = "查询活动")
    public TSingleResult<ActivityVO> getActivity(@ApiParam(required = true, name = "activityId", value = "活动ID") @RequestParam Integer activityId) {

        return activityService.getActivity(activityId);
    }
}
