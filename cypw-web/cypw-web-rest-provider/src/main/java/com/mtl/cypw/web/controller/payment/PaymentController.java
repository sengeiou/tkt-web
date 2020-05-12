package com.mtl.cypw.web.controller.payment;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.enums.PlatformSource;
import com.mtl.cypw.domain.payment.callback.CcbCallbackParam;
import com.mtl.cypw.domain.payment.enums.PayTypeEnum;
import com.mtl.cypw.domain.payment.result.PayResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.service.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@RestController
@Api(tags = {"支付"})
@Slf4j
@CrossOrigin
public class PaymentController extends WebGenericController {

    @Resource
    private PaymentService paymentService;

    /**
     * 统一下单
     */
    @RequestMapping(value = "/buyer/v1/unifiedOrder/{payTypeEnum}/{orderId}/{src}", method = RequestMethod.GET)
    @ApiOperation(value = "统一下单", httpMethod = "GET", response = PayResult.class, notes = "统一下单")
    public TSingleResult<PayResult> unifiedOrder(@ApiParam(required = true, name = "payTypeEnum", value = "支付方式") @PathVariable PayTypeEnum payTypeEnum,
                                                 @ApiParam(required = true, name = "orderId", value = "订单ID") @PathVariable Integer orderId,
                                                 @ApiParam(required = true, name = "src", value = "来源") @PathVariable PlatformSource src,
                                                 @ApiParam(name = "openid", value = "openid") @RequestParam(required = false) String openid,
                                                 @ApiParam(name = "installment", value = "分期付款期数") @RequestParam(required = false) Integer installment,
                                                 HttpServletRequest request) {

        return paymentService.pay(payTypeEnum, orderId, src, openid, request.getRemoteAddr(), installment);
    }

    @RequestMapping(value = "/pub/v1/notify/wx", method = RequestMethod.POST)
    public String wxPayReturn(HttpServletRequest request) {
        log.debug("微信支付回调开始");
        try {
            String xml = getAllRequestParamsByStringEntity(request);
            paymentService.payReturn(PayTypeEnum.WECHAT_PAY, xml);
        } catch (Exception e) {
            log.error("微信支付回调异常：{}", e.getMessage());
        }
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    @RequestMapping(value = "/pub/v1/notify/ali", method = RequestMethod.POST)
    public String aliPayReturn(HttpServletRequest request) {
        log.debug("支付宝支付回调开始");
        try {
            Map<String, String> params = convertRequestParamsToMap(request);
            String paramsJson = JSONObject.toJSONString(params);
            Boolean b = paymentService.payReturn(PayTypeEnum.ALI_PAY, paramsJson);
            if (b) {
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            log.error("支付宝支付回调异常：{}", e.getMessage());
            return "failure";
        }
    }

    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }

    @RequestMapping(value = "/pub/v1/notify/cmb", method = RequestMethod.POST)
    public String cmbPayReturn(HttpServletRequest request) {
        log.debug("招行支付回调开始");
        String jsonParams = request.getParameter("jsonRequestData");
        log.debug("回调数据：{}", jsonParams);
        paymentService.payReturn(PayTypeEnum.CMB_PAY, jsonParams);
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    @RequestMapping(value = "/pub/v1/notify/ccb", method = RequestMethod.POST)
    public String ccbPayReturn(HttpServletRequest request) {
        log.debug("建行支付回调开始");
        CcbCallbackParam param = new CcbCallbackParam();
        String posId = request.getParameter("POSID");
        String branchId = request.getParameter("BRANCHID");
        String orderId = request.getParameter("ORDERID");
        String payment = request.getParameter("PAYMENT");
        String curCode = request.getParameter("CURCODE");
        param.setPosId(posId);
        param.setBranchId(branchId);
        param.setPayment(payment);
        param.setOrderId(orderId);
        param.setCurCode(curCode);

        String remark1 = request.getParameter("REMARK1");
        String remark2 = request.getParameter("REMARK2");
        String accType = request.getParameter("ACC_TYPE");
        String success = request.getParameter("SUCCESS");
        String type = request.getParameter("TYPE");
        param.setRemark1(remark1);
        param.setRemark2(remark2);
        param.setAccType(accType);
        param.setSuccess(success);
        param.setType(type);

        String referer = request.getParameter("REFERER");
        String clientIp = request.getParameter("CLIENTIP");
        String accDate = request.getParameter("ACCDATE");
        String usrMsg = request.getParameter("USRMSG");
        String installNum = request.getParameter("INSTALLNUM");
        param.setReferer(referer);
        param.setClientIp(clientIp);
        param.setAccDate(accDate);
        param.setUsrMsg(usrMsg);
        param.setInstallNum(installNum);

        String errMsg = request.getParameter("ERRMSG");
        String usrInfo = request.getParameter("USRINFO");
        String discount = request.getParameter("DISCOUNT");
        String sign = request.getParameter("SIGN");
        param.setErrMsg(errMsg);
        param.setUsrInfo(usrInfo);
        param.setDiscount(discount);
        param.setSign(sign);
        paymentService.ccbPayReturn(param);
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    /**
     * PayPal支付
     *
     * @param paymentId
     * @param payerId
     */
    @RequestMapping(value = "/pub/v1/notify/paypal", method = RequestMethod.GET)
    public void executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletResponse resp) {

        String url = paymentService.payPalExecute(paymentId, payerId);
        try {
            resp.sendRedirect(url);
        } catch (IOException e) {
            log.error("页面重定向失败,e:{}", e.getMessage());
        }
    }

    public static String getAllRequestParamsByStringEntity(HttpServletRequest request) {
        String queryStr = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            reader.close();
            queryStr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryStr;
    }
}
