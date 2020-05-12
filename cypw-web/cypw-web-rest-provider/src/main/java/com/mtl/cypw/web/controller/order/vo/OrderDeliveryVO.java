package com.mtl.cypw.web.controller.order.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-10 15:52
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "配送方式")
public class OrderDeliveryVO extends BaseEntityInfo {

    @ApiModelProperty(value = "配送方式", position = 1)
    private String deliverType;

    @ApiModelProperty(value = "配送方式[DESC]", position = 1)
    private String deliverTypeName;

    @ApiModelProperty(value = "取票状态", position = 2)
    private String deliveryStatus;

    @ApiModelProperty(value = "需要证件取件", position = 3)
    private Boolean needIdcard;

    @ApiModelProperty(value = "证件号类型", position = 4)
    private String idcardType;

    @ApiModelProperty(value = "收件人证件号", position = 5)
    private String addresseeIdcard;

    @ApiModelProperty(value = "省份名[快递]", position = 6)
    private String provinceName;

    @ApiModelProperty(value = "城市名[快递]", position = 7)
    private String cityName;

    @ApiModelProperty(value = "区县名[快递]", position = 8)
    private String districtName;

    @ApiModelProperty(value = "收件人具体地址[快递]", position = 9)
    private String detailedAddress;

    @ApiModelProperty(value = "收件人邮编[快递]", position = 10)
    private String postCode;

    @ApiModelProperty(value = "收件人姓名[快递]", position = 11)
    private String addresseeName;

    @ApiModelProperty(value = "收件人电话[快递]", position = 12)
    private String addresseeMobile;

    @ApiModelProperty(value = "物流公司[快递]", position = 13)
    private String expressCompany;

    @ApiModelProperty(value = "快递单号", position = 14)
    private String expressNo;

    @ApiModelProperty(value = "开始配送时间", position = 15)
    private Long deliverTime;

    @ApiModelProperty(value = "配送完成时间", position = 16)
    private Long deliveredTime;

    @ApiModelProperty(value = "现场取票取票地点[线下取票]", position = 17)
    private String localeAddress;

    @ApiModelProperty(value = "现场取票联系方式[线下取票]", position = 18)
    private String localeContact;

    @ApiModelProperty(value = "自助取票时间[自助取票]", position = 19)
    private Long fetchedTime;

}
