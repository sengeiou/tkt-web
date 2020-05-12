package com.mtl.cypw.web.controller.mpm;

import com.juqitech.response.TMultiResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.mpm.vo.DistrictVO;
import com.mtl.cypw.web.service.mpm.DistrictService;
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
 * @date 2019/11/27.
 */
@RestController
@Slf4j
@Api(tags = {"省.市.区"})
@CrossOrigin
public class DistrictController extends WebGenericController {

    @Resource
    DistrictService districtService;

    @RequestMapping(value = "/pub/v1/districts", method = RequestMethod.GET)
    @ApiOperation(value = "查询区信息", httpMethod = "GET", response = DistrictVO.class, notes = "查询区信息")
    public TMultiResult<DistrictVO> districts() {
        return districtService.districts();
    }
}
