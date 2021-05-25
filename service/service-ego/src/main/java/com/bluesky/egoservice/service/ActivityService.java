package com.bluesky.egoservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bluesky.egoservice.entity.frontvo.ActivityFrontVo;
import com.bluesky.egoservice.entity.frontvo.ActivityWebVo;
import com.bluesky.egoservice.entity.vo.ActivityInfoVo;
import com.bluesky.egoservice.entity.vo.ActivityPublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
public interface ActivityService extends IService<Activity> {
    //添加活动基本信息的方法
    String saveActivityInfo(ActivityInfoVo activityInfoVo);
    //根据活动id查询活动基本信息
    ActivityInfoVo getActivityInfo(String activityId);

    //修改课程信息
    void updateActivityInfo(ActivityInfoVo activityInfoVo);

    //根据课程id查询课程确认信息
    ActivityPublishVo publishActivityInfo(String id);

    //删除活动
    void removeActivity(String activityId);

    // 前台活动页面显示
    Map<String, Object> getActivityFrontList(Page<Activity> pageActivity, ActivityFrontVo activityFrontVo);

    // 根据活动id，编写sql语句查询活动信息
    ActivityWebVo getBaseActivityInfo(String id);

}
