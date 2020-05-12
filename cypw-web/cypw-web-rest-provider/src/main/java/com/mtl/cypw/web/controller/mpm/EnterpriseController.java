package com.mtl.cypw.web.controller.mpm;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.param.EnterpriseQueryParam;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.mpm.vo.EnterpriseVO;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * @author tang.
 * @date 2019/11/26.
 */
@RestController
@Slf4j
@Api(tags = {"02-C端-首页&商户信息"})
@CrossOrigin
public class EnterpriseController extends WebGenericController {

    @Resource
    EnterpriseService enterpriseService;

    @RequestMapping(value = "/pub/v1/enterprise", method = RequestMethod.GET)
    @ApiOperation(value = "查询商户信息", httpMethod = "GET", response = EnterpriseVO.class, notes = "查询商户信息")
    public TSingleResult<EnterpriseVO> getEnterprise(@ApiParam(name = "tenantId", value = "tenantId") @RequestParam(required = false) String tenantId,
                                                     @ApiParam(name = "appId", value = "appId") @RequestParam(required = false) String appId,
                                                     HttpServletRequest httpServletRequest) {
        String domainUrl = httpServletRequest.getServerName();
        EnterpriseQueryParam query = new EnterpriseQueryParam();
        query.setTenantId(tenantId);
        query.setDomainName(domainUrl);
        query.setAppId(appId);
        QueryRequest<EnterpriseQueryParam> request = QueryRequest.build();
        request.setParam(query);
        return enterpriseService.getEnterprise(request);
    }

}
