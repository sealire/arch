package org.leesia.statix.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 14:47
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentProxyTest {

    @Test
    public void testEat() {
        Person person = new Student("张三");
        Person proxy = new StudentProxy(person);
        proxy.eat();
    }
}
