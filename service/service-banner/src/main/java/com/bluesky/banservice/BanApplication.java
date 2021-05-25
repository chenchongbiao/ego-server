package com.bluesky.banservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient // nacos注册

@ComponentScan(basePackages = {"com.bluesky"})
public class BanApplication {
    public static void main(String[] args) {
        SpringApplication.run(BanApplication.class, args);
    }
}
