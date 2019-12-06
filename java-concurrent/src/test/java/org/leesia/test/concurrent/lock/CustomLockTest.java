package org.leesia.test.concurrent.lock;

import org.leesia.concurrent.lock.CustomLock;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.base.BaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: CustomLockTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/6 10:44
 */
public class CustomLockTest {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomLockTest.class);

    private static CustomLock customLock = new CustomLock();

    public static void main(String[] args) {
        test_lock();

        LOGGER.info("main exit");
    }

    public static void test_lock() {
        Vote vote = new Vote();
        Task<Vote, Integer> task = new Task<>(v -> {
            customLock.lock();

            v.add();

            customLock.unlock();
            return v.get();
        }, vote);

        Thread[] threads = ThreadUtil.run(10000, task);

        ThreadUtil.join(threads);

        LOGGER.info("vote: {}", vote.get());
    }
}

class Vote extends BaseObject {

    private int v = 0;

    public void add() {
        v++;
    }

    public Integer get() {
        return v;
    }
}
