package com.bluesky.egoservice.controller;


import com.bluesky.egoservice.entity.Admin;
import com.bluesky.egoservice.entity.Volunteer;
import com.bluesky.egoservice.service.VolunteerService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Api(value = "志愿者管理")
@RestController
@RequestMapping("/egoservice/volunteer")
//@CrossOrigin//解决跨域
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;
    // 查询志愿者表所有数据
    //rest风格
    @ApiOperation(value = "所有志愿者列表")
    @GetMapping("findAll")
    public R findAllVolunteer() {
        //调用service的方法实现查询所有的操作
        List<Volunteer> list = volunteerService.list(null);
        return R.ok().data("items",list);

    }
}

