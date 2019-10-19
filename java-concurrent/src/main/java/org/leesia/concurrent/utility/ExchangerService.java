package org.leesia.concurrent.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: leesia
 * @Date: 2019/10/19 22:00
 * @Description:
 */
public class ExchangerService<T> {

    private static Logger LOGGER = LoggerFactory.getLogger(ExchangerService.class);

    private Exchanger<T> exchanger = new Exchanger<>();

    /**
     * 交换数据
     *
     * @param t
     * @return
     * @throws InterruptedException
     */
    public T exchange(T t) throws InterruptedException {
        LOGGER.info("Thread: {} exchange {}", Thread.currentThread().getName(), t);

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
        LOGGER.info("Thread: {} exchange {}", Thread.currentThread().getName(), t);

        return exchanger.exchange(t, timeout, unit);
    }
}
