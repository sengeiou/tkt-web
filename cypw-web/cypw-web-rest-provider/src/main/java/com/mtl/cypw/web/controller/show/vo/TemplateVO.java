package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class TemplateVO {

    @ApiModelProperty(value = "模板ID")
    private Integer templateId;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "布局模式（0-区域选座，1-直接选座）")
    private Integer mapTypeId;

    @ApiModelProperty(value = "区域选座-SVG")
    private String templateMap;

    @ApiModelProperty(value = "直接选座-SVG")
    private String oneSvg;
}
