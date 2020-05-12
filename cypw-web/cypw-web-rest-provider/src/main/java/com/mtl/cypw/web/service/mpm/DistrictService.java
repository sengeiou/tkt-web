package com.mtl.cypw.web.service.mpm;

import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.mpm.client.DistrictApiClient;
import com.mtl.cypw.domain.mpm.dto.DistrictDTO;
import com.mtl.cypw.web.controller.mpm.converter.DistrictConverter;
import com.mtl.cypw.web.controller.mpm.vo.DistrictVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Service
@Slf4j
public class DistrictService {

    @Autowired
    DistrictApiClient districtApiClient;

    @Autowired
    DistrictConverter districtConverter;

    public TMultiResult<DistrictVO> districts() {
        log.debug("查询所有省份");
        TMultiResult<DistrictDTO> dto = districtApiClient.districts();
        return districtConverter.toVo(dto);
    }

}
