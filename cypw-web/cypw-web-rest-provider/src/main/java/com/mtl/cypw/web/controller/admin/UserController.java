package com.mtl.cypw.web.controller.admin;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.admin.param.AdminUserLoginParam;
import com.mtl.cypw.web.controller.admin.vo.UserVO;
import com.mtl.cypw.web.service.admin.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/18.
 */
@RestController
@Slf4j
@Api(tags = {"管理端用户"})
@CrossOrigin
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/pub/v1/admin/user/login", method = RequestMethod.POST)
    @ApiOperation(value = "管理员登录", httpMethod = "POST", response = UserVO.class, notes = "管理员登录")
    public TSingleResult<UserVO> login(@ApiParam(required = true, name = "loginParam", value = "登录信息") @RequestBody AdminUserLoginParam loginParam) {

        return userService.adminUserLogin(loginParam);
    }
}
