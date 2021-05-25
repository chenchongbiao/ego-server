package com.bluesky.egoservice.client;

import com.bluesky.egoservice.entity.Volunteer;
import com.bluesky.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// 配置文件中的服务名称

@FeignClient(name = "service-ucenter", fallback = UcenterFileDegradeFeignClient.class)
@Component
public interface UcenterClient {
    // 定义调用的方法的路径
    // 根据用户id获取用户信息
    // @PathVariable注解一定要指定参数名称，否则出错
    @PostMapping("/ucenter/volunteer/getInfoUc/{id}")
    public R getInfo(@PathVariable("id") String id);
}


