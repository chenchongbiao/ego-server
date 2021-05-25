package com.bluesky.utils.ApplyInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityWebApplyInfo {
    @ApiModelProperty(value = "活动ID")
    private String id;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "报名最大人数")
    private Long applyMax;

    @ApiModelProperty(value = "已报名数量")
    private Long applyCount;

    @ApiModelProperty(value = "志愿时长")
    private Integer activityTime;

    @ApiModelProperty(value = "活动报名开始时间")
    private Date startTime;

    @ApiModelProperty(value = "活动报名结束时间")
    private Date endTime;

    @ApiModelProperty(value = "活动封面图片路径")
    private String photos;

    @ApiModelProperty(value = "活动简介")
    private String intro;

    @ApiModelProperty(value = "组织id")
    private String teamId;

    @ApiModelProperty(value = "组织名称")
    private String teamName;

    @ApiModelProperty(value = "组织宗旨")
    private String theme;

    @ApiModelProperty(value = "组织主题图")
    private String themeImg;

    @ApiModelProperty(value = "活动一级类别ID")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "类别一级名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "活动二级类别ID")
    private String subjectLevelTwoId;

    @ApiModelProperty(value = "类别二级名称")
    private String subjectLevelTwo;
}
