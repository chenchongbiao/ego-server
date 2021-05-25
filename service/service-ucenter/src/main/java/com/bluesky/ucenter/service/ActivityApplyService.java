package com.bluesky.ucenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.ucenter.entity.ActivityApply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动报名 服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-15
 */
public interface ActivityApplyService extends IService<ActivityApply> {

    // 生成活动报名信息
    String applyInfo(String activityId, String memberIdByJwtToken);

    // 查找报名信息中是否存在该对象
    ActivityApply selectOne(QueryWrapper<ActivityApply> wrapper);
}
