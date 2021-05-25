package com.bluesky.egoservice.service;

import com.bluesky.egoservice.entity.ActivitySubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bluesky.egoservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
public interface ActivitySubjectService extends IService<ActivitySubject> {
    //添加活动分类
    void saveSubject(MultipartFile file, ActivitySubjectService subjectService);

    //活动分类列表（树形）
    List<OneSubject> getAllOneTwoSubject();
}
