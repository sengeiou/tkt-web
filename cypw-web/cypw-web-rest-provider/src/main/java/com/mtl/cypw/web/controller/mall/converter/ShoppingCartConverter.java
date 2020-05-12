package com.mtl.cypw.web.controller.mall.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.mall.dto.ShoppingCartDTO;
import com.mtl.cypw.web.controller.mall.vo.ShoppingCartVO;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 10:22
 */
@Component
public class ShoppingCartConverter extends DataConverter<ShoppingCartDTO, ShoppingCartVO> {

    @Override
    public ShoppingCartVO convert(ShoppingCartDTO input) {
        if (input == null) {
            return null;
        }
        ShoppingCartVO vo = new ShoppingCartVO();
        vo.setShoppingCartId(input.getShoppingCartId());
        vo.setGoodsId(input.getProductId());
        vo.setGoodsName(input.getProductName());
        vo.setGoodsImgSrc(input.getProductImgSrc());
        vo.setMemberId(input.getMemberId());
        vo.setSkuId(input.getSkuId());
        vo.setSkuName(input.getSkuName());
        vo.setSkuType(input.getSkuType());
        vo.setUnitPrice(input.getUnitPrice());
        vo.setOriginalPrice(input.getOriginalPrice());
        vo.setQuantity(input.getQuantity());
        vo.setLimitCnt(input.getLimitCnt());
        vo.setEnable(BooleanUtils.toBooleanObject(input.getStatus()));
        return vo;
    }

}
