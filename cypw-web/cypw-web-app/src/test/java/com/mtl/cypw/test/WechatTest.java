package com.mtl.cypw.test;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.wx.response.WxConfigResponse;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.service.wx.OffiaccountService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/9.
 */
public class WechatTest extends BaseTest {

    @Resource
    private OffiaccountService offiaccountService;

    @Test
    public void getConfig(){
        Operator.setEnterpriseId(1);
        TSingleResult<WxConfigResponse> result = offiaccountService.getConfigInfo("http://10.24.126.37");
        System.out.println(">>>>>>>>"+result.getData());
    }
}
