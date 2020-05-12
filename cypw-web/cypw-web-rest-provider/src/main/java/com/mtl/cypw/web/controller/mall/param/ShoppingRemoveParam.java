package com.mtl.cypw.web.controller.mall.param;

import com.juqitech.request.BaseParam;
import com.juqitech.service.utils.ParamChecker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-02 18:49
 */
@Data
@ApiModel(description = "批量移除购物车参数")
public class ShoppingRemoveParam extends BaseParam {

    @ApiModelProperty(value = "SKU 类型（默认值=3）", required = true, position = 1)
    private Integer skuType;

    @ApiModelProperty(value = "SKU ID集合（批量）", required = true, position = 2)
    private List<Integer> skuIds;

    @Override
    public void checkParam() {
        ParamChecker.notEmpty(skuIds, "操作商品错误");
    }
}
