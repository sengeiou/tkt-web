package com.mtl.cypw.web.controller.order.vo;

import com.juqitech.response.BaseEntityInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-10 15:52
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel(description = "订单电子票明细")
public class OrderTicketVO extends BaseEntityInfo {

    @ApiModelProperty(value = "电子票ID", position = 1)
    private Integer orderTicketId;

    @ApiModelProperty(value = "订单明细ID", position = 2)
    private Integer orderItemId;

    @ApiModelProperty(value = "票品ID", position = 3)
    private Integer priceId;

    @ApiModelProperty(value = "分区名称", position = 4)
    private String zoneName;

    @ApiModelProperty(value = "座位名称(row_name+col_name)", position = 5)
    private String seatName;

    @ApiModelProperty(value = "票面价", position = 6)
    private BigDecimal ticketPrice;

    @ApiModelProperty(value = "市场价", position = 7)
    private BigDecimal originPrice;

    @ApiModelProperty(value = "电子票检票码", position = 8)
    private String checkCode;

    @ApiModelProperty(value = "电子票二维码", position = 9)
    private String qrCode;

    @ApiModelProperty(value = "电子票二维码Base64", position = 13)
    private String qrCodeBase64;

    @ApiModelProperty(value = "是否检票", position = 10)
    private Boolean checkStatus;

    @ApiModelProperty(value = "检票时间", position = 11)
    private Long checkTime;

    @ApiModelProperty(value = "票品描述", position = 12)
    private String ticketDesc;

}
