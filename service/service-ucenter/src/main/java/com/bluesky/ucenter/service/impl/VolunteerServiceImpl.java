package com.bluesky.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.ucenter.entity.Volunteer;
import com.bluesky.ucenter.entity.vo.LoginVo;
import com.bluesky.ucenter.entity.vo.RegisterVo;
import com.bluesky.ucenter.mapper.VolunteerMapper;
import com.bluesky.ucenter.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bluesky.utils.JwtUtils;
import com.bluesky.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.bluesky.baseservice.exceptionhandler.myException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-02-22
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
    //登录的方法
    @Override
    public String login(LoginVo loginVo, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        //获取登录手机号、学号和密码
//        String code = loginVo.getCode();
        String stud_no = loginVo.getSNo();
        String password = loginVo.getPassword();

        //学号和密码非空判断
        if(StringUtils.isEmpty(stud_no) || StringUtils.isEmpty(password)) {
            throw new myException(20001,"用户名或密码不正确！");
        }
//        // 验证码非空判断,及验证
//        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
//            throw new myException(20001,"验证码输入错误，请重新输入！");
//        }
        //判断学号是否正确
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        wrapper.eq("s_no",stud_no);
        Volunteer studMenber = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(studMenber == null) {//没有这个学号
            throw new myException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(studMenber.getPassword())) {
            throw new myException(20001,"登录失败");
        }


        //判断用户是否禁用 1 禁用 0 未禁用
        if(studMenber.getIsDisabled()) {
            throw new myException(20001,"账号被禁用，请联系管理员！");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(studMenber.getId(), studMenber.getSNo(),studMenber.getUsername());
        return jwtToken;
    }

    //注册的方法
    @Override
    public boolean register(RegisterVo registerVo) {
        //获取注册的数据
//        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getPhone(); //手机号
        String stud_no = registerVo.getS_no(); //学号
        String nickname = registerVo.getUsername(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(stud_no) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)) {
            throw new myException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)) {
//            throw new myException(20001,"注册失败");
//        }

        //判断学号是否重复，表里面存在相同学号不进行添加
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        wrapper.eq("s_no",stud_no);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new myException(20001,"注册失败");
        }
        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<Volunteer> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("phone",mobile);
        Integer count2 = baseMapper.selectCount(wrapper2);
        if(count2 > 0) {
            throw new myException(20001,"注册失败");
        }
        //数据添加数据库中
        Volunteer member = new Volunteer();
        member.setPhone(mobile);
        member.setUsername(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setSNo(stud_no);
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
        return true;
    }

    //根据openid判断
    @Override
    public Volunteer getOpenIdMember(String openid) {
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Volunteer member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
