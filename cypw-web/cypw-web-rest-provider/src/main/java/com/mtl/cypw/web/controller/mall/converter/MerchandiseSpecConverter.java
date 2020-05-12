package com.mtl.cypw.web.controller.mall.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.mall.dto.MerchandiseSpecDTO;
import com.mtl.cypw.web.controller.mall.vo.GoodsSpecVO;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 10:22
 */
@Component
public class MerchandiseSpecConverter extends DataConverter<MerchandiseSpecDTO, GoodsSpecVO> {

    @Override
    public GoodsSpecVO convert(MerchandiseSpecDTO input) {
        if (input == null) {
            return null;
        }
        GoodsSpecVO vo = new GoodsSpecVO();
        vo.setGoodsId(input.getMerchandiseId());
        vo.setSkuId(input.getPriceId());
        vo.setPriceTitle(input.getPriceTitle());
        vo.setPriceValue(input.getPriceValue());
        vo.setPriceOrigin(input.getPriceOrigin());
        vo.setImageSrc(input.getImageSrc());
        vo.setMinQty(input.getMinQty());
        vo.setStockQty(input.getStockQty());
        vo.setEnable(BooleanUtils.toBooleanObject(input.getIsEnable()));
        return vo;
    }

}
