package com.wode;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: jitao
 * @createDate: 2021/7/28
 */

/**
 * SpringBoot本身具有自动配置，现在自动配置时发生冲突了，那么我们可以将冲突的部分排除掉，即告诉SpringBoot，某个类不用帮我自动配置了，这里我们将Druid的关于数据源的配置排除掉： exclude={DruidDataSourceAutoConfigure.class}
 * ————————————————
 * 版权声明：本文为CSDN博主「Heartsuit」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u013810234/article/details/99318113
 *
 *
 */
@SpringBootApplication(exclude={DruidDataSourceAutoConfigure.class})
@MapperScan(basePackages = "com.wode.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
