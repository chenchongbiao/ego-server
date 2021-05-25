package com.bluesky.banservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.banservice.entity.Banner;
import com.bluesky.banservice.service.BannerService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 首页banner表管理接口
 * </p>
 *
 * @author bluesky
 * @since 2021-02-21
 */
@Api(value = "banner管理")
@RestController
@RequestMapping("/banservice/banneradmin")
//@CrossOrigin//解决跨域
public class BannerAdminController {
    @Autowired
    private BannerService bannerAdminService;

    //1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<Banner> pageBanner = new Page<>(page,limit);
        bannerAdminService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //2 添加banner
    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody Banner banner) {
        bannerAdminService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        Banner banner = bannerAdminService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody Banner banner) {
        bannerAdminService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerAdminService.removeById(id);
        return R.ok();
    }
}
