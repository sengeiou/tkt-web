package com.mtl.cypw.web.service.auth;

import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.auth.client.CmbApiClient;
import com.mtl.cypw.domain.auth.dto.CmbAuthDTO;
import com.mtl.cypw.domain.auth.param.AuthCheckSignParam;
import com.mtl.cypw.domain.auth.request.CmbRequest;
import com.mtl.cypw.domain.auth.response.CmbAuthResponse;
import com.mtl.cypw.web.common.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/1/15.
 */
@Service
@Slf4j
public class CmbService {

    @Resource
    CmbApiClient client;

    public TSingleResult<CmbAuthResponse> auth() {
        CmbRequest param = new CmbRequest();
        param.setEnterpriseId(Operator.getEnterpriseId());
        GenericRequest<CmbRequest> request = new GenericRequest<>();
        request.setParam(param);
        return client.auth(request);
    }

    public TSingleResult<CmbAuthDTO> checkSign(String responseStr) {
        AuthCheckSignParam param = new AuthCheckSignParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setResponseStr(responseStr);
        GenericRequest<AuthCheckSignParam> request = new GenericRequest<>();
        request.setParam(param);
        return client.checkSign(request);
    }
}

