package com.mtl.cypw.web.controller.member;

import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.member.param.CreateMemberSignInParam;
import com.mtl.cypw.web.controller.member.vo.MemberSignInVO;
import com.mtl.cypw.web.controller.member.vo.SignInVO;
import com.mtl.cypw.web.service.member.MemberSignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-会员签到"})
@CrossOrigin
public class MemberSignInController {

    @Resource
    MemberSignInService memberSignInService;

    @RequestMapping(value = "/buyer/v1/member/search/sign_in/theatre", method = RequestMethod.GET)
    @ApiOperation(value = "查询附近可以签到的剧院", httpMethod = "GET", response = SignInVO.class, notes = "查询附近可以签到的剧院")
    public TMultiResult<SignInVO> searchSignInTheatre(@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam Double longitude,
                                                      @ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam Double latitude) {

        return memberSignInService.searchSignInTheatre(longitude, latitude);
    }

    @RequestMapping(value = "/buyer/v1/member/search/sign_in_list", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户签到列表", httpMethod = "GET", response = MemberSignInVO.class, notes = "查询用户签到列表")
    public TMultiResult<MemberSignInVO> searchSignInTheatre(@ApiParam(required = true, name = "signInTimeBegin", value = "签到时间开始") @DateTimeFormat Date signInTimeBegin,
                                                            @ApiParam(required = true, name = "signInTimeEnd", value = "签到时间结束") @DateTimeFormat Date signInTimeEnd) {

        return memberSignInService.searchMemberSignInList(signInTimeBegin, signInTimeEnd);
    }

    @RequestMapping(value = "/buyer/v1/member/create/sign_in", method = RequestMethod.POST)
    @ApiOperation(value = "用户签到", httpMethod = "POST", response = Boolean.class, notes = "用户签到")
    public TSingleResult<Boolean> searchSignInTheatre(@ApiParam(required = true, name = "createMemberSignInParam", value = "签到参数") @RequestBody CreateMemberSignInParam createMemberSignInParam) {

        return memberSignInService.addMemberSignIn(createMemberSignInParam);
    }
}
