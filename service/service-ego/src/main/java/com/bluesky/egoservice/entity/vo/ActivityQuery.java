package com.bluesky.egoservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityQuery {
        @ApiModelProperty(value = "活动名称,模糊查询")
        private String title;

        @ApiModelProperty(value = "活动开始时间", example = "2021-01-01 10:10:10")
        private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

        @ApiModelProperty(value = "活动结束时间", example = "2021-12-01 10:10:10")
        private String end;

        @ApiModelProperty(value = "发布活动组织ID")
        private String teamId;

        @ApiModelProperty(value = "活动分类ID")
        private String subjectId;

        @ApiModelProperty(value = "活动分类父级ID")
        private String subjectParentId;

        @ApiModelProperty(value = "志愿最小时长")
        private Integer activityMinTime;

        @ApiModelProperty(value = "志愿最大时长")
        private Integer activityMaxTime;

        @ApiModelProperty(value = "活动地点")
        private String address;

        @ApiModelProperty(value = "报名最小数量")
        private Long applyMinCount;

        @ApiModelProperty(value = "报名最大数量")
        private Long applyMaxCount;

        @ApiModelProperty(value = "浏览最小数量")
        private Long viewMinCount;

        @ApiModelProperty(value = "浏览最大数量")
        private Long viewMaxCount;

        @ApiModelProperty(value = "活动状态 Draft未发布  Normal已发布")
        private String status;

}
