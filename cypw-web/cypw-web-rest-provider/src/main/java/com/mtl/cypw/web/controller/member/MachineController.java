package com.mtl.cypw.web.controller.member;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.member.param.CheckInUserLoginParam;
import com.mtl.cypw.web.controller.member.param.MachineLoginParam;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.controller.member.vo.MachineVO;
import com.mtl.cypw.web.service.member.CheckInUserService;
import com.mtl.cypw.web.service.member.MachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/12.
 */
@RestController
@Slf4j
@Api(tags = {"设备登录"})
@CrossOrigin
public class MachineController {

    @Resource
    MachineService machineService;

    @RequestMapping(value = "/pub/v1/machine/login", method = RequestMethod.POST)
    @ApiOperation(value = "设备登录", httpMethod = "POST", response = MachineVO.class, notes = "设备登录")
    public TSingleResult<MachineVO> login(@ApiParam(required = true, name = "loginParam", value = "登录信息") @RequestBody MachineLoginParam loginParam) {

        return machineService.machinrLogin(loginParam);
    }

}
