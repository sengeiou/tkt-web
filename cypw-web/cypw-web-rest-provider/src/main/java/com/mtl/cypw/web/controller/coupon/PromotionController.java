package com.mtl.cypw.web.controller.coupon;

import com.juqitech.response.TMultiResult;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import com.mtl.cypw.web.service.coupon.PromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/12/4.
 */
@RestController
@Slf4j
@Api(value = "票星球-优惠券", tags = {"票星球-优惠券接口"})
@CrossOrigin
public class PromotionController extends WebGenericController {

    @Resource
    PromotionService promotionService;

    @RequestMapping(value = "/pub/v1/promotion/coupon", method = RequestMethod.GET)
    @ApiOperation(value = "查询优惠券列表", httpMethod = "GET", response = PromotionVO.class, notes = "查询优惠券列表")
    public TMultiResult<PromotionVO> searchPromotionList(
            @ApiParam(required = true, name = "businessType", value = "业务类型(0：全部，1：票品类，2：衍生品类)") @RequestParam(defaultValue = "0") Integer businessType) {
        return promotionService.searchPromotionList(Operator.getEnterpriseId(), businessType);
    }

}
