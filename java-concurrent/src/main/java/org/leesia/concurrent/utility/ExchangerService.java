package org.leesia.concurrent.utility;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: leesia
 * @Date: 2019/10/19 22:00
 * @Description:
 */
public class ExchangerService<T> {

    private Exchanger<T> exchanger = new Exchanger<>();

    /**
     * 交换数据
     *
     * @param t
     * @return
     * @throws InterruptedException
     */
    public T exchange(T t) throws InterruptedException {
        return exchanger.exchange(t);
    }

    /**
     * 交换数据，最大等待timeout时间
     *
     * @param t
     * @param timeout
     * @param unit
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public T exchange(T t, long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
        return exchanger.exchange(t, timeout, unit);
    }
}
