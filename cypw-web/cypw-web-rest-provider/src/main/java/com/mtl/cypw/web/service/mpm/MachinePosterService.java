package com.mtl.cypw.web.service.mpm;

import com.juqitech.request.IdRequest;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.mpm.client.MachinePosterApiClient;
import com.mtl.cypw.domain.mpm.dto.MachinePosterDTO;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.mpm.converter.MachinePosterConverter;
import com.mtl.cypw.web.controller.mpm.vo.MachinePosterVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/16.
 */
@Service
public class MachinePosterService {

    @Resource
    private MachinePosterApiClient machinePosterApiClient;

    @Resource
    private MachinePosterConverter machinePosterConverter;

    public TMultiResult<MachinePosterVO> searchMachinePoster() {
        IdRequest request = new IdRequest(Operator.getEnterpriseId().toString());
        TMultiResult<MachinePosterDTO> dtoList = machinePosterApiClient.searchMachinePoster(request);
        return machinePosterConverter.toVo(dtoList);
    }
}
