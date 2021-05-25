package com.bluesky.egoservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bluesky.egoservice.entity.ActivitySubject;
import com.bluesky.egoservice.entity.excel.SubjectData;
import com.bluesky.egoservice.entity.subject.OneSubject;
import com.bluesky.egoservice.entity.subject.TwoSubject;
import com.bluesky.egoservice.listener.SubjectExcelListener;
import com.bluesky.egoservice.mapper.ActivitySubjectMapper;
import com.bluesky.egoservice.service.ActivitySubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 活动程目 服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Service
public class ActivitySubjectServiceImpl extends ServiceImpl<ActivitySubjectMapper, ActivitySubject> implements ActivitySubjectService {
    //添加活动分类
    @Override
    public void saveSubject(MultipartFile file, ActivitySubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //课程活动列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<ActivitySubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<ActivitySubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类  parentid != 0
        QueryWrapper<ActivitySubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<ActivitySubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) { //遍历oneSubjectList集合
            //得到oneSubjectList每个acitvitySubject对象
            ActivitySubject acitvitySubject = oneSubjectList.get(i);
            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(acitvitySubject.getId());
//            oneSubject.setTitle(acitvitySubject.getTitle());
            //acitvitySubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(acitvitySubject,oneSubject);
            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                ActivitySubject tSubject = twoSubjectList.get(m);
                //判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(acitvitySubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
