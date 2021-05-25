package com.bluesky.ucenter.mapper;

import com.bluesky.ucenter.entity.Volunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bluesky
 * @since 2021-02-22
 */
public interface VolunteerMapper extends BaseMapper<Volunteer> {
    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
