package com.bluesky.egoservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.Team;
import com.bluesky.egoservice.service.ActivityService;
import com.bluesky.egoservice.service.TeamService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "组织信息前端管理")
@RestController
@RequestMapping("/egoservice/teamfront")
//@CrossOrigin//解决跨域
public class TeamFrontController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private ActivityService activityService;

    // 分页查询组织
    @ApiOperation(value = "分页查询组织")
    @GetMapping("getTeamFrontList/{page}/{limit}")
    public R getTeamFrontList(@PathVariable long page, @PathVariable long limit){
        // 创建page对象,传入显示进入第几页page，最大显示数limit
        Page<Team> pageTeam = new Page<>(page,limit);
        Map<String,Object> map = teamService.getTeamFrontList(pageTeam);
        // 返回分页的所有数据
        return R.ok().data(map);
    }

    // 组织详情的功能
    @ApiOperation(value = "组织详情的功能")
    @GetMapping("getTeamFrontInfo/{teamId}")
    public R getTeamFrontInfo(@PathVariable String teamId) {
        //1 根据讲师id查询讲师基本信息
        Team team = teamService.getById(teamId);
        //2 根据组织id查询所讲课程
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("team_id",teamId);
        List<Activity> acitivityList = activityService.list(wrapper);
        return R.ok().data("team",team).data("acitivityList",acitivityList);
    }
}
