package com.bluesky.banservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.banservice.entity.Banner;
import com.bluesky.banservice.mapper.BannerMapper;
import com.bluesky.banservice.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    //查询所有banner，Cacheable使用在查询方法上
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<Banner> selectAllBanner() {

        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        // 根据id降序排序
        wrapper.orderByDesc("id");
        //last方法，结尾拼接sql语句，显示前两条
        wrapper.last("limit 2");
        List<Banner> list = baseMapper.selectList(null);
        return list;
    }
}
