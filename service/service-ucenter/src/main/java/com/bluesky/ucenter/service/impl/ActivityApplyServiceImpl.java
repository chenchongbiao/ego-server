package com.bluesky.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.ucenter.client.EgoClient;
import com.bluesky.ucenter.entity.ActivityApply;
import com.bluesky.ucenter.entity.Volunteer;
import com.bluesky.ucenter.mapper.ActivityApplyMapper;
import com.bluesky.ucenter.service.ActivityApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bluesky.ucenter.service.VolunteerService;
import com.bluesky.utils.ApplyInfo.ActivityWebApplyInfo;
import com.bluesky.utils.JwtUtils;
import com.bluesky.utils.SetUidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动报名 服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-15
 */
@Service
public class ActivityApplyServiceImpl extends ServiceImpl<ActivityApplyMapper, ActivityApply> implements ActivityApplyService {

    @Autowired
    private EgoClient egoClient;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public String applyInfo(String activityId, String menbenId) {
        // 获取用户id获取用户信息
        Volunteer volunteer = volunteerService.getById(menbenId);

        // 远程调用根据活动id获取活动信息，在utils模块中加入ActivityWebApplyInfo对象，通过base模块导入了utils模块，server模块导入了base模块，通过包传递机制，使用对象
        ActivityWebApplyInfo activityWebApplyInfo = egoClient.getActivityApplyInfo(activityId);
        // 创建apply对象
        ActivityApply activityApply = new ActivityApply();
        activityApply.setActivityId(activityWebApplyInfo.getId());// 获取活动id
        activityApply.setActivityTitle(activityWebApplyInfo.getTitle());//获取活动名称
        activityApply.setPhotos(activityWebApplyInfo.getPhotos());//获取活动图片
        activityApply.setTeamName(activityWebApplyInfo.getTeamName());//设置组织名称
        activityApply.setMemberId(volunteer.getId());//设置志愿者id
        activityApply.setUsername(volunteer.getUsername());//设置志愿者名称
        activityApply.setSNo(volunteer.getSNo());//设置志愿者学号
        activityApply.setActivityTime(activityWebApplyInfo.getActivityTime());//设置志愿时长
        activityApply.setStatus(1);//活动状态（0：活动结束 1：正在参与）
        baseMapper.insert(activityApply);
        return activityApply.getActivityId();
    }

    @Override
    public ActivityApply selectOne(QueryWrapper<ActivityApply> wrapper) {
        ActivityApply activityApply = baseMapper.selectOne(wrapper);
        return activityApply;
    }
}
