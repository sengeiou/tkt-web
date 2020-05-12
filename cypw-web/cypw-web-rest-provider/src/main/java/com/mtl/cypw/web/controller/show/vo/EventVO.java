package com.mtl.cypw.web.controller.show.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class EventVO {
    @ApiModelProperty(value = "场次ID")
    private Integer eventId;
    @ApiModelProperty(value = "演出ID")
    private Integer programId;
    @ApiModelProperty(value = "场次标题")
    private String eventTitle;
    @ApiModelProperty(value = "场次时间")
    private Long eventDate;
}
