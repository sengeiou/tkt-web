package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2020-01-10 19:09
 */
@Data
public class SeatMapVO {

    @ApiModelProperty(value = "场次信息")
    private EventVO event;

    @ApiModelProperty(value = "模板信息")
    private TemplateVO template;

    @ApiModelProperty(value = "票品列表")
    private List<EventPriceVO> prices;

    @ApiModelProperty(value = "座位列表")
    private List<SeatVO> seats;
}
