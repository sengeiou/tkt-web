package com.mtl.cypw.web.controller.order.enums;

import com.mtl.cypw.domain.order.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/18.
 */
public enum OrderStateEnum {

    /**
     * 订单状态
     */
    ALL(1, "全部", new ArrayList<>()),
    WAIT_PAY(2, "待支付", new ArrayList<Integer>() {{
        add(OrderStatusEnum.LOCKED.getCode());
    }}),
    WAIT_DELIVERY(3, "待收货", new ArrayList<Integer>() {{
        add(OrderStatusEnum.PAID.getCode());
        add(OrderStatusEnum.TICKETING.getCode());
    }}),
    WAIT_ATTEND(4, "待参加", new ArrayList<Integer>() {{
        add(OrderStatusEnum.TICKETED.getCode());
        add(OrderStatusEnum.DELIVERED.getCode());
        add(OrderStatusEnum.SUCCEED.getCode());
    }});

    OrderStateEnum(int code, String name, List<Integer> orderStatusList) {
        this.code = code;
        this.name = name;
        this.orderStatusList = orderStatusList;
    }

    private int code;
    private String name;
    private List<Integer> orderStatusList;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOrderStatusList() {
        return orderStatusList;
    }
}
