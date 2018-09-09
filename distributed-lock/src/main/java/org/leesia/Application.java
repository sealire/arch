package org.leesia;

import org.leesia.kill.SecKill;
import org.leesia.redis.RedisService;
import org.leesia.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: leesia
 * @Date: 2018/9/6 15:54
 * @Description:
 */
@EnableCaching
@ComponentScan(basePackages = {"org.leesia"})
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        SpringUtil.setApplicationContext(ctx);

//        init();
        seckill();
    }

    public static void init() {
        RedisService redisService = (RedisService) SpringUtil.getBean("redisService");
        redisService.set("p1", "" + 1000, null);
    }

    public static void seckill() {
        SecKill secKill = (SecKill) SpringUtil.getBean("secKill");
        for (int i = 0; i < 1000; i++) {
            SK sk = new SK(secKill, "c" + i, "p1");
            new Thread(sk).start();
        }
    }
}

class SK implements Runnable {

    private SecKill sk;

    private String cid;

    private String pid;

    public SK(SecKill sk, String cid, String pid) {
        this.sk = sk;
        this.cid = cid;
        this.pid = pid;
    }

    @Override
    public void run() {
        sk.seckill(cid, pid, 5000);
    }
}
