package com.bluesky.egoservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Team;
import com.bluesky.egoservice.mapper.TeamMapper;
import com.bluesky.egoservice.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    //分页查询组织的方法
    @Override
    public Map<String, Object> getTeamFrontList(Page<Team> pageParam) {
        // 构建条件
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        // 根据讲师的id降序排序
        wrapper.orderByDesc("id");
        // 把分页数据封装到pageParam对象
        baseMapper.selectPage(pageParam,wrapper);
        // 组织数据
        List<Team> records = pageParam.getRecords();
        // 当前页
        long current = pageParam.getCurrent();
        // 总页数
        long pages = pageParam.getPages();
        // 每页记录数
        long size = pageParam.getSize();
        // 总记录数
        long total = pageParam.getTotal();
        // 是否有下页
        boolean hasNext = pageParam.hasNext();
        // 是否有上页
        boolean hasPrevious = pageParam.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }


}
