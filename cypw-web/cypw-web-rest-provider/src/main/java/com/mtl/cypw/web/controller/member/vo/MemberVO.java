package com.mtl.cypw.web.controller.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Data
public class MemberVO {
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;
    @ApiModelProperty(value = "会员名称")
    private String memberName;
    @ApiModelProperty(value = "会员昵称")
    private String nickName;
    @ApiModelProperty(value = "会员手机号")
    private String memberMobile;
    @ApiModelProperty(value = "商户ID")
    private Integer enterpriseId;
    @ApiModelProperty(value = "用户访问令牌")
    private String accessToken;
    @ApiModelProperty(value = "会员图片")
    private String memberImage;
    @ApiModelProperty(value = "会员默认地址ID")
    private Integer defaultAddressId;
    @ApiModelProperty(value = "更据计算用户可能喜欢")
    private List guessKeys;
}
