package com.mtl.cypw.web.service.member;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.member.client.MachineApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.domain.member.dto.MachineDTO;
import com.mtl.cypw.domain.member.param.MachineParam;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.member.converter.MachineConverter;
import com.mtl.cypw.web.controller.member.enums.UserTypeEnum;
import com.mtl.cypw.web.controller.member.param.MachineLoginParam;
import com.mtl.cypw.web.controller.member.vo.MachineVO;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author tang.
 * @date 2020/3/12.
 */
@Slf4j
@Service
public class MachineService {

    @Resource
    MachineApiClient client;

    @Resource
    MachineConverter converter;

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Resource
    private EnterpriseService enterpriseService;


    public TSingleResult<MachineVO> machinrLogin(MachineLoginParam param) {
        if (!param.checkParam()) {
            log.error("登录参数有误，param:{}", JSONObject.toJSONString(param));
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        TSingleResult<MachineVO> result = login(param);
        if (result.success()) {
            String accessToken = UUID.randomUUID().toString();
            result.getData().setAccessToken(accessToken);
            redisTemplate.saveBean(accessToken, result.getData(), Constants.tokenExpireTime());
            redisTemplate.saveBean(Constants.getUserTypeKey(accessToken), UserTypeEnum.MACHINE, Constants.tokenExpireTime());
            return result;
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private TSingleResult<MachineVO> login(MachineLoginParam param) {
        MachineParam machineParam = new MachineParam();
        machineParam.setEnterpriseId(Operator.getEnterpriseId());
        machineParam.setMachineKey(param.getMachineKey());
        machineParam.setMacAddress(param.getMacAddress());
        QueryRequest<MachineParam> request = QueryRequest.build();
        request.setParam(machineParam);
        TSingleResult<MachineDTO> result = client.login(request);
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }
}
