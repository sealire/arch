package org.leesia.concurrent.utility;

import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: leesia
 * @Date: 2019/10/19 22:18
 * @Description:
 */
public class ExchangerTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ExchangerTest.class);

    private static ExchangerService<String> exchangerService = new ExchangerService<>();

    public static void main(String[] args) {
        test_timeout_exchange();

        LOGGER.info("main exit");
    }

    /**
     * 10个线程间交换数据
     */
    public static void test_exchange() {
        ThreadUtil.run(10, x ->  {
            try {
                String result = exchangerService.exchange(Thread.currentThread().getName());

                LOGGER.info("Thread: {} exchange, get {}", Thread.currentThread().getName(), result);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 10个线程间交换数据
     */
    public static void test_timeout_exchange() {
        ThreadUtil.run(10, x ->  {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 5000, true));

                String result = exchangerService.exchange(Thread.currentThread().getName(), RandomUtil.randomLong(0, 5000, true), TimeUnit.MILLISECONDS);

                LOGGER.info("Thread: {} exchange, get {}", Thread.currentThread().getName(), result);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            } catch (TimeoutException e) {
                LOGGER.error("Thread: {} timeout", Thread.currentThread().getName());
            }
            return x;
        });
    }
}
