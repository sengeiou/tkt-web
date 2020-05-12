package com.mtl.cypw.test;

import com.alibaba.fastjson.JSONObject;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.controller.member.vo.CheckInUserVO;
import com.mtl.cypw.web.controller.member.vo.MemberVO;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/17.
 */
@Slf4j
public class RedisTest extends BaseTest {

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Autowired
    EnterpriseService enterpriseService;

    @Test
    public void get() {
//        redisTemplate.getBean("c3f3e07a-ca83-4656-98a6-d229321b8597");
        MemberVO member = redisTemplate.getBean("c3f3e07a-ca83-4656-98a6-d229321b8597", MemberVO.class);
        System.out.println(JSONObject.toJSONString(member));
    }

    @Test
    public void delete() {
//        redisTemplate.getBean("c3f3e07a-ca83-4656-98a6-d229321b8597");
        redisTemplate.delete(Arrays.asList("ENTERPRISE_INFO_ID_7", "ENTERPRISE_INFO_TENANT_ID_72099987348033536"));
//        QueryRequest<EnterpriseQueryParam> request = new QueryRequest<>();
//        EnterpriseQueryParam param = new EnterpriseQueryParam();
//        param.setEnterpriseId(7);
//        request.setParam(param);
//        TSingleResult<EnterpriseVO> result = enterpriseService.getEnterprise(request);
//        log.info("enterprise:{}", result.getData());
    }

    @Test
    public void geo() {
        addGeo( 1, 121.4272960000, 31.1841500000);
        addGeo( 2, 121.4369058600, 31.1952189500);
        addGeo( 3, 121.4382147800, 31.1765500200);
        geoRadius(121.4345884300,31.1817821000,500);
        geoRadius(121.4345884300,31.1817821000,1000);
        geoRadius(121.4345884300,31.1817821000,3000);
    }

    private void addGeo(Integer theatreId, Double longitude, Double latitude) {
        redisTemplate.geoRemove(Constants.getTheatreGeoKey(), String.valueOf(theatreId));
        redisTemplate.geoAdd(Constants.getTheatreGeoKey(), String.valueOf(theatreId), longitude, latitude);
    }

    private void geoRadius(Double longitude, Double latitude, double radius) {
        List<String> list = redisTemplate.geoRadius(Constants.getTheatreGeoKey(), longitude, latitude, radius);
        log.info("list:{}", JSONObject.toJSONString(list));
    }

}
