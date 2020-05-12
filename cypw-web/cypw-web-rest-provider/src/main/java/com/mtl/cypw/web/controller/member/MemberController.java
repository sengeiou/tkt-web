package com.mtl.cypw.web.controller.member;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.domain.member.param.MemberParam;
import com.mtl.cypw.domain.member.param.MemberQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.member.param.LoginParam;
import com.mtl.cypw.web.controller.member.vo.MemberVO;
import com.mtl.cypw.web.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@RestController
@Slf4j
@Api(value = "票星球-会员", tags = {"票星球-会员接口"})
@CrossOrigin
public class MemberController extends WebGenericController {

    @Resource
    MemberService memberService;

    @RequestMapping(value = "/pub/v1/member/login", method = RequestMethod.POST)
    @ApiOperation(value = "会员登录", httpMethod = "POST", response = MemberVO.class, notes = "会员登录")
    public TSingleResult<MemberVO> login(@ApiParam(required = true, name = "loginParam", value = "登录信息") @RequestBody LoginParam loginParam) {
        if (loginParam == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_VERIFY_CODE.getCode(), ErrorCode.ERROR_COMMON_VERIFY_CODE.getMsg());
        }
        return memberService.registerAndLogin(loginParam);
    }

    @RequestMapping(value = "/buyer/v1/member/getMember", method = RequestMethod.GET)
    @ApiOperation(value = "查询会员", httpMethod = "GET", response = MemberVO.class, notes = "查询会员信息")
    public TSingleResult<MemberVO> getMember() {

        return memberService.getMember();
    }

    @RequestMapping(value = "/buyer/v1/member/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改会员信息", httpMethod = "POST", response = Boolean.class, notes = "修改会员信息")
    public TSingleResult<Boolean> updateMemberAddress(@ApiParam(required = true, name = "param", value = "会员信息") @RequestBody MemberParam param) {
        GenericRequest<MemberParam> request = new GenericRequest();
        param.setMemberId(Operator.getMemberId());
        request.setParam(param);
        return memberService.updateMember(request);
    }

}
