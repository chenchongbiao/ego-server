package com.bluesky.egoservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Team对象", description="")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "负责人id")
    private String teamManagerId;

    @ApiModelProperty(value = "组织名称")
    private String teamName;

    @ApiModelProperty(value = "宗旨")
    private String theme;

    @ApiModelProperty(value = "简介")
    private String intro;

    @ApiModelProperty(value = "人数")
    private Integer persons;

    @ApiModelProperty(value = "上级组织")
    private String parentOrganization;

    @ApiModelProperty(value = "等级（1->5 每增加10人升星，10人2星）")
    private Integer level;

    @ApiModelProperty(value = "组织主题图")
    private String themeImg;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
