package com.mtl.cypw.web.controller.mpm;

import com.juqitech.response.TMultiResult;
import com.mtl.cypw.web.controller.mpm.vo.MachinePosterVO;
import com.mtl.cypw.web.service.mpm.MachinePosterService;
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
 * @date 2020/3/16.
 */
@RestController
@Slf4j
@Api(tags = {"彩熠-机器海报"})
@CrossOrigin
public class MachinePosterController {

    @Resource
    private MachinePosterService machinePosterService;

    @RequestMapping(value = "/pub/v1/machine_posters", method = RequestMethod.GET)
    @ApiOperation(value = "查询企业所有机器海报", httpMethod = "GET", response = MachinePosterVO.class, notes = "查询企业所有机器海报")
    public TMultiResult<MachinePosterVO> searchMachinePoster() {
        return machinePosterService.searchMachinePoster();
    }

}
