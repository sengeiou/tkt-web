package com.mtl.cypw.test;

import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.web.controller.mall.converter.ShoppingDeliveryConverter;
import com.mtl.cypw.web.service.mpm.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-11 12:02
 */
@Slf4j
public class MallTest extends BaseTest {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private ShoppingDeliveryConverter shoppingDeliveryConverter;


    @Test
    public void testDelivery() {
        EnterpriseDTO dto = enterpriseService.getEnterpriseInfo(7);
        if (dto != null) {
            log.info(JsonUtils.toJson(shoppingDeliveryConverter.batchConvert(dto.getDelivers())));
        }
    }
}
