package com.mtl.cypw.web.controller.mall.param;

import com.juqitech.request.BaseParam;
import com.juqitech.service.utils.ParamChecker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Johnathon.Yuan
 * @date 2020-03-02 18:49
 */
@Data
@ApiModel(description = "编辑购物车参数")
public class ShoppingParam extends BaseParam {

    @ApiModelProperty(value = "商品 ID", required = true, position = 1)
    private Integer goodsId;

    @ApiModelProperty(value = "SKU 类型（默认值=3）", required = true, position = 2)
    private Integer skuType;

    @ApiModelProperty(value = "SKU ID（即票价/规格ID）", required = true, position = 3)
    private Integer skuId;

    @ApiModelProperty(value = "购买数量", required = true, position = 4)
    private Integer quantity;

    @Override
    public void checkParam() {
        ParamChecker.notNull(skuId, "商品错误");
        ParamChecker.notNull(goodsId, "商品错误");
        ParamChecker.notNull(quantity, "购买数量错误");
    }
}
