package com.zhishi.picture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.zhishi.picture.mapper")
@SpringBootApplication
@EnableAsync
public class ZhishiPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhishiPictureApplication.class, args);
    }

}
