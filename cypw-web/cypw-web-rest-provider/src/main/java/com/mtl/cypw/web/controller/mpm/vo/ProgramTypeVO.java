package com.mtl.cypw.web.controller.mpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2019/12/31.
 */
@Data
public class ProgramTypeVO {

    @ApiModelProperty("演出类型ID")
    private Integer programTypeId;
    @ApiModelProperty("演出类型标题")
    private String programTypeTitle;
    @ApiModelProperty("排序")
    private Integer sortOrder;

}
