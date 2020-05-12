package com.mtl.cypw.web.controller.order;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.enums.PlatformSource;
import com.juqitech.service.utils.CommonUtil;
import com.mtl.cypw.domain.order.enums.ChannelEnum;
import com.mtl.cypw.domain.order.enums.OrderCancelEnum;
import com.mtl.cypw.domain.order.enums.OrderStatusEnum;
import com.mtl.cypw.domain.order.param.BindExpressNoParam;
import com.mtl.cypw.domain.order.param.OrderCancelParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.order.enums.OrderStateEnum;
import com.mtl.cypw.web.controller.order.param.CreateOrderParam;
import com.mtl.cypw.web.controller.order.vo.OrderVO;
import com.mtl.cypw.web.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 10:01
 */
@RestController
@Api(tags = {"06-C端-下单&支付页"})
@Slf4j
@CrossOrigin
public class OrderController extends WebGenericController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", httpMethod = "POST", response = Integer.class)
    @PostMapping("/buyer/v1/order/create")
    public TSingleResult<Integer> create(HttpServletRequest request,
                                         @ApiParam("创建订单参数") @RequestBody CreateOrderParam param) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        param.setMemberId(Operator.getMemberId());
        param.setClientIp(CommonUtil.getClientIP(request));
        param.setTenantId(Operator.getEnterpriseId());
        param.setSrc(PlatformSource.M_WEB);
        param.setChannel(ChannelEnum.STORE);
        TSingleResult<Integer> result = orderService.create(param);
        return result;
    }

    @ApiOperation(value = "获取订单详情", httpMethod = "GET", response = OrderVO.class)
    @GetMapping("/buyer/v1/order/{orderId}")
    public TSingleResult<OrderVO> detail(
            @ApiParam("订单ID") @PathVariable Integer orderId) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        IdRequest request = new IdRequest(String.valueOf(orderId));
        return orderService.detail(request);
    }

    @ApiOperation(value = "获取订单列表", httpMethod = "GET", response = OrderVO.class)
    @GetMapping("/buyer/v1/order/list")
    public TPageResult<OrderVO> pageList(
            @ApiParam("页码") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @ApiParam("订单状态") @RequestParam(value = "orderStateEnum", required = false) OrderStateEnum orderStateEnum) {

        //TODO: 统一权限验证（登录会话、商户权限等...）

        return orderService.pageQuery(pageNo, pageSize, orderStateEnum);
    }

    @ApiOperation(value = "查询订单状态", httpMethod = "GET", response = OrderStatusEnum.class, notes = "订单状态枚举")
    @GetMapping(value = "/buyer/v1/order/{orderId}/status")
    public TSingleResult<OrderStatusEnum> queryStatus(
            @ApiParam("订单ID") @PathVariable("orderId") Integer orderId) {
        //TODO: 统一权限验证（登录会话、商户权限等...）
        return ResultBuilder.succTSingle(OrderStatusEnum.SUCCEED);
    }

    @ApiOperation(value = "取消指定订单", httpMethod = "POST", response = Boolean.class, notes = "只能需要未支付订单")
    @PostMapping("/buyer/v1/order/{orderId}/cancel")
    public TSingleResult<Boolean> cancel(
            @ApiParam("订单ID") @PathVariable("orderId") Integer orderId) {

        //TODO: 统一权限验证（登录会话、商户权限等...）
        OrderCancelParam param = OrderCancelParam.builder()
                .orderId(orderId)
                .validationUser(true)
                .userId(Operator.getMemberId())
                .cancelEnum(OrderCancelEnum.USER_CANCEL)
                .build();
        GenericRequest<OrderCancelParam> request = new GenericRequest<>();
        request.setParam(param);
        return orderService.cancel(request);
    }

    @ApiOperation(value = "确认收货", httpMethod = "POST", response = Boolean.class, notes = "支付成功未确认收货的订单")
    @PostMapping("/buyer/v1/order/{orderId}/confirmReceipt")
    public TSingleResult<Boolean> confirmReceipt(
            @ApiParam("订单ID") @PathVariable("orderId") Integer orderId) {
        IdRequest request = new IdRequest();
        request.setId(orderId.toString());
        return orderService.confirmReceipt(request);
    }

    @ApiOperation(value = "绑定物流单号", httpMethod = "POST", response = Boolean.class, notes = "绑定待收货的订单")
    @PostMapping("/buyer/v1/order/bindExpressNo")
    public TSingleResult<Boolean> bindExpressNo(
            @ApiParam("绑定物流单号参数") @RequestBody BindExpressNoParam param) {
        return orderService.bindExpressNo(param);
    }

}
