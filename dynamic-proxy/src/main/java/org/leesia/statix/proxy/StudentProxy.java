package org.leesia.statix.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 14:43
 * @Description:
 */
public class StudentProxy implements Person {

    private final static Logger logger = LoggerFactory.getLogger(StudentProxy.class);

    private Student student;

    public StudentProxy(Person person) {
        if (person.getClass() == Student.class) {
            this.student = (Student) person;
        }
    }

    @Override
    public void eat() {
        logger.info("proxy eat");
        student.eat();
    }
}
