package com.mtl.cypw.web.controller.member.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2020/3/12.
 */
@Data
public class MachineLoginParam {

    @ApiModelProperty(value = "密钥")
    private String machineKey;
    @ApiModelProperty(value = "MAC地址")
    private String macAddress;

    public Boolean checkParam() {
        if (StringUtils.isEmpty(machineKey) || StringUtils.isEmpty(macAddress)) {
            return false;
        }
        return true;
    }
}
