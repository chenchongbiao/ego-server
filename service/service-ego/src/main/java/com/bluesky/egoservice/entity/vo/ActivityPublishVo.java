package com.bluesky.egoservice.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityPublishVo {
    private String id; // 活动ID
    private String title; // 活动名称
    private String photos; // 活动封面图片路径
    private Integer activityTime; // 志愿时长
    private String subjectLevelOne; // 一级分类
    private String subjectLevelTwo; // 二级分类
    private Date startTime;  // 活动开始时间
    private Date endTime; // 活动结束时间
    private String teamname; // 举办活动组织ID
    private String intro; // 服务内容
//    private String manager; // 联系人
}
