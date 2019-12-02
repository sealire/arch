package org.leesia.concurrent.vo;

import org.leesia.util.base.BaseObject;

/**
 * @ClassName: Person
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 10:28
 */
public class Person extends BaseObject implements Comparable<Person> {

    private String name;

    private int age;

    private String address;

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public int compareTo(Person o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
