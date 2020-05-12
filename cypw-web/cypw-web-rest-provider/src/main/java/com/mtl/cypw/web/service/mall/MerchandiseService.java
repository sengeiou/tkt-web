package com.mtl.cypw.web.service.mall;

import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.mall.client.MerchandiseApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.domain.mall.dto.MerchandiseDTO;
import com.mtl.cypw.domain.mall.dto.MerchandiseSpecDTO;
import com.mtl.cypw.domain.mall.param.MerchandiseQueryParam;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.mall.converter.MerchandiseDetailConverter;
import com.mtl.cypw.web.controller.mall.converter.MerchandiseListConverter;
import com.mtl.cypw.web.controller.mall.converter.MerchandiseSpecConverter;
import com.mtl.cypw.web.controller.mall.converter.ShoppingDeliveryConverter;
import com.mtl.cypw.web.controller.mall.vo.GoodsDetailVO;
import com.mtl.cypw.web.controller.mall.vo.GoodsSpecVO;
import com.mtl.cypw.web.controller.mall.vo.GoodsVO;
import com.mtl.cypw.web.controller.mall.vo.ShoppingDeliveryVO;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 10:22
 */
@Slf4j
@Service
public class MerchandiseService {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private MerchandiseApiClient merchandiseApiClient;

    @Autowired
    private MerchandiseSpecConverter merchandiseSpecConverter;

    @Autowired
    private MerchandiseListConverter merchandiseListConverter;

    @Autowired
    private MerchandiseDetailConverter merchandiseDetailConverter;

    @Autowired
    private ShoppingDeliveryConverter shoppingDeliveryConverter;

    public TMultiResult<ShoppingDeliveryVO> getDelivery() {
        EnterpriseDTO dto = enterpriseService.getEnterpriseInfo(Operator.getEnterpriseId());
        if (dto == null || CollectionUtils.isEmpty(dto.getDelivers())) {
            return ResultBuilder.succTMulti(Collections.emptyList());
        }
        return ResultBuilder.succTMulti(shoppingDeliveryConverter.batchConvert(dto.getDelivers()));
    }


    public TPageResult<GoodsVO> pageQuery(Integer pageNo, Integer pageSize, Integer goodsType) {
        MerchandiseQueryParam param = new MerchandiseQueryParam();
        param.setTypeId(goodsType);
        param.setEnterpriseId(Operator.getEnterpriseId());
        QueryRequest request = new QueryRequest();
        PaginationParam pagination = new PaginationParam();
        pagination.setOffset(pageNo);
        pagination.setLength(pageSize);
        request.setPagination(pagination);
        request.setParam(param);
        TPageResult<MerchandiseDTO> result = merchandiseApiClient.pageQueryGoodsByEnterprise(request);
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return ResultBuilder.succTPage(Collections.EMPTY_LIST, result.getPagination());
        }
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        List<GoodsVO> voList = merchandiseListConverter.batchConvert(result.getData());
        return ResultBuilder.succTPage(voList, result.getPagination());
    }

    public TSingleResult<GoodsDetailVO> getDetail(Integer goodsId) {
        MerchandiseQueryParam param = new MerchandiseQueryParam();
        param.setMerchandiseId(goodsId);
        param.setEnterpriseId(Operator.getEnterpriseId());
        QueryRequest request = new QueryRequest();
        request.setParam(param);
        TSingleResult<MerchandiseDTO> result = merchandiseApiClient.findGoodsDetailByMerchandiseId(request);
        if (result == null || result.getData() == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getCode(), ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getMsg());
        }
        GoodsDetailVO detailVO = merchandiseDetailConverter.convert(result.getData());
        return ResultBuilder.succTSingle(detailVO);
    }

    public TMultiResult<GoodsSpecVO> getSpecs(Integer goodsId) {
        MerchandiseQueryParam param = new MerchandiseQueryParam();
        param.setMerchandiseId(goodsId);
        param.setEnterpriseId(Operator.getEnterpriseId());
        QueryRequest request = new QueryRequest();
        request.setParam(param);
        TMultiResult<MerchandiseSpecDTO> result = merchandiseApiClient.findGoodsSpecByMerchandiseId(request);
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return ResultBuilder.succTMulti(Collections.EMPTY_LIST);
        }
        List<GoodsSpecVO> voList = merchandiseSpecConverter.batchConvert(result.getData());
        return ResultBuilder.succTMulti(voList);
    }
}
