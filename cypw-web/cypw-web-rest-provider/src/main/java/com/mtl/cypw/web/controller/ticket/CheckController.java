package com.mtl.cypw.web.controller.ticket;

import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.ticket.param.CheckConsumeParam;
import com.mtl.cypw.web.controller.ticket.vo.TicketPaperVO;
import com.mtl.cypw.web.service.ticket.CheckInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Johnathon.Yuan
 * @date 2019-02-17 20:23
 */
@RestController
@Api(tags = {"07-核销小程序-验票*核销"})
@Slf4j
@CrossOrigin
public class CheckController extends WebGenericController {

    @Autowired
    private CheckInService checkInService;

    @ApiOperation(value = "查询电子票@核销码", httpMethod = "GET", response = TicketPaperVO.class)
    @GetMapping("/seller/v1/check/queryByCode")
    public TSingleResult<TicketPaperVO> queryByCode(@ApiParam("检票码") String code) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        return checkInService.queryByCode(code);
    }

    @ApiOperation(value = "查询电子票@手机号", httpMethod = "GET", response = TicketPaperVO.class)
    @GetMapping("/seller/v1/check/queryByMobileNo")
    public TMultiResult<TicketPaperVO> queryByMobileNo(@ApiParam("手机号") String mobile) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        return checkInService.queryByMobileNo(mobile);
    }

    @ApiOperation(value = "核销电子票", httpMethod = "POST", response = TicketPaperVO.class)
    @PostMapping("/seller/v1/check/consume")
    public TSingleResult<TicketPaperVO> consume(@ApiParam("核销参数") @RequestBody CheckConsumeParam param) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        return checkInService.consume(param);
    }

    @ApiOperation(value = "查询核销记录@总数", httpMethod = "GET", response = Integer.class)
    @GetMapping("/seller/v1/check/countQuery")
    public TSingleResult<Integer> countQuery(@ApiParam("核销日期")
             @RequestParam(value = "checkDate", required = true)
             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date checkDate) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        return checkInService.countQuery(checkDate);
    }

    @ApiOperation(value = "查询核销记录@分页", httpMethod = "GET", response = TicketPaperVO.class)
    @GetMapping("/seller/v1/check/pageQuery")
    public TPageResult<TicketPaperVO> pageQuery(
            @ApiParam("页码") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @ApiParam("核销日期") @RequestParam(value = "checkDate", required = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date checkDate) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        return checkInService.pageQuery(pageNo, pageSize, checkDate);
    }

}
