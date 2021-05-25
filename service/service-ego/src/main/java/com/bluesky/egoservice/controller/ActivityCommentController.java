package com.bluesky.egoservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.client.UcenterClient;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.ActivityComment;
import com.bluesky.egoservice.service.ActivityCommentService;
import com.bluesky.utils.JwtUtils;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-07
 */
@Api(value = "活动评论")
@RestController
@RequestMapping("/egoservice/comment")
//@CrossOrigin//解决跨域
public class ActivityCommentController {
    @Autowired
    private ActivityCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    //根据活动id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "activityQuery", value = "查询对象", required = false)
                    String courseId) {
        Page<ActivityComment> pageParam = new Page<>(page, limit);

        QueryWrapper<ActivityComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        commentService.page(pageParam,wrapper);
        List<ActivityComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody ActivityComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);

//        UcenterClient ucenterInfo = ucenterClient.getInfo(memberId);
//
//        comment.setUsername(ucenterInfo.getInfo());
//        comment.setAvatar(ucenterInfo.getAvatar());

        commentService.save(comment);
        return R.ok();
    }
}

