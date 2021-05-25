package com.bluesky.egoservice.service.impl;

import com.bluesky.egoservice.entity.Admin;
import com.bluesky.egoservice.mapper.AdminMapper;
import com.bluesky.egoservice.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  管理员服务实现类
 * </p>
 *
 * @author bluesky
 * @since 2021-03-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
