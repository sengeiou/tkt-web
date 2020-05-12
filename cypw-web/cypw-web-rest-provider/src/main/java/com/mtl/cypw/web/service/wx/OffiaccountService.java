package com.mtl.cypw.web.service.wx;

import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.wx.client.OffiaccountApiClient;
import com.mtl.cypw.api.wx.endpoint.OffiaccountApi;
import com.mtl.cypw.domain.wx.request.WxRequest;
import com.mtl.cypw.domain.wx.response.WxConfigResponse;
import com.mtl.cypw.web.common.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/10.
 */
@Slf4j
@Service
public class OffiaccountService {
    @Resource
    private OffiaccountApiClient client;

    public TSingleResult<WxConfigResponse> getConfigInfo(String url) {
        WxRequest param = new WxRequest();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setUrl(url);
        GenericRequest<WxRequest> request = new GenericRequest<>();
        request.setParam(param);
        return client.getConfigInfo(request);
    }

    public TSingleResult<String> getOpenIdByCode(String code) {
        WxRequest param = new WxRequest();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setCode(code);
        GenericRequest<WxRequest> request = new GenericRequest<>();
        request.setParam(param);
        return client.getOpenIdByCode(request);
    }
}
