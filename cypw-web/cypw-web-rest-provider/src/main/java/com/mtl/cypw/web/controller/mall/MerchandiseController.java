package com.mtl.cypw.web.controller.mall;

import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.enums.CommonStateEnum;
import com.mtl.cypw.domain.mpm.enums.BannerTypeEnum;
import com.mtl.cypw.domain.mpm.param.BannerQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.banner.vo.BannerViewVO;
import com.mtl.cypw.web.controller.mall.vo.GoodsDetailVO;
import com.mtl.cypw.web.controller.mall.vo.GoodsSpecVO;
import com.mtl.cypw.web.controller.mall.vo.GoodsVO;
import com.mtl.cypw.web.controller.mall.vo.ShoppingDeliveryVO;
import com.mtl.cypw.web.service.mall.MerchandiseService;
import com.mtl.cypw.web.service.mpm.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnathon.Yuan
 * @date 2019-03-05 10:23
 */
@RestController
@Api(tags = {"08-衍生品商城-商品信息"})
@Slf4j
@CrossOrigin
public class MerchandiseController extends WebGenericController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private MerchandiseService merchandiseService;

    @ApiOperation(value = "查询衍生品@轮播图", httpMethod = "GET", response = BannerViewVO.class)
    @GetMapping("/pub/v1/mall/banners")
    public TMultiResult<BannerViewVO> queryBanners() {
        BannerQueryParam query = new BannerQueryParam();
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setCommonState(CommonStateEnum.VALID);
        query.setTypeEnum(BannerTypeEnum.GOODS);
        QueryRequest<BannerQueryParam> requestParam = QueryRequest.build();
        requestParam.setParam(query);
        return bannerService.searchBannerList(requestParam);
    }

    @ApiOperation(value = "查询衍生品@配送方式", httpMethod = "GET", response = BannerViewVO.class)
    @GetMapping("/pub/v1/mall/delivery")
    public TMultiResult<ShoppingDeliveryVO> queryDelivery() {
        return merchandiseService.getDelivery();
    }

    @ApiOperation(value = "查询衍生品@分页列表", httpMethod = "GET", response = GoodsVO.class)
    @GetMapping("/pub/v1/mall/pageQuery")
    public TPageResult<GoodsVO> pageQuery(
            @ApiParam("页码") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @ApiParam("商品类别") @RequestParam(value = "goodsType", required = false) Integer goodsType) {
        return merchandiseService.pageQuery(pageNo, pageSize, goodsType);
    }

    @ApiOperation(value = "查询衍生品@详情", httpMethod = "GET", response = GoodsDetailVO.class)
    @GetMapping("/pub/v1/mall/{goodsId}")
    public TSingleResult<GoodsDetailVO> detail(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                               @PathVariable("goodsId") Integer goodsId) {
        return merchandiseService.getDetail(goodsId);
    }

    @ApiOperation(value = "查询衍生品@规格列表", httpMethod = "GET", response = GoodsSpecVO.class)
    @GetMapping("/pub/v1/mall/{goodsId}/specs")
    public TMultiResult<GoodsSpecVO> specs(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                               @PathVariable("goodsId") Integer goodsId) {
        return merchandiseService.getSpecs(goodsId);
    }

}
