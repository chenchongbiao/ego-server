package com.bluesky.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.ucenter.entity.ActivityApply;
import com.bluesky.ucenter.entity.Volunteer;
import com.bluesky.ucenter.entity.vo.LoginVo;
import com.bluesky.ucenter.entity.vo.RegisterVo;
import com.bluesky.ucenter.service.VolunteerService;
import com.bluesky.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bluesky.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-02-22
 */
@Api(value = "用户管理")
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了
@RequestMapping("/ucenter/volunteer")
//@CrossOrigin
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;
    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        if(volunteerService.register(registerVo)){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //登录
    @ApiOperation(value = "志愿者登录")
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo loginVo,HttpServletRequest request) {
        //member对象封装学号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = volunteerService.login(loginVo,request);
        return R.ok().data("token",token);
    }

    // 查询志愿者所有数据
    @ApiOperation(value = "所有志愿者列表")
    @GetMapping("findAllVolunteer")
    public R findAllVolunteer() {
        //构建条件
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        wrapper.select("id","username");
        //调用service的方法实现查询所有的操作
        List<Volunteer> list = volunteerService.list(wrapper);
        return R.ok().data("items",list);

    }

    // 根据token获取用户信息
    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String Id = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        Volunteer member = volunteerService.getById(Id);
        return R.ok().data("userInfo",member);
    }

    // 根据用户id获取用户信息
    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("getInfoUc/{id}")
    public Volunteer getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        Volunteer volunteer = volunteerService.getById(id);
        return volunteer;
    }

}

