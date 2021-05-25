package com.bluesky.egoservice.controller;


import com.bluesky.egoservice.entity.subject.OneSubject;
import com.bluesky.egoservice.service.ActivitySubjectService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Api(value = "活动分类管理")
@RestController
@RequestMapping("/egoservice/subject")
//@CrossOrigin//解决跨域
public class ActivitySubjectController {
    @Autowired
    private ActivitySubjectService subjectService;

    //添加活动分类
    //获取上传过来文件，把文件内容读取出来
    @ApiOperation(value = "添加活动分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    // 活动分类（树形）
    @ApiOperation(value = "查询活动分类")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

