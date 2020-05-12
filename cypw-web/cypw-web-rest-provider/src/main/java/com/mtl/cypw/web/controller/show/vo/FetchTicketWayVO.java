package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tang.
 * @date 2019/12/3.
 */
@Data
public class FetchTicketWayVO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("项目id")
    private Integer programId;

    @ApiModelProperty("取票方式")
    private String deliverType;

    @ApiModelProperty("是否需要证件取件(0-不需要, 1-需要)")
    private Integer needIdcard;

    @ApiModelProperty("温馨提示")
    private String tips;

    @ApiModelProperty("配送费")
    private Long expressFee;

    @ApiModelProperty("取票地址")
    private String fetchAddress;

    @ApiModelProperty("联系电话")
    private String contactMobile;

    @ApiModelProperty("上门取票的服务时间说明")
    private String fetchTimeDesc;

    @ApiModelProperty("企业ID")
    private Integer enterpriseId;

}
