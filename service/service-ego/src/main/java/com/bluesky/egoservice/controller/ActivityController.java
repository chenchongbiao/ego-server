package com.bluesky.egoservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.vo.ActivityInfoVo;
import com.bluesky.egoservice.entity.vo.ActivityPublishVo;
import com.bluesky.egoservice.entity.vo.ActivityQuery;
import com.bluesky.egoservice.service.ActivityService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Api(value = "活动管理")
@RestController
@RequestMapping("/egoservice/activity")
//@CrossOrigin//解决跨域
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //添加活动基本信息的方法
    @ApiOperation(value = "添加活动基本信息的方法")
    @PostMapping("addActivityInfo")
    public R addActivityInfo(@RequestBody ActivityInfoVo activityInfoVo) {
        //返回添加之后活动id
        String id = activityService.saveActivityInfo(activityInfoVo);
        return R.ok().data("activityId",id);
    }

    // 删除活动
    @ApiOperation(value = "删除活动")
    @DeleteMapping("{ActivityId}")
    public R deleteActivity(@ApiParam(name = "ActivityId", value = "活动id", required = true)
                            @PathVariable String ActivityId) {
        activityService.removeActivity(ActivityId);
        return R.ok();
    }
    //活动列表 基本实现
    @ApiOperation(value = "活动列表")
    @GetMapping
    public R getActivityList() {
        List<Activity> list = activityService.list(null);
        return R.ok().data("list",list);
    }

    //根据活动id查询活动基本信息
    @ApiOperation(value = "根据活动id查询活动基本信息")
    @GetMapping("getActivityInfo/{activityId}")
    public R getActivityInfo(@PathVariable String activityId) {
        ActivityInfoVo activityInfoVo = activityService.getActivityInfo(activityId);
        return R.ok().data("ActivityInfoVo",activityInfoVo);
    }

    //修改活动信息
    @ApiOperation(value = "修改活动信息")
    @PostMapping("updateActivityInfo")
    public R updateActivityInfo(@RequestBody ActivityInfoVo activityInfoVo) {
        activityService.updateActivityInfo(activityInfoVo);
        return R.ok();
    }

    //根据活动id查询活动确认信息
    @ApiOperation(value = "根据活动id查询活动确认信息")
    @GetMapping("getPublishActivityInfo/{id}")
    public R getPublishActivityInfo(@PathVariable String id) {
        ActivityPublishVo activityPublishVo = activityService.publishActivityInfo(id);
        return R.ok().data("publishActivity",activityPublishVo);
    }

    //活动最终发布
    //修改活动状态
    @ApiOperation(value = "活动最终发布")
    @GetMapping("publishActivity/{id}")
    public R publishActivity(@PathVariable String id) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus("Normal");// 设置活动发布状态
        activityService.updateById(activity);
        return R.ok();
    }


    //4 条件查询带分页的方法
    @ApiOperation(value = "条件查询活动带分页")
    @PostMapping("pageActivityCondition/{current}/{limit}")
    public R pageActivityCondition(@PathVariable long current, @PathVariable long limit,
                                   @RequestBody(required = false) ActivityQuery activityQuery) {
        //@RequestBody，使用json格式传递数据封装到对应对象，required = false表示值可以为空，必须使用post请求
        //创建page对象
        Page<Activity> pageActivity = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis 动态sql
        String title = activityQuery.getTitle(); // 活动名称
        String begin = activityQuery.getBegin(); // 活动开始时间
        String end = activityQuery.getEnd(); // 活动结束时间
        String teamId = activityQuery.getEnd(); // 发布活动组织ID
        String subjectId = activityQuery.getEnd();  // 活动分类ID
        String subjectParentId = activityQuery.getEnd(); // 活动分类父级ID
        Integer activityMinTime = activityQuery.getActivityMinTime(); // 志愿时长
        Integer activityMaxTime = activityQuery.getActivityMaxTime(); // 志愿时长
        String address = activityQuery.getEnd(); // 活动地点
        Long applyMinCount = activityQuery.getApplyMinCount(); // 报名最小数量
        Long applyMaxCount = activityQuery.getApplyMaxCount(); // 报名最大数量
        Long viewMinCount = activityQuery.getViewMinCount(); // 浏览最小数量
        Long viewMaxCount = activityQuery.getViewMaxCount(); // 浏览最小数量
        String status = activityQuery.getEnd();  //课程状态，Draft未发布  Normal已发布

        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            // //构建条件，相似
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)) {
            //大于等于活动开始时间
            wrapper.ge("start_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            //小于等于活动结束时间
            wrapper.le("end_time",end);
        }
        if(!StringUtils.isEmpty(teamId)){
            // 等于组织的id
            wrapper.eq("team_id",teamId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            // 等于分类id
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            // 等于分类父级
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(activityMinTime) && !StringUtils.isEmpty(activityMaxTime)){
            // 志愿时长范围
            wrapper.ge("activity_time",activityMinTime).le("activity_time",activityMaxTime);
        }
        if(!StringUtils.isEmpty(address)){
            // 模糊查询活动地点
            wrapper.like("address",address);
        }
        if(!StringUtils.isEmpty(applyMinCount) && !StringUtils.isEmpty(applyMaxCount)){
            // 报名数量范围
            wrapper.ge("apply_max",activityMinTime).le("apply_max",activityMaxTime);
        }
        if(!StringUtils.isEmpty(viewMinCount) && !StringUtils.isEmpty(viewMaxCount)){
            // 浏览范围
            wrapper.ge("view_count",activityMinTime).le("view_count",activityMaxTime);
        }
        if(!StringUtils.isEmpty(status)){
            // 等于
            wrapper.eq("status",status);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        activityService.page(pageActivity,wrapper);

        long total = pageActivity.getTotal();//总记录数
        List<Activity> records = pageActivity.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
}

