package com.mtl.cypw.web.controller.mpm;

import com.juqitech.request.PaginationParam;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.mpm.param.CreateBuryingPointParam;
import com.mtl.cypw.web.service.mpm.BuryingPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-埋点接口"})
@CrossOrigin
public class BuryingPointController {

    @Resource
    private BuryingPointService buryingPointService;

    @RequestMapping(value = "/buyer/v1/mpm/add/buryingPoint", method = RequestMethod.POST)
    @ApiOperation(value = "新增埋点信息", httpMethod = "POST", response = Boolean.class, notes = "新增埋点信息")
    public TSingleResult<Boolean> findBuryingPointList(@ApiParam(required = true, name = "param", value = "新增埋点信息") @RequestBody CreateBuryingPointParam param) {
        try {
            buryingPointService.addBuryingPointInfo(param);
        } catch (Exception e) {
            log.error("新增埋点信息出错,e:{}", e.getMessage());
        }
        return ResultBuilder.succTSingle(true);
    }

    @RequestMapping(value = "/buyer/v1/mpm/delete/buryingPoint", method = RequestMethod.POST)
    @ApiOperation(value = "删除埋点信息", httpMethod = "POST", response = Boolean.class, notes = "删除埋点信息")
    public TSingleResult<Boolean> deleteBuryingPointInfo(@ApiParam(required = true, name = "type", value = "删除埋点信息") @RequestBody String type) {
        try {
            buryingPointService.deleteBuryingPointInfo(type);
        } catch (Exception e) {
            log.error("删除埋点信息出错,e:{}", e.getMessage());
        }
        return ResultBuilder.succTSingle(true);
    }

    @RequestMapping(value = "/buyer/v1/mpm/search/logs/page", method = RequestMethod.GET)
    @ApiOperation(value = "会员搜索历史记录(分页)", httpMethod = "GET", response = List.class, notes = "会员搜索历史记录(分页)")
    public TPageResult<String> findMemberSearchLogList(@ApiParam(name = "createTimeBegin", value = "开始时间") @RequestParam(required = false) Date createTimeBegin,
                                                       @ApiParam(name = "createTimeEnd", value = "结束时间") @RequestParam(required = false) Date createTimeEnd,
                                                       @ApiParam(required = true, name = "pageNo", value = "页码") @RequestParam int pageNo,
                                                       @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam int pageSize) {
        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setOffset(pageNo);
        paginationParam.setLength(pageSize);
        TPageResult<String> result = buryingPointService.findSearchLogPage(createTimeBegin, createTimeEnd, paginationParam);
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return result;
    }
}
