package com.bluesky.powerservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.bluesky"})
@MapperScan("com.bluesky.powerservice.mapper")
public class PowerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PowerApplication.class,args);
    }

}
