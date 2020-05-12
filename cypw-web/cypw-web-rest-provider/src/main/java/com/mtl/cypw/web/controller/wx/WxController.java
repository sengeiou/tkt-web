package com.mtl.cypw.web.controller.wx;

import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.wx.response.WxConfigResponse;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.service.wx.OffiaccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@RestController
@Api(tags = {"微信"})
@Slf4j
@CrossOrigin
public class WxController extends WebGenericController {

    @Resource
    private OffiaccountService offiaccountService;

    @RequestMapping(value = "/pub/v1/wechat/getConfig", method = RequestMethod.GET)
    @ApiOperation(value = "获取微信分享参数", httpMethod = "GET", response = WxConfigResponse.class, notes = "获取微信分享参数")
    public TSingleResult<WxConfigResponse> getConfigInfo(@ApiParam(required = true, name = "url", value = "分享链接") @RequestParam String url) {

        return offiaccountService.getConfigInfo(url);
    }

    @RequestMapping(value = "/pub/v1/wechat/getOpenId", method = RequestMethod.GET)
    @ApiOperation(value = "获取微信openId", httpMethod = "GET", response = String.class, notes = "获取微信openId")
    public TSingleResult<String> getOpenIdByCode(@ApiParam(required = true, name = "code", value = "code") @RequestParam String code) {

        return offiaccountService.getOpenIdByCode(code);
    }

}
