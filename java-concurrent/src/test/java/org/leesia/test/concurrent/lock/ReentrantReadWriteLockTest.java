package org.leesia.test.concurrent.lock;

import org.leesia.concurrent.lock.ReentrantReadWriteLockService;
import org.leesia.concurrent.vo.Person;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @ClassName: ReentrantReadWriteLockTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/9 16:14
 */
public class ReentrantReadWriteLockTest {
    private static Logger LOGGER = LoggerFactory.getLogger(ReentrantReadWriteLockTest.class);

    private static ReentrantReadWriteLockService reentrantReadWriteLockService = new ReentrantReadWriteLockService();

    public static void main(String[] args) {
        test_read_write_lock();

        LOGGER.info("main exit");
    }

    public static void test_read_write_lock() {
        Cache cache = new Cache();
        Task<Cache, Integer> setTask = new Task<>(c -> {
            int size = RandomUtil.randomInt(1000, 10000, true);
            for (int i = 0; i < size; i++) {
                ThreadUtil.sleep(RandomUtil.randomLong(1000, 3000, false));

                String key = "key-" + RandomUtil.randomInt(0, 100, true);
                Person person = new Person(RandomUtil.randomString(10), RandomUtil.randomInt(10, 100, true), RandomUtil.randomString(20));
//                LOGGER.info("set {} -> {}", key, person);

                c.set(key, person);
            }

            return size;
        }, cache);

        Task<Cache, Integer> getTask = new Task<>(c -> {
            int size = RandomUtil.randomInt(1000, 10000, true);
            for (int i = 0; i < size; i++) {
                ThreadUtil.sleep(RandomUtil.randomLong(100, 300, false));

                String key = "key-" + RandomUtil.randomInt(0, 100, true);
                Person person = (Person) c.get(key);
//                LOGGER.info("get {} -> {}", key, person);
            }
            return size;
        }, cache);

        Task<ReentrantReadWriteLockService, Integer> statisticTask = new Task<>(s -> {
            for (int i = 0; i < 10000; i++) {
                ThreadUtil.sleep(500);

                LOGGER.info("write locked : {}, read lock count: {}, has thread wait: {}", s.isWriteLocked(), s.getReadLockCount(), s.hasQueuedThreads());
            }

            return 0;
        }, reentrantReadWriteLockService);

        ThreadUtil.run(2, setTask);
        ThreadUtil.run(10, getTask);
        ThreadUtil.run(1, statisticTask);
    }

    static class Cache {
        private Map<String, Object> cache = new HashMap<>();

        private Lock readLock = reentrantReadWriteLockService.readLock();

        private Lock writeLock = reentrantReadWriteLockService.writeLock();

        public Object get(String key) {
            readLock.lock();
            ThreadUtil.sleep(RandomUtil.randomLong(10, 100, true));

            try {
                return cache.get(key);
            } catch (Exception e) {
                LOGGER.error("get cache error: {}", e.getMessage(), e);
            } finally {
                readLock.unlock();
            }
            return null;
        }

        public Object set(String key, Object value) {
            writeLock.lock();
            ThreadUtil.sleep(RandomUtil.randomLong(300, 1500, true));

            try {
                return cache.put(key, value);
            } catch (Exception e) {
                LOGGER.error("set cache error: {}", e.getMessage(), e);
            } finally {
                writeLock.unlock();
            }
            return null;
        }
    }
}