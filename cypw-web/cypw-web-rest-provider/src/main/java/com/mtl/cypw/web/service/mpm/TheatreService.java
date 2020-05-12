package com.mtl.cypw.web.service.mpm;

import com.juqitech.request.IdRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.mpm.client.TheatreApiClient;
import com.mtl.cypw.domain.mpm.dto.TheatreDTO;
import com.mtl.cypw.domain.mpm.param.TheatreQueryParam;
import com.mtl.cypw.web.controller.mpm.converter.TheatreConverter;
import com.mtl.cypw.web.controller.mpm.vo.TheatreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Slf4j
@Service
public class TheatreService {

    @Resource
    TheatreApiClient client;

    @Resource
    TheatreConverter converter;

    public TSingleResult<TheatreVO> getTheatreById(Integer theatreId) {
        IdRequest request = new IdRequest();
        request.setId(theatreId.toString());
        TSingleResult<TheatreDTO> result = client.getTheatreById(request);
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    public TMultiResult<TheatreVO> getTheatreList(List<Integer> theatreIds) {
        TheatreQueryParam queryParam = new TheatreQueryParam();
        queryParam.setTheatreIds(theatreIds);
        QueryRequest<TheatreQueryParam> request = QueryRequest.build();
        request.setParam(queryParam);
        TMultiResult<TheatreDTO> result = client.getTheatreList(request);
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTMulti(result.getStatusCode(), result.getComments());
        }
    }
}
