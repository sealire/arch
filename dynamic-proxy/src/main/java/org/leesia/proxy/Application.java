package org.leesia.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 14:34
 * @Description:
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = "org.leesia", exclude = DataSourceAutoConfiguration.class)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
