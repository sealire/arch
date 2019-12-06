package org.leesia.concurrent.basic;

/**
 * @ClassName: VolatileService
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 17:18
 */
public class VolatileService<E> {

    private volatile E target;

    public VolatileService(E e) {
        this.target = e;
    }

    public E getTarget() {
        return target;
    }

    public void setTarget(E target) {
        this.target = target;
    }
}
