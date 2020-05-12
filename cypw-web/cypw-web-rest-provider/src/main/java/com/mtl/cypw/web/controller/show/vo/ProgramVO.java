package com.mtl.cypw.web.controller.show.vo;

import com.mtl.cypw.domain.show.enums.ProgramTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/21.
 */
@Data
public class ProgramVO {
    @ApiModelProperty(value = "演出ID")
    private Integer id;

    /**
     * @see ProgramTypeEnum
     */
    @ApiModelProperty(value = "演出类型")
    private Integer type;

//    @ApiModelProperty(value = "演出类型标题")
//    private String typeTitle;

    @ApiModelProperty(value = "演出全称")
    private String name;

    @ApiModelProperty(value = "演出简称")
    private String brief;

    @ApiModelProperty(value = "演出海报")
    private String posterUrl;

    @ApiModelProperty(value = "演出状态（ONSALE-在售）")
    private String status;

    @ApiModelProperty(value = "场馆ID")
    private Integer venueId;

    @ApiModelProperty(value = "首场演出开始时间")
    private Long firstShowTime;

    @ApiModelProperty(value = "末场演出结束时间")
    private Long lastShowTime;

    @ApiModelProperty(value = "是否选座")
    private Boolean supportSeatFlag;

    @ApiModelProperty(value = "是否支持电子票")
    private Boolean supportETicket;

    @ApiModelProperty(value = "官方")
    private Boolean isOfficial;

    @ApiModelProperty(value = "折扣描述")
    private String discount;

    @ApiModelProperty(value = "价格描述")
    private String currentOnSaleMinPrice;

    @ApiModelProperty(value = "时间描述")
    private String programTimeDescribe;

    @ApiModelProperty(value = "精彩看点")
    private String recommended;

    @ApiModelProperty(value = "最低票价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "剧院名称")
    private String locationName;

    @ApiModelProperty(value = "剧院地址")
    private String locationAddress;

    @ApiModelProperty(value = "取票方式")
    private List<FetchTicketWayVO> fetchTicketWayList;

    @ApiModelProperty(value = "服务电话")
    private String serviceMobile;

    @ApiModelProperty(value = "项目标签")
    private String tags;
}
