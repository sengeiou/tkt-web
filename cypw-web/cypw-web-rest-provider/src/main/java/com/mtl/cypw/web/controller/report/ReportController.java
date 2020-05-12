package com.mtl.cypw.web.controller.report;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.report.vo.MemberReportVO;
import com.mtl.cypw.web.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/13.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-统计"})
@CrossOrigin
public class ReportController extends WebGenericController {

    @Resource
    ReportService reportService;

    @RequestMapping(value = "/buyer/v1/member/report", method = RequestMethod.GET)
    @ApiOperation(value = "查询会员报表", httpMethod = "GET", response = MemberReportVO.class, notes = "查询会员报表")
    public TSingleResult<MemberReportVO> getMemberReport() {

        return reportService.getMemberReportVO();
    }
}
