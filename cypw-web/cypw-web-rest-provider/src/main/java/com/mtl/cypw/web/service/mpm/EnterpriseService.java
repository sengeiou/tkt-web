package com.mtl.cypw.web.service.mpm;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.mpm.client.EnterpriseApiClient;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.domain.mpm.constant.CacheConstant;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.domain.mpm.param.EnterpriseQueryParam;
import com.mtl.cypw.web.controller.mpm.converter.EnterpriseConverter;
import com.mtl.cypw.web.controller.mpm.vo.EnterpriseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/26.
 */
@Service
@Slf4j
public class EnterpriseService {

    @Autowired
    EnterpriseApiClient enterpriseApiClient;

    @Autowired
    EnterpriseConverter enterpriseConverter;

    @Autowired
    CommonRedisTemplate redisTemplate;

    public TSingleResult<EnterpriseVO> getEnterprise(QueryRequest<EnterpriseQueryParam> request) {
        log.debug("查询企业信息，request：{}", JSONObject.toJSONString(request));
//        EnterpriseDTO dto = getCacheEnterpriseInfo(request.getParam());
//        if (dto == null) {
//            TSingleResult<EnterpriseDTO> result = enterpriseApiClient.getEnterprise(request);
//            dto = result.getData();
//            cacheEnterpriseInfo(dto);
//        }
//        log.debug("查询结果，response：{}", JSONObject.toJSONString(dto));
//        return ResultBuilder.succTSingle(enterpriseConverter.toVo(dto));
        TSingleResult<EnterpriseDTO> result = enterpriseApiClient.getEnterprise(request);
        if (result.success()) {
            EnterpriseDTO dto = result.getData();
            cacheEnterpriseInfo(dto);
            return enterpriseConverter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    private void cacheEnterpriseInfo(EnterpriseDTO dto) {
        log.info("存缓存");
        if (dto != null) {
            if (dto.getEnterpriseId() != null) {
                redisTemplate.saveBean(CacheConstant.getEnterpriseKeyById(dto.getEnterpriseId()), dto, 3600);
            }
            if (StringUtils.isNotEmpty(dto.getTenantId())) {
                redisTemplate.saveBean(CacheConstant.getEnterpriseKeyByTenanId(dto.getTenantId()), dto, 3600);
            }
            if (StringUtils.isNoneEmpty(dto.getDomainName())) {
                redisTemplate.saveBean(CacheConstant.getEnterpriseKeyByDomain(dto.getDomainName()), dto, 3600);
            }
        }
    }

    private EnterpriseDTO getCacheEnterpriseInfo(EnterpriseQueryParam param) {
        log.info("取缓存");
        EnterpriseDTO dto = null;
        if (param != null) {
            if (param.getEnterpriseId() != null) {
                dto = redisTemplate.getBean(CacheConstant.getEnterpriseKeyById(param.getEnterpriseId()), EnterpriseDTO.class);
            } else if (StringUtils.isNotEmpty(param.getTenantId())) {
                dto = redisTemplate.getBean(CacheConstant.getEnterpriseKeyByTenanId(param.getTenantId()), EnterpriseDTO.class);
            } else if (StringUtils.isNoneEmpty(param.getDomainName())) {
                dto = redisTemplate.getBean(CacheConstant.getEnterpriseKeyByDomain(param.getDomainName()), EnterpriseDTO.class);
            }
        }
        return dto;
    }

    public EnterpriseDTO getCacheEnterpriseInfo(Integer enterpriseId) {
        log.info("取缓存");
        EnterpriseDTO dto = null;
        if (enterpriseId != null) {
            dto = redisTemplate.getBean(CacheConstant.getEnterpriseKeyById(enterpriseId), EnterpriseDTO.class);
            if (dto == null) {
                QueryRequest<EnterpriseQueryParam> request = new QueryRequest<>();
                EnterpriseQueryParam param = new EnterpriseQueryParam();
                param.setEnterpriseId(enterpriseId);
                request.setParam(param);
                TSingleResult<EnterpriseDTO> result = enterpriseApiClient.getEnterprise(request);
                if (result.success()) {
                    dto = result.getData();
                    cacheEnterpriseInfo(dto);
                }
            }
        }
        return dto;
    }

    public EnterpriseDTO getEnterpriseInfo(String enterpriseAbbr) {
        EnterpriseDTO dto = null;
        if (StringUtils.isNotEmpty(enterpriseAbbr)) {
            if (dto == null) {
                QueryRequest<EnterpriseQueryParam> request = new QueryRequest<>();
                EnterpriseQueryParam param = new EnterpriseQueryParam();
                param.setEnterpriseAbbr(enterpriseAbbr);
                request.setParam(param);
                TSingleResult<EnterpriseDTO> result = enterpriseApiClient.getEnterprise(request);
                if (result.success()) {
                    dto = result.getData();
                }
            }
        }
        return dto;
    }

    public EnterpriseDTO getEnterpriseInfo(Integer enterpriseId) {
        EnterpriseDTO dto = null;
        QueryRequest<EnterpriseQueryParam> request = new QueryRequest<>();
        EnterpriseQueryParam param = new EnterpriseQueryParam();
        param.setEnterpriseId(enterpriseId);
        request.setParam(param);
        TSingleResult<EnterpriseDTO> result = enterpriseApiClient.getEnterprise(request);
        if (result.success() && result.getData() != null) {
            dto = result.getData();
        }
        return dto;
    }


}
