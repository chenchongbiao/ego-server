package com.bluesky.egoservice.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class ActivityInfoVo {
    @ApiModelProperty(value = "活动ID")
    private String id;

    @ApiModelProperty(value = "举办活动组织ID")
    private String teamId;

    @ApiModelProperty(value = "活动分类ID")
    private String subjectId;

    @ApiModelProperty(value = "活动分类父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "报名最大人数")
    private Long applyMax;

    @ApiModelProperty(value = "活动时长")
    private Integer activityTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "活动报名开始时间")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "活动报名结束时间")
    private Date endTime;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "活动封面图片路径")
    private String photos;

    @ApiModelProperty(value = "活动简介")
    private String intro;
}
