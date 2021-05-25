package com.bluesky.banservice.service;

import com.bluesky.banservice.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
public interface BannerService extends IService<Banner> {
    //查询所有banner
    List<Banner> selectAllBanner();
}
