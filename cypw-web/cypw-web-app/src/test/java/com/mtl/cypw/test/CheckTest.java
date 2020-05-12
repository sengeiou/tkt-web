package com.mtl.cypw.test;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.web.controller.member.param.CheckInUserLoginParam;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.controller.ticket.vo.TicketPaperVO;
import com.mtl.cypw.web.service.member.CheckInUserService;
import com.mtl.cypw.web.service.ticket.CheckInService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Johnathon.Yuan
 * @date 2020-02-20 14:19
 */
@Slf4j
public class CheckTest extends BaseTest {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private CheckInUserService checkInUserService;
    @Autowired
    CommonRedisTemplate commonRedisTemplate;

    @Test
    public void testLogin() {
        CheckInUserLoginParam param = new CheckInUserLoginParam();
        param.setUserName("Aaron@pxq");
        param.setUserPass("123456");
        TSingleResult<CheckInUserVO> result = checkInUserService.checkInUserLogin(param);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testQueryCode() {
        CheckInUserLoginParam param = new CheckInUserLoginParam();
        param.setUserName("Aaron@pxq");
        param.setUserPass("123456");
        TSingleResult<CheckInUserVO> result = checkInUserService.checkInUserLogin(param);
        log.info(JsonUtils.toJson(result));
        TSingleResult<TicketPaperVO> result1 = checkInService.queryByCode("1050033623");
        log.info(JsonUtils.toJson(result1));
    }

    @Test
    public void redisTest(){
        commonRedisTemplate.saveString("test","'helloworld1'");
        String str = commonRedisTemplate.getString("test");
        System.out.println(str);
    }
}
