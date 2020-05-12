package com.mtl.cypw.test;

import com.mtl.cypw.web.service.sms.SmsService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/13.
 */
public class SmsTest extends BaseTest {

    @Resource
    SmsService smsService;

    @Test
    public void sendMessage(){
//        smsService.sendSms("18321407297");
    }
}
