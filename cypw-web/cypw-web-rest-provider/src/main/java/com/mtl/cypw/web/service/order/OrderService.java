package com.mtl.cypw.web.service.order;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.order.client.OrderApiClient;
import com.mtl.cypw.api.order.client.OrderQueryApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.domain.order.dto.OrderDTO;
import com.mtl.cypw.domain.order.param.BindExpressNoParam;
import com.mtl.cypw.domain.order.param.OrderCancelParam;
import com.mtl.cypw.domain.order.param.OrderQueryParam;
import com.mtl.cypw.domain.order.param.UserCreateOrderParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.order.converter.CreateOrderConverter;
import com.mtl.cypw.web.controller.order.converter.OrderConverter;
import com.mtl.cypw.web.controller.order.enums.OrderStateEnum;
import com.mtl.cypw.web.controller.order.param.CreateOrderParam;
import com.mtl.cypw.web.controller.order.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 10:22
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderApiClient orderApiClient;

    @Autowired
    private OrderQueryApiClient orderQueryApiClient;

    @Autowired
    private CreateOrderConverter createOrderConverter;

    @Autowired
    private OrderConverter orderConverter;

    public TSingleResult<Integer> create(CreateOrderParam request) {
        log.info("创建订单，request：{}", JsonUtils.toJson(request));
        UserCreateOrderParam createOrderParam = createOrderConverter.convert(request);
        GenericRequest genericRequest = new GenericRequest();
        genericRequest.setParam(createOrderParam);
        return orderApiClient.create(genericRequest);
    }

    public TSingleResult<OrderVO> detail(IdRequest request) {
        log.info("查询订单详情，request：{}", JsonUtils.toJson(request));
        TSingleResult<OrderDTO> result = orderQueryApiClient.findById(request);
        if (result.getData() == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getCode(), ErrorCode.ERROR_COMMON_DATA_NOT_FOUND.getMsg());
        }
        return ResultBuilder.succTSingle(orderConverter.convert(result.getData()));
    }

    public TPageResult<OrderVO> pageQuery(Integer pageNo, Integer pageSize, OrderStateEnum orderStateEnum) {
        log.info("查询订单列表，pageNo：{}，pageSize:{},orderStateEnum:{}", pageNo, pageSize, orderStateEnum);
        QueryRequest request = QueryRequest.build();
        PaginationParam pagination = new PaginationParam();
        pagination.setOffset(pageNo);
        pagination.setLength(pageSize);
        request.setPagination(pagination);

        OrderQueryParam param = OrderQueryParam.builder()
                .memberId(Operator.getMemberId())
                .enterpriseId(Operator.getEnterpriseId())
                .orderStatusList(orderStateEnum.getOrderStatusList())
                .isIncludingSnapshot(true)
                .isIncludingOrderDelivery(true)
                .isIncludingOrderItems(true)
                .isIncludingOrderTickets(true)
                .isIncludingDiscountDetails(true)
                .build();

        request.setParam(param);
        TPageResult<OrderDTO> result = orderQueryApiClient.pageQuery(request);
        if (CollectionUtils.isEmpty(result.getData())) {
            return ResultBuilder.succTPage(Collections.emptyList(), result.getPagination());
        }
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return ResultBuilder.succTPage(orderConverter.batchConvert(result.getData()), result.getPagination());
    }

    public TSingleResult<Boolean> cancel(GenericRequest<OrderCancelParam> request) {
        log.info("取消订单，request：{}", JsonUtils.toJson(request));
        return orderApiClient.cancel(request);
    }

    public Boolean delete(IdRequest request) {
        log.info("删除订单，request：{}", JsonUtils.toJson(request));
        return Boolean.TRUE;
    }

    public TSingleResult<Integer> countOrder(OrderStateEnum orderStateEnum) {
        OrderQueryParam param = OrderQueryParam.builder()
                .memberId(Operator.getMemberId())
                .enterpriseId(Operator.getEnterpriseId())
                .orderStatusList(orderStateEnum.getOrderStatusList())
                .build();
        QueryRequest request = QueryRequest.build();
        request.setParam(param);
        return orderQueryApiClient.countQuery(request);
    }

    /**
     * 确认收货
     * @param request 订单id
     * @return TSingleResult<Boolean>
     */
    public TSingleResult<Boolean> confirmReceipt(IdRequest request) {
        log.info("确认收货，request：{}", JsonUtils.toJson(request));
        return orderApiClient.confirmReceipt(request);
    }

    /**
     * 绑定物流单号
     * @param param 绑定参数
     * @return TSingleResult<Boolean>
     */
    public TSingleResult<Boolean> bindExpressNo(BindExpressNoParam param) {
        log.info("绑定物流单号，request：{}", JsonUtils.toJson(param));
        GenericRequest genericRequest = new GenericRequest();
        genericRequest.setParam(param);
        return orderApiClient.bindExpressNo(genericRequest);
    }
}
