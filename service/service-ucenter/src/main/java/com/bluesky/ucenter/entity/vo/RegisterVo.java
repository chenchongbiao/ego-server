package com.bluesky.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVo {
    @ApiModelProperty(value = "志愿者名称")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "密码")
    private String password;
//    @ApiModelProperty(value = "验证码")
//    private String code;
    @ApiModelProperty(value = "学号")
    private String s_no;
}
