package com.bluesky.ucenter.client;

import com.bluesky.utils.ApplyInfo.ActivityWebApplyInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ego")
public interface EgoClient {

    // 根据活动id查询活动信息
    @PostMapping("/egoservice/activityfront/getActivityApplyInfo/{activityId}")
    public ActivityWebApplyInfo getActivityApplyInfo(@PathVariable("activityId") String activityId);
}
