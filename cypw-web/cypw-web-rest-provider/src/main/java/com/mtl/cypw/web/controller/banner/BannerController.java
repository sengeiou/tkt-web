package com.mtl.cypw.web.controller.banner;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.enums.CommonStateEnum;
import com.mtl.cypw.domain.mpm.enums.BannerTypeEnum;
import com.mtl.cypw.domain.mpm.param.BannerQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.banner.vo.BannerViewVO;
import com.mtl.cypw.web.service.mpm.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-18 15:49
 */
@RestController
@Api(tags = {"02-C端-首页&商户信息"})
@Slf4j
@CrossOrigin
public class BannerController extends WebGenericController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/")
    public TSingleResult<Void> index() {
        return ResultBuilder.succTSingle(null);
    }

    @ApiOperation(value = "Banner 列表", httpMethod = "GET", response = BannerViewVO.class)
    @GetMapping("/pub/v1/banners")
    public TMultiResult<BannerViewVO> banners() {
        BannerQueryParam query = new BannerQueryParam();
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setCommonState(CommonStateEnum.VALID);
        query.setTypeEnum(BannerTypeEnum.SHOWS);
        QueryRequest<BannerQueryParam> requestParam = QueryRequest.build();
        requestParam.setParam(query);
        return bannerService.searchBannerList(requestParam);
    }
}