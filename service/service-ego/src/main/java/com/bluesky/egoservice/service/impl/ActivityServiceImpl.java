package com.bluesky.egoservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bluesky.baseservice.exceptionhandler.myException;
import com.bluesky.egoservice.entity.Activity;
import com.bluesky.egoservice.entity.ActivityIntro;
import com.bluesky.egoservice.entity.frontvo.ActivityFrontVo;
import com.bluesky.egoservice.entity.frontvo.ActivityWebVo;
import com.bluesky.egoservice.entity.vo.ActivityInfoVo;
import com.bluesky.egoservice.entity.vo.ActivityPublishVo;
import com.bluesky.egoservice.mapper.ActivityMapper;
import com.bluesky.egoservice.service.ActivityIntroService;
import com.bluesky.egoservice.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动 服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    //活动描述注入
    @Autowired
    private ActivityIntroService activityIntroService;

    //添加活动基本信息的方法
    @Override
    public String saveActivityInfo(ActivityInfoVo activityInfoVo) {
        //1 向活动表添加活动基本信息
        //ActivityInfoVo对象转换Activity对象
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityInfoVo,activity);

        int insert = baseMapper.insert(activity);
        if(insert == 0) {
            //添加失败
            throw new myException(20001,"添加活动信息失败");
        }

        //获取添加之后活动id
        String cid = activity.getId();

        //2 向活动简介表添加活动简介
        //activityIntro
        ActivityIntro activityIntro = new ActivityIntro();
        activityIntro.setIntro(activityInfoVo.getIntro());
        //设置描述id就是活动id
        activityIntro.setId(cid);
        activityIntroService.save(activityIntro);

        return cid;
    }
    //根据活动id查询活动基本信息
    @Override
    public ActivityInfoVo getActivityInfo(String activityId) {
        //1 查询活动表
        Activity activity = baseMapper.selectById(activityId);
        ActivityInfoVo activityInfoVo = new ActivityInfoVo();
        BeanUtils.copyProperties(activity,activityInfoVo);

        //2 查询描述表
        ActivityIntro activityIntro = activityIntroService.getById(activityId);
        activityInfoVo.setIntro(activityIntro.getIntro());

        return activityInfoVo;
    }

    //修改活动信息
    @Override
    public void updateActivityInfo(ActivityInfoVo activityInfoVo) {
        //1 修改活动表
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityInfoVo,activity);
        int update = baseMapper.updateById(activity);
        if(update == 0) {
            throw new myException(20001,"修改活动信息失败");
        }

        //2 修改描述表
        ActivityIntro activityIntro = new ActivityIntro();
        activityIntro.setId(activityInfoVo.getId());
        activityIntro.setIntro(activityInfoVo.getIntro());
        activityIntroService.updateById(activityIntro);
    }

    //根据活动id查询活动确认信息
    @Override
    public ActivityPublishVo publishActivityInfo(String id) {
        //调用mapper
        ActivityPublishVo publishActivityeInfo = baseMapper.getActivityPublishInfo(id);
        return publishActivityeInfo;
    }

    //删除活动
    @Override
    public void removeActivity(String activityeId) {
        //1 根据活动id删除描述
        activityIntroService.removeById(activityeId);

        //2 根据活动id删除活动本身
        int result = baseMapper.deleteById(activityeId);
        if(result == 0) { //失败返回
            throw new myException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getActivityFrontList(Page<Activity> pageParam, ActivityFrontVo activityFrontVo) {
        // 根据讲师id查询所讲活动
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(activityFrontVo.getSubjectParentId())) {
            // 如果相等一级分类相等
            wrapper.eq("subject_parent_id",activityFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(activityFrontVo.getSubjectId())) {
            // 如果二级分类相等
            wrapper.eq("subject_id",activityFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(activityFrontVo.getApplyCountSort())) {
            // 按照参与人数降序排序
            wrapper.orderByDesc("apply_count");
        }
        if (!StringUtils.isEmpty(activityFrontVo.getGmtCreateSort())) {
            // 按照发布时间降序
            wrapper.orderByDesc("gmt_create");
        }
        // 把分页数据封装到pageParam对象
        baseMapper.selectPage(pageParam,wrapper);
        List<Activity> records = pageParam.getRecords();
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

    @Override
    public ActivityWebVo getBaseActivityInfo(String id) {
        return baseMapper.getBaseActivityInfo(id);
    }

}
