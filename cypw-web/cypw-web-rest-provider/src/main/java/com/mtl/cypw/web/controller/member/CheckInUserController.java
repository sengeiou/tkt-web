package com.mtl.cypw.web.controller.member;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.member.param.CheckInUserLoginParam;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.service.member.CheckInUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/2/12.
 */
@RestController
@Slf4j
@Api(tags = {"核销用户"})
@CrossOrigin
public class CheckInUserController {

    @Resource
    CheckInUserService checkInUserService;

    @RequestMapping(value = "/pub/v1/check/user/login", method = RequestMethod.POST)
    @ApiOperation(value = "核销用户登录", httpMethod = "POST", response = CheckInUserVO.class, notes = "核销用户登录")
    public TSingleResult<CheckInUserVO> login(@ApiParam(required = true, name = "loginParam", value = "登录信息") @RequestBody CheckInUserLoginParam loginParam) {

        return checkInUserService.checkInUserLogin(loginParam);
    }

}
