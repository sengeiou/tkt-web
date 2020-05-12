package com.mtl.cypw.web.controller.mall.converter;

import com.juqitech.converter.DataConverter;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDeliveryDTO;
import com.mtl.cypw.web.controller.mall.vo.ShoppingDeliveryVO;
import org.springframework.stereotype.Component;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-10 14:16
 */
@Component
public class ShoppingDeliveryConverter extends DataConverter<EnterpriseDeliveryDTO, ShoppingDeliveryVO> {

    @Override
    public ShoppingDeliveryVO convert(EnterpriseDeliveryDTO input) {
        if (input == null) {
            return null;
        }
        ShoppingDeliveryVO vo = new ShoppingDeliveryVO();
        vo.setDeliverType(input.getDeliverType().getName());
        vo.setTips(input.getTips());
        vo.setExpressFee(input.getExpressFee());
        vo.setFreeShippingRestrict(input.getFreeShippingRestrict());
        vo.setContactMobile(input.getContactMobile());
        vo.setFetchAddress(input.getFetchAddress());
        vo.setFetchTimeDesc(input.getFetchTimeDesc());
        return vo;
    }

}
