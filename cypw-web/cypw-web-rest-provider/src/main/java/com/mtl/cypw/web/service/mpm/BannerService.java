package com.mtl.cypw.web.service.mpm;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.mpm.client.BannerApiClient;
import com.mtl.cypw.domain.mpm.dto.BannerDTO;
import com.mtl.cypw.domain.mpm.param.BannerQueryParam;
import com.mtl.cypw.web.controller.banner.converter.BannerConverter;
import com.mtl.cypw.web.controller.banner.vo.BannerViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@Slf4j
@Service
public class BannerService {

    @Autowired
    BannerApiClient client;

    @Autowired
    BannerConverter converter;

    public TMultiResult<BannerViewVO> searchBannerList(QueryRequest<BannerQueryParam> request) {
        TMultiResult<BannerDTO> pageDto = client.pageQuery(request);
        return converter.toVo(pageDto);
    }
}
