package com.mtl.cypw.web.controller.mpm.vo;

import com.mtl.cypw.domain.payment.enums.PayTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/26.
 */
@Data
public class EnterpriseVO {
    @ApiModelProperty("企业id")
    private Integer enterpriseId;
    @ApiModelProperty("企业名称")
    private String enterpriseName;
    @ApiModelProperty("商户id")
    private String tenantId;
    @ApiModelProperty("商户logo")
    private String tenantLogo;
    @ApiModelProperty("服务电话")
    private String servicePhone;
    @ApiModelProperty("首页展示模板")
    private String indexTemplate;
    @ApiModelProperty("首页背景颜色")
    private String indexBackgroundColor;
    @ApiModelProperty("支持支付方式")
    private List<PayTypeEnum> paymentTypes;
    @ApiModelProperty("店铺首页title")
    private String homeTitle;
    @ApiModelProperty("商户的演出类型")
    private List<ProgramTypeVO> programTypes;
    @ApiModelProperty("扩展登录方式")
    private String extendedLogin;
    @ApiModelProperty("商户搜索设置")
    private EnterpriseSearchVO enterpriseSearch;
    @ApiModelProperty("建行分期支付数据")
    private String ccbInstallmentStr;
    @ApiModelProperty("首页弹出窗1")
    private String message1;
    @ApiModelProperty("首页弹出窗2")
    private String message2;
}
