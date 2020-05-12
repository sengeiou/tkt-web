package com.mtl.cypw.web.service.mpm;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.mpm.client.CityApiClient;
import com.mtl.cypw.domain.mpm.dto.CityDTO;
import com.mtl.cypw.web.controller.mpm.converter.CityConverter;
import com.mtl.cypw.web.controller.mpm.vo.CityVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Service
@Slf4j
public class CityService {

    @Autowired
    CityApiClient cityApiClient;

    @Autowired
    CityConverter cityConverter;

    public TMultiResult<CityVO> citys() {
        TMultiResult<CityDTO> dto = cityApiClient.citys();
        return cityConverter.toVo(dto);
    }

}
