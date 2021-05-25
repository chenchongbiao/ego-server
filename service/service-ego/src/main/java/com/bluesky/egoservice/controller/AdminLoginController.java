package com.bluesky.egoservice.controller;

import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(value = "管理员登录管理")
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了
@RequestMapping("/egoservice/user")//请求的地址
//@CrossOrigin//解决跨域问题
public class AdminLoginController {
    //login,处理post请求
    @PostMapping("login")
    public R login(){
        //返回token
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        //返回角色，名字，头像
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=704976227,1845688897&fm=11&gp=0.jpg");
    }
}
