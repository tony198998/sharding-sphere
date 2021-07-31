package com.wode;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: jitao
 * @createDate: 2021/7/30
 */
@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
@MapperScan(basePackages = "com.wode.mapper")
public class ReadWriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteApplication.class, args);
    }
}
