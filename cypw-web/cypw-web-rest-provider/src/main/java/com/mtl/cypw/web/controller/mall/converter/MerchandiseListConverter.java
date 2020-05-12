package com.mtl.cypw.web.controller.mall.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.mall.dto.MerchandiseDTO;
import com.mtl.cypw.web.controller.mall.vo.GoodsVO;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 10:22
 */
@Component
public class MerchandiseListConverter extends DataConverter<MerchandiseDTO, GoodsVO> {

    @Override
    public GoodsVO convert(MerchandiseDTO input) {
        if (input == null) {
            return null;
        }
        GoodsVO vo = new GoodsVO();
        vo.setGoodsId(input.getMerchandiseId());
        vo.setGoodsName(input.getMerchandiseName());
        vo.setGoodsCode(input.getMerchandiseCode());
        vo.setGoodsImage(input.getMerchandiseImage());
        vo.setBeginDate(input.getBeginDate());
        vo.setEndDate(input.getEndDate());
        vo.setBottomPrice(input.getBottomPrice());
        vo.setLimitCnt(input.getMerchandiseLimitCnt());
        vo.setSaleable(input.getSaleable());
        vo.setSortOrder(input.getSortOrder());
        return vo;
    }

}
