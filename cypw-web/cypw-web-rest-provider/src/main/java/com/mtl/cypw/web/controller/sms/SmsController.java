package com.mtl.cypw.web.controller.sms;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.service.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/16.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-短信发送"})
@CrossOrigin
public class SmsController extends WebGenericController {

    @Resource
    SmsService smsService;

    @RequestMapping(value = "/pub/v1/sms/verify/{phone}", method = RequestMethod.GET)
    @ApiOperation(value = "验证码", httpMethod = "GET", response = String.class, notes = "验证码")
    public TSingleResult<Boolean> getVerifyCode(@ApiParam(required = true, name = "phone", value = "手机号") @PathVariable("phone") String phone) {

        return smsService.sendSms(phone);
    }
}
