package com.bluesky.egoservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityFrontVo {
    @ApiModelProperty(value = "活动名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "发布活动组织ID")
    private String teamId;

    @ApiModelProperty(value = "活动分类ID")
    private String subjectId;

    @ApiModelProperty(value = "活动分类父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "报名数量排序")
    private Long applyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;
}
