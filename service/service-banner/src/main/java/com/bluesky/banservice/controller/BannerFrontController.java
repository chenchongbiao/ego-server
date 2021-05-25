package com.bluesky.banservice.controller;


import com.bluesky.banservice.entity.Banner;
import com.bluesky.banservice.service.BannerService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台banner显示
 * </p>
 *
 * @author bluesky
 * @since 2021-02-21
 */
@Api(value="banner显示")
@RestController
@RequestMapping("/banservice/bannerfront")
//@CrossOrigin//解决跨域
public class BannerFrontController {
    @Autowired
    private BannerService bannerService;

    //查询所有banner

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<Banner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

