package com.bluesky.egoservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Team;
import com.bluesky.egoservice.entity.vo.TeamQuery;
import com.bluesky.egoservice.service.TeamService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Api(value = "组织管理")
@RestController
@RequestMapping("/egoservice/team")
//@CrossOrigin//解决跨域
public class TeamController {
    @Autowired
    private TeamService teamService;

    // 1 添加组织接口的方法
    @ApiOperation("添加组织")
    @PostMapping("addTeam")
    public R addAdmin(@RequestBody Team team) {
        boolean save = teamService.save(team);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    // 逻辑删除组织"
    @ApiOperation(value = "逻辑删除组织")
    @DeleteMapping("/remove/{id}")
    public R removeAdmin(@ApiParam(name = "id", value = "组织ID", required = true) // name参数名称，value对参数的描述，required表示必传参数
                         @PathVariable String id) {
        boolean flag = teamService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //修改组织信息
    @ApiOperation(value = "修改组织信息")
    @PostMapping("updateTeamInfo")
    public R updateTeamInfo(@RequestBody Team team) {
        boolean flag = teamService.updateById(team);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    // 查询组织表所有数据
    //rest风格
    @ApiOperation(value = "所有组织列表")
    @GetMapping("findAll")
    public R findAllAdmin() {
        //调用service的方法实现查询所有的操作
        List<Team> list = teamService.list(null);
        return R.ok().data("items",list);

    }



    //根据活动id查询活动基本信息
    @GetMapping("getTeamInfo/{id}")
    public R getTeamInfo(@PathVariable String id) {
        Team team = teamService.getById(id);
        return R.ok().data("TeamInfo",team);
    }

    // 条件查询带分页的方法
    @ApiOperation(value = "条件查询组织带分页")
    @PostMapping("pageTeamCondition/{current}/{limit}")
    public R pageTeamCondition(@PathVariable long current, @PathVariable long limit,
                               @RequestBody(required = false) TeamQuery teamQuery) {
        //@RequestBody，使用json格式传递数据封装到对应对象，required = false表示值可以为空，必须使用post请求
        //创建page对象
        Page<Team> pageTeam = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String teamname = teamQuery.getTeamName();
        Integer minpersons = teamQuery.getMinpersons();
        Integer maxpersons = teamQuery.getMaxpersons();
        Integer level = teamQuery.getLevel();
        String begin = teamQuery.getBegin();
        String end = teamQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(teamname)){
            // //构建条件，相似
            wrapper.like("teamname",teamname);
        }
        if(!StringUtils.isEmpty(minpersons) && !StringUtils.isEmpty(maxpersons)){
            // 判断人数在某个范围
            wrapper.between("persons",minpersons,maxpersons);
        }
        if(!StringUtils.isEmpty(minpersons) && StringUtils.isEmpty(maxpersons)){
            // 最小值不为空，最大值为空，查询人数最小
            wrapper.ge("persons",minpersons);
        }
        if(StringUtils.isEmpty(minpersons) && !StringUtils.isEmpty(maxpersons)){
            // 最小值为空，最大值不为空，构建条件，查询人数最大
            wrapper.between("persons",1,maxpersons);
        }
        if(!StringUtils.isEmpty(level)){
            // //构建条件，等于
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin) && !StringUtils.isEmpty(end)) {
            // 判断创建时间在某个范围
            wrapper.between("gmt_create",begin,end);
        }
        // 倒序排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        teamService.page(pageTeam,wrapper);

        long total = pageTeam.getTotal();//总记录数
        List<Team> records = pageTeam.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
}

