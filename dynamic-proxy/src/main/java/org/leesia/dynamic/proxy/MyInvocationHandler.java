package org.leesia.dynamic.proxy;

import java.lang.reflect.Method;

/**
 * @Auther: leesia
 * @Date: 2018/9/10 11:00
 * @Description:
 */
public interface MyInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
