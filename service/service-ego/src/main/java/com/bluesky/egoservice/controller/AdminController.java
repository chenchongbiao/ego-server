package com.bluesky.egoservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Admin;
import com.bluesky.egoservice.entity.vo.AdminQuery;
import com.bluesky.egoservice.service.AdminService;
import com.bluesky.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Api(value = "管理员管理")
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了
@RequestMapping("/egoservice/admin") //请求的地址
//@CrossOrigin//解决跨域
public class AdminController {
    @Autowired // 注入service
    private AdminService adminService;

    // 1 添加管理员接口的方法
    @ApiOperation("添加管理员")
    @PostMapping("addAdmin")
    public R addAdmin(@RequestBody Admin admin) {
        boolean save = adminService.save(admin);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //2 逻辑删除管理员的方法
    @ApiOperation(value = "逻辑删除管理员")
    @DeleteMapping("/remove/{admin_id}") // ApiParam参数说明-name：参数 – value：参数名 – required：是否必须。默认为 true,表示请求参数中必须包含对应的参数，若不存在，将抛出异常
    public R removeAdmin(@ApiParam(name = "admin_id", value = "管理员ID", required = true)
                         @PathVariable String admin_id) {
        boolean flag = adminService.removeById(admin_id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 3 管理员修改功能
    @ApiOperation("管理员修改功能")
    @PostMapping("updateAdmin")
    public R updateAdmin(@RequestBody Admin admin) {
        boolean flag = adminService.updateById(admin);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 查询管理员表所有数据
    //rest风格
    @ApiOperation(value = "所有管理员列表")
    @GetMapping("findAll")
    public R findAllAdmin() {
        //调用service的方法实现查询所有的操作
        List<Admin> list = adminService.list(null);
        return R.ok().data("items",list);

    }


    //3 分页查询管理员的方法
    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "分页查询管理员")
    @GetMapping("pageAdmin/{current}/{limit}")
    public R pageListAdmin(@PathVariable long current,
                           @PathVariable long limit) {
        //创建page对象
        Page<Admin> pageAdmin = new Page<>(current,limit);

//        try {
//            int i = 10/0;
//        }catch(Exception e) {
//            //执行自定义异常
//            throw new GuliException(20001,"执行了自定义异常处理....");
//        }


        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        adminService.page(pageAdmin,null);

        long total = pageAdmin.getTotal();//总记录数
        List<Admin> records = pageAdmin.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "条件查询管理员带分页")
    @PostMapping("pageAdminCondition/{current}/{limit}")
    public R pageAdminCondition(@PathVariable long current, @PathVariable long limit,
                                @RequestBody(required = false) AdminQuery adminQuery) {
        //@RequestBody，使用json格式传递数据封装到对应对象，required = false表示值可以为空，必须使用post请求
        //创建page对象
        Page<Admin> pageAdmin = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String username = adminQuery.getUsername();
        String begin = adminQuery.getBegin();
        String end = adminQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(username)){
            // //构建条件，相似
            wrapper.like("username",username);
        }
//        if(!StringUtils.isEmpty(username)){
//            // //构建条件，等于
//            wrapper.eq("username",username);
//        }
        if(!StringUtils.isEmpty(begin)) {
            //大于等于
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            //小于等于
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        adminService.page(pageAdmin,wrapper);

        long total = pageAdmin.getTotal();//总记录数
        List<Admin> records = pageAdmin.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }



    //根据管理员id进行查询
    @ApiOperation("根据管理员id进行查询")
    @GetMapping("getAdmin/{admin_id}")
    public R getAdmin(@PathVariable String admin_id) {
        Admin admin = adminService.getById(admin_id);
        return R.ok().data("admin",admin);
    }
}

