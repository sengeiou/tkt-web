package com.mtl.cypw.web.controller.show;

import com.juqitech.request.PaginationParam;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.show.vo.ProgramDetailVO;
import com.mtl.cypw.web.controller.show.vo.ProgramVO;
import com.mtl.cypw.web.service.show.ProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/19.
 */
@RestController
@Slf4j
@Api(value = "票星球-演出项目信息", tags = {"票星球-演出接口"})
@CrossOrigin
public class ProgramController extends WebGenericController {

    @Resource
    ProgramService programService;

    @RequestMapping(value = "/pub/v1/projects", method = RequestMethod.GET)
    @ApiOperation(value = "查询演出列表", httpMethod = "GET", response = ProgramVO.class, notes = "查询演出列表")
    public TPageResult<ProgramVO> searchProgramList(@ApiParam(name = "likeName", value = "模糊搜索") @RequestParam(required = false) String likeName,
                                                    @ApiParam(name = "programTypes", value = "演出类型") @RequestParam(required = false) List<Integer> programTypes,
                                                    @ApiParam(required = true, name = "pageNo", value = "页码") @RequestParam int pageNo,
                                                    @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam int pageSize) {

        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setOffset(pageNo);
        paginationParam.setLength(pageSize);

        TPageResult<ProgramVO> result = programService.searchProgramList(paginationParam, likeName, programTypes, null);
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return result;
    }

    @RequestMapping(value = "/pub/v1/recommends", method = RequestMethod.GET)
    @ApiOperation(value = "查询推荐演出列表", httpMethod = "GET", response = ProgramVO.class, notes = "查询推荐演出列表")
    public TPageResult<ProgramVO> searchRecommendList(@ApiParam(required = true, name = "pageNo", value = "页码") @RequestParam int pageNo,
                                                      @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam int pageSize) {

        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setOffset(pageNo);
        paginationParam.setLength(pageSize);

        TPageResult<ProgramVO> result = programService.searchProgramList(paginationParam, 1);
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return result;
    }

    @RequestMapping(value = "/pub/v1/projects/{projectId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看演出详情", httpMethod = "GET", response = ProgramDetailVO.class, notes = "查看演出详情")
    public TSingleResult<ProgramDetailVO> searchProgramList(@ApiParam(required = true, name = "projectId", value = "演出ID") @PathVariable("projectId") Integer projectId) {

        return programService.getProgramById(projectId);
    }


}
