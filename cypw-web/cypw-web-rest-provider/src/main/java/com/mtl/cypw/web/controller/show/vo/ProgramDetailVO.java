package com.mtl.cypw.web.controller.show.vo;

import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/21.
 */
@Data
public class ProgramDetailVO extends ProgramVO {

    @ApiModelProperty(value = "演出介绍")
    private String content;

    @ApiModelProperty(value = "购票须知")
    private String ticketInfo;

    @ApiModelProperty(value = "演员列表")
    private List<ActorVO> actors;

    @ApiModelProperty(value = "优惠券列表")
    private List<PromotionVO> promotions;

    @ApiModelProperty("演出商标题")
    private String sponserTitle;

    @ApiModelProperty("演出商介绍")
    private String sponserIntroduce;

    @ApiModelProperty("是否在线选座")
    private Boolean supportSeatFlag;

}
