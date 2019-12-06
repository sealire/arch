package org.leesia.concurrent.basic;

/**
 * @ClassName: ThreadLocalService
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 16:35
 */
public class ThreadLocalService<E> {

    private ThreadLocal<E> threadLocal;

    public ThreadLocalService() {
        threadLocal = ThreadLocal.withInitial(() -> null);
    }

    /**
     * 设值
     *
     * @param e
     */
    public void set(E e) {
        threadLocal.set(e);
    }

    /**
     * 取值
     *
     * @return
     */
    public E get() {
        return threadLocal.get();
    }
}
