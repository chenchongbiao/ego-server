package com.bluesky.ucenter.service;

import com.bluesky.ucenter.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bluesky.ucenter.entity.vo.LoginVo;
import com.bluesky.ucenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-02-22
 */
public interface VolunteerService extends IService<Volunteer> {
    //登录的方法
    String login(LoginVo loginVo, HttpServletRequest request);

    //注册的方法
    boolean register(RegisterVo registerVo);

    //根据openid判断
    Volunteer getOpenIdMember(String openid);

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
