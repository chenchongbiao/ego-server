package com.bluesky.egoservice.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminQuery {

    @ApiModelProperty(value = "管理员用户名,模糊查询")
    private String username;

    @ApiModelProperty(value = "查询开始时间", example = "2021-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2021-12-01 10:10:10")
    private String end;

}
