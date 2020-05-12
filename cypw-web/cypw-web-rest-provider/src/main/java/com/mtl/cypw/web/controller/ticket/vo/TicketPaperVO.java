package com.mtl.cypw.web.controller.ticket.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Johnathon.Yuan
 * @date 2020-02-14 22:14
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "电子票明细")
public class TicketPaperVO extends BaseEntityInfo {

    @ApiModelProperty(value = "验证状态（布尔值, t=通过, f=失败）", position = 1)
    private Boolean passed;

    @ApiModelProperty(value = "验证提示消息", position = 1)
    private String passMessage;

    @ApiModelProperty(value = "电子票ID", position = 1)
    private Integer ticketId;

    @ApiModelProperty(value = "项目名称", position = 2)
    private String programName;

    @ApiModelProperty(value = "场次名称", position = 3)
    private String eventName;

    @ApiModelProperty(value = "验检身份证号", position = 4)
    private String idCard;

    @ApiModelProperty(value = "验检身份证名称", position = 5)
    private String idCardName;

    @ApiModelProperty(value = "实名制绑定身份证号", position = 6)
    private String bindingIdCard;

    @ApiModelProperty(value = "实名制绑定身份证名称", position = 7)
    private String bindingIdCardName;

    @ApiModelProperty(value = "检票入场时间", position = 8)
    private Date checkTime;

    @ApiModelProperty(value = "电子票检票码", position = 9)
    private String checkCode;

    @ApiModelProperty(value = "检票人姓名", position = 10)
    private String checkUserName;

    @ApiModelProperty(value = "检票入口", position = 11)
    private String checkEntry;

    @ApiModelProperty(value = "票价", position = 12)
    private BigDecimal ticketPrice;

    @ApiModelProperty(value = "票价描述", position = 13)
    private String ticketPriceDesc;

    @ApiModelProperty(value = "场馆描述", position = 14)
    private String venueName;

    @ApiModelProperty(value = "区域描述", position = 15)
    private String zoneName;

    @ApiModelProperty(value = "座位描述", position = 16)
    private String seatName;

    @ApiModelProperty(value = "客户手机", position = 17)
    private String mobileNo;

    @ApiModelProperty(value = "客户名称", position = 18)
    private String customerName;

}
