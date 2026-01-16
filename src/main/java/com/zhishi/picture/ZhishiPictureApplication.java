package com.zhishi.picture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.zhishi.picture.mapper")
@SpringBootApplication
public class ZhishiPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhishiPictureApplication.class, args);
    }

}
