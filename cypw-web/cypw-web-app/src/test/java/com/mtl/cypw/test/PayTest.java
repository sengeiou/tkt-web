package com.mtl.cypw.test;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.enums.PlatformSource;
import com.mtl.cypw.api.payment.client.AliPayApiClient;
import com.mtl.cypw.domain.payment.enums.AliPayTypeEnum;
import com.mtl.cypw.domain.payment.enums.PayTypeEnum;
import com.mtl.cypw.domain.payment.param.AliPayRequestParam;
import com.mtl.cypw.domain.payment.result.AliPayResult;
import com.mtl.cypw.domain.payment.result.PayResult;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@Slf4j
public class PayTest extends BaseTest {

    @Resource
    private PaymentService paymentService;

    @Resource
    private AliPayApiClient client;

    @Test
    public void pay() {
        Operator.setEnterpriseId(1);
        TSingleResult<PayResult> result = paymentService.pay(PayTypeEnum.CCB_PAY, 884, PlatformSource.M_WEB, null, null,3);
        log.info("result:{}", result.getData());
    }

    @Test
    public void aliPay() {
//        AliPayRequestParam param = new AliPayRequestParam();
//        param.setEnterpriseId(1);
//        param.setPayTypeEnum(PayTypeEnum.ALI_PAY);
//        param.setOrderNo("P81001030151526903");
//        param.setOrderAmount(1L);
//        param.setAliPayTypeEnum(AliPayTypeEnum.WAP);
//        param.setSubject("大乐透");
//        GenericRequest<AliPayRequestParam> requestGenericRequest = new GenericRequest<>();
//        requestGenericRequest.setParam(param);
//        TSingleResult<AliPayResult> result = client.unifiedOrder(requestGenericRequest);
//        System.out.println("result={" + JSONObject.toJSONString(result.getData()));
    }
}
