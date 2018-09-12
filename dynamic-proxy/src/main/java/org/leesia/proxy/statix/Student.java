package org.leesia.proxy.statix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 14:41
 * @Description:
 */
public class Student implements Person {

    private final static Logger logger = LoggerFactory.getLogger(Student.class);

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void eat() {
        logger.info("{} eat", name);
        System.out.println(name + " eat");
    }
}
