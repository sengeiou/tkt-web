package com.mtl.cypw.web.controller.mall;

import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.mall.param.ShoppingParam;
import com.mtl.cypw.web.controller.mall.param.ShoppingRemoveParam;
import com.mtl.cypw.web.controller.mall.vo.ShoppingCartVO;
import com.mtl.cypw.web.service.mall.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnathon.Yuan
 * @date 2019-03-05 10:23
 */
@RestController
@Api(tags = {"09-衍生品商城-用户购物车"})
@Slf4j
@CrossOrigin
public class ShoppingCartController extends WebGenericController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation(value = "查询购物车@列表", httpMethod = "GET", response = ShoppingCartVO.class)
    @GetMapping("/buyer/v1/shopping/list")
    public TMultiResult<ShoppingCartVO> list() {
        return shoppingCartService.findShoppingCartListByMemberId(Operator.getMemberId());
    }

    @ApiOperation(value = "操作购物车@添加", httpMethod = "POST", response = ShoppingCartVO.class)
    @PostMapping("/buyer/v1/shopping/add")
    public TSingleResult<ShoppingCartVO> add(@ApiParam("添加购物车参数") @RequestBody ShoppingParam param) {
        return shoppingCartService.add(param);
    }

    @ApiOperation(value = "操作购物车@修改", httpMethod = "POST", response = ShoppingCartVO.class)
    @PostMapping("/buyer/v1/shopping/update")
    public TSingleResult<ShoppingCartVO> update(@ApiParam("修改购物车参数") @RequestBody ShoppingParam param) {
        return shoppingCartService.update(param);
    }

    @ApiOperation(value = "操作购物车@批量移除", httpMethod = "POST", response = Integer.class)
    @PostMapping("/buyer/v1/shopping/remove")
    public TSingleResult<Integer> remove(@ApiParam("移除购物车参数") @RequestBody ShoppingRemoveParam param) {
        return shoppingCartService.remove(param);
    }

    @ApiOperation(value = "操作购物车@清空", httpMethod = "POST", response = Integer.class)
    @PostMapping("/buyer/v1/shopping/empty")
    public TSingleResult<Integer> empty() {
        return shoppingCartService.empty(Operator.getMemberId());
    }

}
