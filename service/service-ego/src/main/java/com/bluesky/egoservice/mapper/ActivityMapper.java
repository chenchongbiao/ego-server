package com.bluesky.egoservice.mapper;

import com.bluesky.egoservice.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bluesky.egoservice.entity.frontvo.ActivityWebVo;
import com.bluesky.egoservice.entity.vo.ActivityPublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    // 根据活动id查询活动最终发布信息
    public ActivityPublishVo getActivityPublishInfo(String activityId);
    // 根据活动id查询前端活动详情页
    public ActivityWebVo getBaseActivityInfo(String id);
}
