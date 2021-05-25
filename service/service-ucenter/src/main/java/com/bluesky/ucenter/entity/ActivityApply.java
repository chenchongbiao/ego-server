package com.bluesky.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动报名
 * </p>
 *
 * @author bluesky
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ActivityApply对象", description="活动报名")
public class ActivityApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "活动id")
    private String activityId;

    @ApiModelProperty(value = "活动名称")
    private String activityTitle;

    @ApiModelProperty(value = "活动封面")
    private String photos;

    @ApiModelProperty(value = "组织名称")
    private String teamName;

    @ApiModelProperty(value = "志愿者id")
    private String memberId;

    @ApiModelProperty(value = "志愿者名称")
    private String username;

    @ApiModelProperty(value = "志愿者学号")
    private String sNo;

    @ApiModelProperty(value = "志愿时长")
    private Integer activityTime;

    @ApiModelProperty(value = "活动状态（0：未参与 1：已参与）")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
