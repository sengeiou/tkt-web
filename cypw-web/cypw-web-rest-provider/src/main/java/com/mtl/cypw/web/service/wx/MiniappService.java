package com.mtl.cypw.web.service.wx;

import com.juqitech.request.GenericRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.wx.client.MiniappApiClient;
import com.mtl.cypw.domain.wx.request.WxRequest;
import com.mtl.cypw.domain.wx.response.MiniappSessionResult;
import com.mtl.cypw.web.common.Operator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/10.
 */
@Service
public class MiniappService {

    @Resource
    MiniappApiClient client;

    public TSingleResult<MiniappSessionResult> getJscode2Session(String code) {
        WxRequest param = new WxRequest();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setCode(code);
        GenericRequest<WxRequest> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<MiniappSessionResult> result = client.getJscode2Session(request);
        if (result.success()) {
            return result;
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

}
