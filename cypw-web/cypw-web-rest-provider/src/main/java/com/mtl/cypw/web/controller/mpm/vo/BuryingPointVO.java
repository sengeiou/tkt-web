package com.mtl.cypw.web.controller.mpm.vo;

import com.mtl.cypw.domain.mpm.enums.BuryingPointTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@Data
public class BuryingPointVO {
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer memberId;

    @ApiModelProperty("埋点类型")
    private BuryingPointTypeEnum buryingPointType;

    @ApiModelProperty("埋点内容")
    private String buryingPointContent;

    @ApiModelProperty("企业ID")
    private Integer enterpriseId;

    @ApiModelProperty("搜索时间")
    private Date createTime;


}
