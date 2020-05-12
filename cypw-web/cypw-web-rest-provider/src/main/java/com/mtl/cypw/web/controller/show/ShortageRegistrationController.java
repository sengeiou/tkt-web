package com.mtl.cypw.web.controller.show;

import com.juqitech.request.GenericRequest;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.show.param.ShortageRegistrationParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.service.show.ShortageRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/2.
 */
@RestController
@Slf4j
@Api(value = "票星球-缺货登记", tags = {"票星球-缺货登记接口"})
@CrossOrigin
public class ShortageRegistrationController extends WebGenericController {


    @Resource
    ShortageRegistrationService shortageRegistrationService;

    @RequestMapping(value = "/buyer/v1/shortage/registration", method = RequestMethod.POST)
    @ApiOperation(value = "缺货登记", httpMethod = "POST", response = Boolean.class, notes = "缺货登记")
    public TSingleResult<Boolean> create(@ApiParam(required = true, name = "param", value = "缺货登记信息") @RequestBody ShortageRegistrationParam param) {
        param.setMemberId(Operator.getMemberId());
        GenericRequest<ShortageRegistrationParam> request = new GenericRequest();
        request.setParam(param);
        return shortageRegistrationService.create(request);
    }
}
