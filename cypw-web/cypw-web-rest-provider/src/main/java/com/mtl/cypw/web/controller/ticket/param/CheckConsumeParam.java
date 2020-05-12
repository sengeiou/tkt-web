package com.mtl.cypw.web.controller.ticket.param;

import com.juqitech.request.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Johnathon.Yuan
 * @date 2019-02-17 11:57
 */
@Data
public class CheckConsumeParam extends BaseParam {

    @ApiModelProperty(value = "检票码", required = true)
    private String code;

    @ApiModelProperty(value = "检票渠道[1-线下运营, 2-小程序检票, 3-PDA设备检票, 4-闸机设备检票]", required = true)
    private Integer checkChannel;

    @ApiModelProperty(value = "入场方式[1-扫码, 2-人工, 3-RFID, 4-身份证]", required = true)
    private Integer checkMethod;

    @ApiModelProperty(value = "检票设备信息", required = true)
    private CheckDeviceInfo deviceInfo;

    @Data
    public static class CheckDeviceInfo {

        @ApiModelProperty(value = "检票设备版本", required = false)
        private String deviceVersion;

        @ApiModelProperty(value = "检票设备类型, 即两种类型[1-移动手机, 2-PDA, 3-闸机]", required = false)
        private Integer deviceType;

        @ApiModelProperty(value = "检票设备唯一标识", required = false)
        private String deviceUniqueCode;

        @ApiModelProperty(value = "验刷身份证号（入场）", required = false)
        private String idCard;

        @ApiModelProperty(value = "验刷身份证名（入场））", required = false)
        private String idCardName;

        @ApiModelProperty(value = "实名制绑定身份证号", required = false)
        private String bindingIdCard;

        @ApiModelProperty(value = "实名制绑定身份证名", required = false)
        private String bindingIdCardName;
    }

    @Override
    public void checkParam() {

    }

}
