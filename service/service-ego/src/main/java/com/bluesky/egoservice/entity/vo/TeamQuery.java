package com.bluesky.egoservice.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamQuery {

    @ApiModelProperty(value = "服务队名称,模糊查询")
    private String teamName;

    @ApiModelProperty(value = "等级（1->5 每增加10人升星，10人2星）")
    private Integer level;

    @ApiModelProperty(value = "最大人数")
    private Integer Maxpersons;

    @ApiModelProperty(value = "最小人数")
    private Integer Minpersons;

    @ApiModelProperty(value = "查询开始时间", example = "2021-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2021-12-01 10:10:10")
    private String end;

}
