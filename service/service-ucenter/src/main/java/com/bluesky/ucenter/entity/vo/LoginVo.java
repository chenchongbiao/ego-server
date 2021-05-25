package com.bluesky.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVo {
    @ApiModelProperty(value = "学号")
    private String sNo;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码")
    private String code;
}
