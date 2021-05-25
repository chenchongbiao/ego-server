package com.bluesky.egoservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.frontvo.ActivityFrontVo;
import com.bluesky.egoservice.entity.frontvo.ActivityWebVo;
import com.bluesky.egoservice.service.ActivityService;
import com.bluesky.utils.ApplyInfo.ActivityWebApplyInfo;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "前端活动查询")
@RestController
@RequestMapping("/egoservice/activityfront")
//@CrossOrigin//解决跨域
public class ActivityFrontController {
    @Autowired
    private ActivityService activityService;

    // 条件查询带分页查询课程
    @ApiOperation(value = "前端活动页条件查询")
    @PostMapping("getFrontActivityList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) ActivityFrontVo activityFrontVo) {
        Page<Activity> pageActivity = new Page<>(page,limit);
        Map<String,Object> map = activityService.getActivityFrontList(pageActivity,activityFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    // 活动详情页
    @ApiOperation(value = "前端活动详情页查询")
    @GetMapping("getFrontActivityInfo/{id}") // ApiParam参数说明-name：参数 – value：参数名 – required：是否必须。默认为 true,表示请求参数中必须包含对应的参数，若不存在，将抛出异常
    public R getFrontActivityInfo(@ApiParam(name = "id", value = "活动id", required = true)
                                  @PathVariable String id){
        // 根据活动id，编写sql语句查询活动信息
        ActivityWebVo activityWebVo = activityService.getBaseActivityInfo(id);
        // 根据活动id和用户id查询当前活动是否已经参加
//        boolean buyCourse = ordersClient.isBuyCourse(id, JwtUtils.getMemberIdByJwtToken(request));
//        .data("isJoin",buyCourse);
        return R.ok().data("activityWebVo",activityWebVo);
    }

    // 根据活动id查询课程信息
    @PostMapping("getActivityApplyInfo/{activityId}")
    public ActivityWebApplyInfo getActivityApplyInfo(@PathVariable String activityId){
        ActivityWebVo activityWebVo = activityService.getBaseActivityInfo(activityId);
        ActivityWebApplyInfo activityApplyInfo = new ActivityWebApplyInfo();
        BeanUtils.copyProperties(activityWebVo,activityApplyInfo);
        return activityApplyInfo;
    }

}
