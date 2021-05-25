package com.bluesky.egoservice.service.impl;

import com.bluesky.egoservice.entity.ActivityComment;
import com.bluesky.egoservice.mapper.ActivityCommentMapper;
import com.bluesky.egoservice.service.ActivityCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-07
 */
@Service
public class ActivityCommentServiceImpl extends ServiceImpl<ActivityCommentMapper, ActivityComment> implements ActivityCommentService {

}
