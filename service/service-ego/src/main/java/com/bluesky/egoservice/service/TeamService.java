package com.bluesky.egoservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bluesky.egoservice.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
public interface TeamService extends IService<Team> {
    // 分页查询组织的方法
    Map<String, Object> getTeamFrontList(Page<Team> pageTeam);
}
