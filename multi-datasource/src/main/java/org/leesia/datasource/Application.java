package org.leesia.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 14:34
 * @Description:
 */
@EnableCaching
@ComponentScan(basePackages = {"org.leesia"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
