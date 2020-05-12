package com.mtl.cypw.web.controller.mall.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.mall.dto.MerchandiseDTO;
import com.mtl.cypw.web.controller.mall.vo.GoodsDetailVO;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-05 10:22
 */
@Component
public class MerchandiseDetailConverter extends DataConverter<MerchandiseDTO, GoodsDetailVO> {

    @Override
    public GoodsDetailVO convert(MerchandiseDTO input) {
        if (input == null) {
            return null;
        }
        GoodsDetailVO vo = new GoodsDetailVO();
        vo.setGoodsId(input.getMerchandiseId());
        vo.setGoodsName(input.getMerchandiseName());
        vo.setGoodsCode(input.getMerchandiseCode());
        vo.setGoodsImage(input.getMerchandiseImage());
        vo.setGoodsBrief(input.getMerchandiseBrief());
        vo.setBeginDate(input.getBeginDate());
        vo.setEndDate(input.getEndDate());
        vo.setBottomPrice(input.getBottomPrice());
        vo.setLimitCnt(input.getMerchandiseLimitCnt());
        vo.setSaleable(input.getSaleable());
        vo.setSortOrder(input.getSortOrder());
        vo.setPurchaseNotice(input.getPurchaseNotice());
        vo.setContent(input.getMerchandiseContent());
        return vo;
    }

}
