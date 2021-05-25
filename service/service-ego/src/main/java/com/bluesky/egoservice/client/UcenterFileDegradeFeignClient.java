package com.bluesky.egoservice.client;

import com.bluesky.utils.R;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient{
    // 获取用户信息出错时才会执行
    @Override
    public R getInfo(String id) {
        return R.error().message("time out");
    }
}
