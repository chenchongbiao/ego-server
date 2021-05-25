package com.bluesky.egoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient // nacos注册
@EnableFeignClients // 服务调用
@ComponentScan(basePackages = {"com.bluesky"}) // 扫描com.bluesky下的所有包
public class EgoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EgoApplication.class,args);
    }
}
