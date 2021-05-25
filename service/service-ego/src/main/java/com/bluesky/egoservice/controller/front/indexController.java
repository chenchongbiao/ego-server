package com.bluesky.egoservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.Team;
import com.bluesky.egoservice.service.ActivityService;
import com.bluesky.egoservice.service.TeamService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页显示")
@RestController
@RequestMapping("/egoservice/index")
//@CrossOrigin//解决跨域
public class indexController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private TeamService teamService;

    //查询前8条活动，查询前4个志愿者组织
    @GetMapping("index")
    public R index() {
        //查询前8个活动
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Activity> activityList = activityService.list(wrapper);

        //查询前4个志愿者组织
        QueryWrapper<Team> wrapperTeam = new QueryWrapper<>();
        wrapperTeam.orderByDesc("id");
        wrapperTeam.last("limit 4");
        List<Team> teamList = teamService.list(wrapperTeam);
        return R.ok().data("activityList",activityList).data("teamList",teamList);
    }

}
