package org.leesia.proxy.dynamic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 19:02
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentInvocationHandlerTest {

    @Test
    public void testEat() {
//        Person person = (Person) new StudentInvocationHandler().getInstance(new Student("张三"));
//        System.out.println(person.getClass());
//        person.eat();
//        person.sleep();
//
//        try {
//            byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("D:/$Proxy0.class");
//            os.write(data);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}