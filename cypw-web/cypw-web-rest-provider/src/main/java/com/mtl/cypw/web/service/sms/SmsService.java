package com.mtl.cypw.web.service.sms;

import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.mpm.client.SmsApiClient;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.domain.mpm.constant.CacheConstant;
import com.mtl.cypw.domain.mpm.param.SmsSendParam;
import com.mtl.cypw.web.common.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tang.
 * @date 2019/12/12.
 */
@Service
@Slf4j
public class SmsService {


    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Autowired
    SmsApiClient client;

    public static final String PRODUCT = "CYPW";

    public static final String SMS_USER_VERIFY_CODE = "SMS_USER_VERIFY_CODE";

    public TSingleResult<Boolean> sendSms(String phone) {
        SmsSendParam param = new SmsSendParam();
        String verifyCode = (int) (Math.random() * 9000 + 1000) + "";
        redisTemplate.saveString(CacheConstant.getVerifyCodeKey(phone), verifyCode, 600);
        Map<String, String> variables = new HashMap<>();
        variables.put("VERIFY_CODE", verifyCode);
        param.setVariables(variables);
        param.setTemplateCode(SMS_USER_VERIFY_CODE);
        param.setProduct(PRODUCT);
        param.setCountryCode(86);
        param.setCellphone(phone);
        param.setEnterpriseId(Operator.getEnterpriseId());

        GenericRequest<SmsSendParam> request = new GenericRequest<>();
        request.setParam(param);
        return client.sendSms(request);

    }


}
