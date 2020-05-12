package com.mtl.cypw.web.service.show;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.show.client.ShortageRegistrationApiClient;
import com.mtl.cypw.domain.show.param.ShortageRegistrationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/2.
 */
@Slf4j
@Service
public class ShortageRegistrationService {

    @Resource
    ShortageRegistrationApiClient client;

    public TSingleResult<Boolean> create(GenericRequest<ShortageRegistrationParam> request) {
        log.debug("新增缺货登记，request：{}", JSONObject.toJSONString(request));
       return client.create(request);
    }

}
