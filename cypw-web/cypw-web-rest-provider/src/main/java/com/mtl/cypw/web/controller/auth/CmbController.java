package com.mtl.cypw.web.controller.auth;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.auth.response.CmbAuthResponse;
import com.mtl.cypw.web.service.auth.CmbService;
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
 * @date 2020/1/15.
 */
@RestController
@Api(tags = {"招行"})
@Slf4j
@CrossOrigin
public class CmbController {

    @Resource
    private CmbService cmbService;

    @RequestMapping(value = "/pub/v1/cmb/auth", method = RequestMethod.GET)
    @ApiOperation(value = "获取招行授权参数", httpMethod = "GET", response = CmbAuthResponse.class, notes = "获取招行授权参数")
    public TSingleResult<CmbAuthResponse> auth() {

        return cmbService.auth();
    }

}
