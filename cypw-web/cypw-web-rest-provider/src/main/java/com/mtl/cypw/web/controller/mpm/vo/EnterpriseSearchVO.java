package com.mtl.cypw.web.controller.mpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@Data
public class EnterpriseSearchVO {

    @ApiModelProperty("默认搜索key")
    private String searchKey;
    @ApiModelProperty("商户猜你喜欢")
    private String guessKey;

}

