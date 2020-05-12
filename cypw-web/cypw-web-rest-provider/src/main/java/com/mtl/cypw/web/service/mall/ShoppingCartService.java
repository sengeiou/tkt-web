package com.mtl.cypw.web.service.mall;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.utils.DateUtils;
import com.mtl.cypw.api.mall.client.ShoppingCartApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.domain.mall.dto.ShoppingCartDTO;
import com.mtl.cypw.domain.mall.param.ShoppingCartOperateParam;
import com.mtl.cypw.domain.mall.param.ShoppingCartParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.mall.converter.ShoppingCartConverter;
import com.mtl.cypw.web.controller.mall.param.ShoppingParam;
import com.mtl.cypw.web.controller.mall.param.ShoppingRemoveParam;
import com.mtl.cypw.web.controller.mall.vo.ShoppingCartVO;
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
public class ShoppingCartService {

    @Autowired
    private ShoppingCartApiClient shoppingCartApiClient;

    @Autowired
    private ShoppingCartConverter shoppingCartConverter;

    public TMultiResult<ShoppingCartVO> findShoppingCartListByMemberId(Integer memberId) {
        TMultiResult<ShoppingCartDTO> result = shoppingCartApiClient.findByUserId(new IdRequest(String.valueOf(memberId)));
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return ResultBuilder.succTMulti(Collections.EMPTY_LIST);
        }
        List<ShoppingCartDTO> dtoList = result.getData();
        List<ShoppingCartVO> voList = shoppingCartConverter.batchConvert(dtoList);
        return ResultBuilder.succTMulti(voList);
    }

    public TSingleResult<ShoppingCartVO> add(ShoppingParam param) {
        GenericRequest genericRequest = new GenericRequest();
        ShoppingCartParam shoppingCartParam = new ShoppingCartParam();
        shoppingCartParam.setHasCover(false);
        shoppingCartParam.setSkuType(param.getSkuType());
        shoppingCartParam.setSkuId(param.getSkuId());
        shoppingCartParam.setProductId(param.getGoodsId());
        shoppingCartParam.setQuantity(param.getQuantity());
        shoppingCartParam.setMemberId(Operator.getMemberId());
        shoppingCartParam.setEnterpriseId(Operator.getEnterpriseId());
        genericRequest.setParam(shoppingCartParam);
        log.info("shopping add, params:{}", JsonUtils.toJson(genericRequest));
        TSingleResult<ShoppingCartDTO> result = shoppingCartApiClient.addGoodsToShoppingCart(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        ShoppingCartVO vo = shoppingCartConverter.convert(result.getData());
        return ResultBuilder.succTSingle(vo);
    }

    public TSingleResult<ShoppingCartVO> update(ShoppingParam param) {
        GenericRequest genericRequest = new GenericRequest();
        ShoppingCartParam shoppingCartParam = new ShoppingCartParam();
        shoppingCartParam.setHasCover(true);
        shoppingCartParam.setSkuType(param.getSkuType());
        shoppingCartParam.setSkuId(param.getSkuId());
        shoppingCartParam.setProductId(param.getGoodsId());
        shoppingCartParam.setQuantity(param.getQuantity());
        shoppingCartParam.setMemberId(Operator.getMemberId());
        shoppingCartParam.setEnterpriseId(Operator.getEnterpriseId());
        genericRequest.setParam(shoppingCartParam);
        log.info("shopping update, params:{}", JsonUtils.toJson(genericRequest));
        TSingleResult<ShoppingCartDTO> result = shoppingCartApiClient.updateQuantityBySkuId(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        ShoppingCartVO vo = shoppingCartConverter.convert(result.getData());
        return ResultBuilder.succTSingle(vo);
    }

    public TSingleResult<Integer> remove(ShoppingRemoveParam param) {
        if (Operator.getMemberId() == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_USER_NOT_ENABLE.getCode(), ErrorCode.ERROR_COMMON_USER_NOT_ENABLE.getMsg());
        }
        GenericRequest genericRequest = new GenericRequest();
        ShoppingCartOperateParam operateParam = new ShoppingCartOperateParam();
        operateParam.setSkuType(param.getSkuType());
        operateParam.setMemberId(Operator.getMemberId());
        operateParam.setSkuIds(param.getSkuIds());
        genericRequest.setParam(operateParam);
        log.info("shopping remove, params:{}", JsonUtils.toJson(genericRequest));
        TSingleResult<Integer> result = shoppingCartApiClient.removeGoodsFromShoppingCart(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        return ResultBuilder.succTSingle(result.getData());
    }

    public TSingleResult<Integer> empty(Integer memberId) {
        if (memberId == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_USER_NOT_ENABLE.getCode(), ErrorCode.ERROR_COMMON_USER_NOT_ENABLE.getMsg());
        }
        log.info("shopping empty, params:{},{}", memberId, DateUtils.now());
        TSingleResult<Integer> result = shoppingCartApiClient.emptyByUserId(new IdRequest(String.valueOf(memberId)));
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        return ResultBuilder.succTSingle(result.getData());
    }

}
