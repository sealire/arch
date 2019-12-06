package org.leesia.test.concurrent.basic;

import org.leesia.concurrent.basic.VolatileService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: VolatileTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 17:25
 */
public class VolatileTest {

    private static Logger LOGGER = LoggerFactory.getLogger(VolatileTest.class);

    private static VolatileService<Boolean> volatileService = new VolatileService<>(new Boolean(false));

    public static void main(String[] args) throws Exception {
        test_volatile();

        LOGGER.info("main exit");
    }

    public static void test_volatile() throws InterruptedException {
        Task<VolatileService<Boolean>, Boolean> countTask = new Task<>(service -> {
            volatileService.setTarget(false);

            int sec = RandomUtil.randomInt(10, 20, true);
            for (int i = 0; i < sec; i++) {
                LOGGER.info("time: {}s", i);

                ThreadUtil.sleep(1000);
            }

            volatileService.setTarget(true);

            return volatileService.getTarget();
        }, volatileService);

        Task<VolatileService<Boolean>, Long> accTask = new Task<>(service -> {
            Long total = 0L;

            while (!volatileService.getTarget()) {
                total++;

                LOGGER.info("total: {}", total);

                ThreadUtil.sleep(10);
            }

            return total;
        }, volatileService);

        Thread thread1 = ThreadFactory.newThread(countTask);
        Thread thread2 = ThreadFactory.newThread(accTask);

        thread1.start();
        thread2.start();

        thread2.join();

        LOGGER.info("total: {}", accTask.getResult());
    }
}
