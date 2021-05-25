package com.bluesky.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.ucenter.client.EgoClient;
import com.bluesky.ucenter.entity.ActivityApply;
import com.bluesky.ucenter.entity.Volunteer;
import com.bluesky.ucenter.service.ActivityApplyService;
import com.bluesky.ucenter.service.VolunteerService;
import com.bluesky.utils.ApplyInfo.ActivityWebApplyInfo;
import com.bluesky.utils.JwtUtils;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 活动报名 前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-15
 */
@Api(value = "志愿活动报名")
@RestController
@RequestMapping("/ucenter/activity-apply")
//@CrossOrigin
public class ActivityApplyController {
    @Autowired
    private ActivityApplyService activityApplyService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EgoClient egoClient;

    // 生成活动报名信息
    @PostMapping("applyInfo/{activityId}")
    public R applyInfo(@PathVariable String activityId, HttpServletRequest request){
        // 创建apply对象
        ActivityApply activityApply = new ActivityApply();
        // 获取用户id
        String menberId = JwtUtils.getMemberIdByJwtToken(request);
        // 获取用户id获取用户信息
        Volunteer volunteer = volunteerService.getById(menberId);

        QueryWrapper<ActivityApply> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id",activityId);
        wrapper.eq("member_id",menberId);
        int status = activityApplyService.count(wrapper);
        if(status==1){
            return R.ok().data("activityId",-1);
        }else{
            // 远程调用根据活动id获取活动信息，在utils模块中加入ActivityWebApplyInfo对象，通过base模块导入了utils模块，server模块导入了base模块，通过包传递机制，使用对象
            ActivityWebApplyInfo activityWebApplyInfo = egoClient.getActivityApplyInfo(activityId);

            activityApply.setActivityId(activityWebApplyInfo.getId());// 获取活动id
            activityApply.setActivityTitle(activityWebApplyInfo.getTitle());//获取活动名称
            activityApply.setPhotos(activityWebApplyInfo.getPhotos());//获取活动图片
            activityApply.setTeamName(activityWebApplyInfo.getTeamName());//设置组织名称
            activityApply.setMemberId(volunteer.getId());//设置志愿者id
            activityApply.setUsername(volunteer.getUsername());//设置志愿者名称
            activityApply.setSNo(volunteer.getSNo());//设置志愿者学号
            activityApply.setActivityTime(activityWebApplyInfo.getActivityTime());//设置志愿时长
            activityApply.setStatus(1);//活动状态（0：活动结束 1：正在参与）
            activityApplyService.save(activityApply);
            return R.ok().data("activityId",activityId);
        }

    }

    // 根据用户id获取用户所有报名信息信息
    @ApiOperation(value = "查找该用户所有活动报名信息")
    @PostMapping("getApplyInfoList/")
    public R getApplyList(HttpServletRequest request){
        //构建条件
        QueryWrapper<ActivityApply> wrapper = new QueryWrapper<>();
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String Id = JwtUtils.getMemberIdByJwtToken(request);
        wrapper.eq("member_id",Id);
        //调用service的方法实现查询所有的操作
        List<ActivityApply> list = activityApplyService.list(wrapper);
        return R.ok().data("items",list);
    }
}

