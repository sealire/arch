package org.leesia.proxy.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Auther: leesia
 * @Date: 2018/9/7 18:42
 * @Description:
 */
public class StudentInvocationHandler<T> implements MyInvocationHandler {

    private final static Logger logger = LoggerFactory.getLogger(StudentInvocationHandler.class);

    private T target;

    public Object getInstance(T target){
        this.target = target;
        Class clazz = target.getClass();
        Object obj = MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("proxy exec");
        Object result = method.invoke(target, args);
        return result;
    }
}
