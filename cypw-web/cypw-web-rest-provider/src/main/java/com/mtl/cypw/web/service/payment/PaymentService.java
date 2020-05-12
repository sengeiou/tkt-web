package com.mtl.cypw.web.service.payment;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.enums.PlatformSource;
import com.juqitech.service.utils.CommonUtil;
import com.juqitech.service.utils.DateUtils;
import com.mtl.cypw.api.order.client.OrderApiClient;
import com.mtl.cypw.api.order.client.OrderQueryApiClient;
import com.mtl.cypw.api.payment.client.*;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.utils.Money;
import com.mtl.cypw.domain.member.dto.MemberDTO;
import com.mtl.cypw.domain.order.dto.OrderDTO;
import com.mtl.cypw.domain.order.param.OrderPaidParam;
import com.mtl.cypw.domain.payment.callback.CcbCallbackParam;
import com.mtl.cypw.domain.payment.enums.*;
import com.mtl.cypw.domain.payment.param.*;
import com.mtl.cypw.domain.payment.result.*;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@Slf4j
@Service
public class PaymentService {

    @Resource
    WechatPayApiClient wechatPayApiClient;

    @Resource
    AliPayApiClient aliPayApiClient;

    @Resource
    CmbPayApiClient cmbPayApiClient;

    @Resource
    CcbPayApiClient ccbPayApiClient;

    @Resource
    PayPalApiClient payPalApiClient;

    @Resource
    OrderApiClient orderApiClient;

    @Resource
    OrderQueryApiClient orderQueryApiClient;

    @Resource
    MemberService memberService;

    @Value("${wx.notify.url}")
    private String wxNotifyUrl;
    @Value("${ali.notify.url}")
    private String aliNotifyUrl;
    @Value("${ali.quit.url}")
    private String aliQuitUrl;
    @Value("${cmb.notify.url}")
    private String cmbNotifyUrl;
    @Value("${cmb.quit.url}")
    private String cmbQuitUrl;
    @Value("${paypal.return.url}")
    private String payPalReturnUrl;
    @Value("${paypal.cancel.url}")
    private String payPalCancelUrl;
    @Value("${paypal.success.url}")
    private String payPalSuccessUrl;


    public TSingleResult<PayResult> pay(PayTypeEnum payTypeEnum, Integer orderId, PlatformSource src, String openid, String remoteAddress, Integer installment) {
        IdRequest idRequest = new IdRequest(orderId.toString());
        TSingleResult<OrderDTO> orderDtoResult = orderQueryApiClient.findById(idRequest);
        OrderDTO orderDto = orderDtoResult.getData();
        if (orderDto == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        if (PayTypeEnum.ZERO_PAY.equals(payTypeEnum)) {
            return zeroPay(orderDto);
        } else if (PayTypeEnum.WECHAT_PAY.equals(payTypeEnum)) {
            return wechatPay(orderDto, src, openid, remoteAddress);
        } else if (PayTypeEnum.ALI_PAY.equals(payTypeEnum)) {
            return aliPay(orderDto, src);
        } else if (PayTypeEnum.CMB_PAY.equals(payTypeEnum)) {
            return cmbPay(orderDto, src);
        } else if (PayTypeEnum.CCB_PAY.equals(payTypeEnum)) {
            return ccbPay(orderDto, src, installment);
        } else if (PayTypeEnum.PAY_PAL.equals(payTypeEnum)) {
            return payPalPay(orderDto);
        } else {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
    }

    public Boolean payReturn(PayTypeEnum payTypeEnum, String responseStr) {
        GenericRequest<PayCheckSignParam> request = new GenericRequest();
        PayCheckSignParam param = new PayCheckSignParam();
        param.setResponseStr(responseStr);
        request.setParam(param);
        TSingleResult<Boolean> result = new TSingleResult<>();

        if (PayTypeEnum.WECHAT_PAY.equals(payTypeEnum)) {
            result = wechatPayApiClient.checkSign(request);
        } else if (PayTypeEnum.ALI_PAY.equals(payTypeEnum)) {
            result = aliPayApiClient.checkSign(request);
        } else if (PayTypeEnum.CMB_PAY.equals(payTypeEnum)) {
            result = cmbPayApiClient.checkSign(request);
        } else if (PayTypeEnum.CCB_PAY.equals(payTypeEnum)) {

        } else {
        }
        if (result.success()) {
            log.debug("支付回调完成");
            return true;
        } else {
            log.error("支付回调失败", JSONObject.toJSONString(result));
            return false;
        }
    }

    public void ccbPayReturn(CcbCallbackParam param) {
        GenericRequest<CcbCallbackParam> request = new GenericRequest();
        request.setParam(param);
        TSingleResult<Boolean> result = ccbPayApiClient.checkSign(request);
        if (result.success()) {
            log.debug("支付回调完成");
        } else {
            log.error("支付回调失败", JSONObject.toJSONString(result));
        }
        log.debug("建行回调参数，param:{}", param.toString());
    }

    public String payPalExecute(String paymentId, String payerId) {
        PayPalExecutePaymentParam param = new PayPalExecutePaymentParam();
        param.setPaymentId(paymentId);
        param.setPayerId(payerId);

        GenericRequest<PayPalExecutePaymentParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<Integer> result = payPalApiClient.executePayment(request);
        if (result.success()) {
            return payPalSuccessUrl + result.getData();
        } else {
            log.error("PayPal执行支付失败，code:{},message:{}", result.getStatusCode(), result.getComments());
            return payPalCancelUrl;
        }
    }

    private TSingleResult<PayResult> zeroPay(OrderDTO orderDto) {
        if (orderDto.getActualAmount().getCent() > 0) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
        OrderPaidParam paidParam = new OrderPaidParam();
        paidParam.setOrderId(orderDto.getOrderId());
        paidParam.setOrderPrice(new Money(0));
        paidParam.setPaySuccessTime(DateUtils.now());
        paidParam.setPayType(PayTypeEnum.ZERO_PAY);
        paidParam.setSerialNo(CommonUtil.generateOID());

        GenericRequest<OrderPaidParam> request = new GenericRequest<>();
        request.setParam(paidParam);
        TSingleResult<Boolean> result = orderApiClient.paid(request);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        return ResultBuilder.succTSingle(null);
    }

    private TSingleResult<PayResult> wechatPay(OrderDTO orderDto, PlatformSource src, String openid, String remoteAddress) {
        WechatPayRequestParam param = new WechatPayRequestParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setPayTypeEnum(PayTypeEnum.WECHAT_PAY);
        param.setOrderNo(orderDto.getOrderNo());
        param.setOrderAmount(orderDto.getActualAmount().getCent());
        param.setProduct(orderDto.getOrderTitle());
        param.setRemoteAddress(remoteAddress);
        param.setNotifyUrl(wxNotifyUrl);
        if (PlatformSource.WEB.equals(src)) {
            param.setWechatPayTypeEnum(WechatPayTypeEnum.NATIVE);
        } else if (PlatformSource.WEIXIN.equals(src)) {
            param.setWechatPayTypeEnum(WechatPayTypeEnum.JSAPI);
            if (StringUtils.isNotEmpty(openid)) {
                param.setOpenid(openid);
            } else {
                MemberDTO member = memberService.getMember2();
                if (member != null) {
                    param.setOpenid(member.getOpenId());
                }
            }
        } else if (PlatformSource.M_WEB.equals(src)) {
            param.setWechatPayTypeEnum(WechatPayTypeEnum.MWEB);
            JSONObject json1 = new JSONObject();
            json1.put("type", "IOS");
            json1.put("app_name", "cypw");
            json1.put("bundle_id", "com.mtl");
            JSONObject json2 = new JSONObject();
            json2.put("h5_info", json1);
            param.setSceneInfo(json2.toString());
        } else {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
        GenericRequest<WechatPayRequestParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<WechatPayResult> result = wechatPayApiClient.unifiedOrder(request);
        if (result.success()) {
            return ResultBuilder.succTSingle(result.getData());
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<PayResult> aliPay(OrderDTO orderDto, PlatformSource src) {
        AliPayRequestParam param = new AliPayRequestParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setPayTypeEnum(PayTypeEnum.ALI_PAY);
        param.setOrderNo(orderDto.getOrderNo());
        param.setOrderAmount(orderDto.getActualAmount().getCent());
        if (PlatformSource.WEB.equals(src)) {
            param.setAliPayTypeEnum(AliPayTypeEnum.PAGE);
        } else if (PlatformSource.APP.equals(src)) {
            param.setAliPayTypeEnum(AliPayTypeEnum.APP);
        } else if (PlatformSource.M_WEB.equals(src)) {
            param.setAliPayTypeEnum(AliPayTypeEnum.WAP);
        } else {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
        param.setSubject(orderDto.getOrderTitle());
        param.setNotifyUrl(aliNotifyUrl);
        param.setQuitUrl(aliQuitUrl);
        GenericRequest<AliPayRequestParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<AliPayResult> result = aliPayApiClient.unifiedOrder(request);
        if (result.success()) {
            return ResultBuilder.succTSingle(result.getData());
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<PayResult> cmbPay(OrderDTO orderDto, PlatformSource src) {
        CmbPayRequestParam param = new CmbPayRequestParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setPayTypeEnum(PayTypeEnum.CMB_PAY);
        param.setOrderNo(orderDto.getOrderNo());
        param.setOrderAmount(orderDto.getActualAmount().getCent());
        if (PlatformSource.APP.equals(src)) {
            param.setCmbPayTypeEnum(CmbPayTypeEnum.APP);
        } else if (PlatformSource.M_WEB.equals(src) || PlatformSource.WEIXIN.equals(src)) {
            param.setCmbPayTypeEnum(CmbPayTypeEnum.H5);
        } else {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
        param.setNotifyUrl(cmbNotifyUrl);
        param.setQuitUrl(cmbQuitUrl);
        GenericRequest<CmbPayRequestParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<CmbPayResult> result = cmbPayApiClient.unifiedOrder(request);
        if (result.success()) {
            return ResultBuilder.succTSingle(result.getData());
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<PayResult> ccbPay(OrderDTO orderDto, PlatformSource src, Integer installment) {
        CcbPayRequestParam param = new CcbPayRequestParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setPayTypeEnum(PayTypeEnum.CCB_PAY);
        param.setOrderNo(orderDto.getOrderNo());
        param.setOrderAmount(orderDto.getActualAmount().getCent());
        if (installment != null && installment > 1) {
            param.setInstallment(installment + "");
        }
//        param.setClientIp(orderDto.getClientIp());
        if (PlatformSource.APP.equals(src)) {
            param.setCcbPayTypeEnum(CcbPayTypeEnum.APP);
        } else if (PlatformSource.M_WEB.equals(src) || PlatformSource.WEIXIN.equals(src)) {
            param.setCcbPayTypeEnum(CcbPayTypeEnum.H5);
        } else {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PAY_TYPE.getCode(), ErrorCode.ERROR_COMMON_PAY_TYPE.getMsg());
        }
        GenericRequest<CcbPayRequestParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<CcbPayResult> result = ccbPayApiClient.unifiedOrder(request);
        if (result.success()) {
            return ResultBuilder.succTSingle(result.getData());
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<PayResult> payPalPay(OrderDTO orderDto) {
        PayPalRequestParam param = new PayPalRequestParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setPayTypeEnum(PayTypeEnum.PAY_PAL);
        param.setOrderNo(orderDto.getOrderNo());
        param.setOrderAmount(orderDto.getActualAmount().getCent());
        param.setPayPalPayTypeEnum(PayPalPayTypeEnum.paypal);
        param.setPayPalPayIntentEnum(PayPalPayIntentEnum.order);
        param.setReturnUrl(payPalReturnUrl);
        param.setCancelUrl(payPalCancelUrl);

        GenericRequest<PayPalRequestParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<PayPalResult> result = payPalApiClient.createPayment(request);
        if (result.success()) {
            return ResultBuilder.succTSingle(result.getData());
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }
}
