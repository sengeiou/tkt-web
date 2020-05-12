package com.mtl.cypw.web.controller.mpm.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2020/3/6.
 */
@Data
public class CreateBuryingPointParam {

    @ApiModelProperty(value = "埋点类型", required = true)
    private String type;
    @ApiModelProperty(value = "埋点内容", required = true)
    private String content;
    @ApiModelProperty(value = "来源，例：WEIXIN、WEB、M_WEB、APP、WEIXIN_MINI")
    private String sourcePlatform;
    @ApiModelProperty(value = "来源页面（非必填），例：“首页”“详情页”")
    private String sourcePage;
}
