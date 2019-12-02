package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.DelayQueueService;
import org.leesia.util.RandomUtil;
import org.leesia.util.base.BaseObject;
import org.leesia.util.date.LDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DelayQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 17:08
 */
public class DelayQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(DelayQueueTest.class);

    private static DelayQueueService<DelayTask> delayQueueService = new DelayQueueService<>();

    public static void main(String[] args) throws Exception {
        test_delay();

        LOGGER.info("main exit");
    }

    public static void test_delay() {
        for (int i = 0; i < 20; i++) {
            try {
                DelayTask delayTask = new DelayTask(RandomUtil.randomString(10), RandomUtil.randomLong(1000, 5000, true));

                delayQueueService.add(delayTask);
                LOGGER.info("add ele: {} on {}", delayTask, LDateUtil.format(new Date(), null));
            } catch (Exception e) {
                LOGGER.error("queue add error: {}", e.getMessage(), e);
            }
        }

        for (int i = 0; i < 20; i++) {
            try {
                DelayTask ele = delayQueueService.take();
                LOGGER.info("ele take: {} on {}", ele, LDateUtil.format(new Date(), null));
            } catch (Exception e) {
                LOGGER.error("queue take error: {}", e.getMessage(), e);
            }
        }
    }
}

class DelayTask extends BaseObject implements Delayed {

    private String taskName;

    private long delay;

    public DelayTask(String taskName, long delay) {
        super();

        this.taskName = taskName;
        this.delay = delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        }

        return 0;
    }
}
