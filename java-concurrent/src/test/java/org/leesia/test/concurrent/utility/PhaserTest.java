package org.leesia.test.concurrent.utility;

import org.leesia.concurrent.utility.PhaserService;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PhaserTest {

    private static Logger LOGGER = LoggerFactory.getLogger(PhaserTest.class);

    private static PhaserService phaserService3 = new PhaserService(3);

    private static PhaserService phaserService10 = new PhaserService(10);

    public static void main(String[] args) throws Exception {
        test_termination();

        LOGGER.info("main exit");
    }

    /**
     * 3个线程等待，屏障等待3个线程
     */
    public static void test_arrive3_3() {
        ThreadUtil.run(3, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
    }

    /**
     * 2个线程等待，屏障等待3个线程
     */
    public static void test_arrive3_2() {
        ThreadUtil.run(2, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
    }

    /**
     * 4个线程等待，屏障等待3个线程
     */
    public static void test_arrive3_4() {
        ThreadUtil.run(4, x -> {
            LOGGER.info("Thread: {} await, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService3.getPhase(), phaserService3.getArrivedParties());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService3.getPhase(), phaserService3.getArrivedParties());
            return x;
        });
    }

    /**
     * 10个线程等待，屏障等待10个线程，两层屏障
     */
    public static void test_arrive10_10_2() {
        ThreadUtil.run(10, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 1000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} await, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());

            phaserService10.arriveAndAwaitAdvance();

            try {
                Thread.sleep(RandomUtil.randomLong(0, 1000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} continue, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());

            phaserService10.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} end, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());
            return x;
        });
    }

    /**
     * 10个线程等待，屏障等待10个线程，线程到达后随机退出
     */
    public static void test_deregister10_1() {
        ThreadUtil.run(10, x -> {
            LOGGER.info("Thread: {} await, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());
            if (RandomUtil.randomProb(0.3)) {
                // 30%的概率退出
                LOGGER.info("Thread: {} deregister, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());
                phaserService10.arriveAndDeregister();
            } else {
                phaserService10.arriveAndAwaitAdvance();
            }
            LOGGER.info("Thread: {} continue, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());
            return x;
        });
    }

    /**
     * 添加parties
     */
    public static void test_register() {
        LOGGER.info("Thread: {}, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());

        phaserService10.register();

        LOGGER.info("Thread: {}, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());

        phaserService10.bulkRegister(9);

        LOGGER.info("Thread: {}, parties: {}", Thread.currentThread().getName(), phaserService10.getRegisteredParties());
    }

    /**
     * 获取已经到达、未到达的parties数
     */
    public static void test_arrived_parties() {
        ThreadUtil.run(10, x -> {
            LOGGER.info("Thread: {} await, arrived parties: {}, unarrived parties: {}", Thread.currentThread().getName(), phaserService10.getArrivedParties(), phaserService10.getUnarrivedParties());

            phaserService10.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            return x;
        });
    }

    /**
     * 3个线程，屏障等待3个线程，线程到达后不等待
     */
    public static void test_arrive_3() throws InterruptedException {
        Thread[] threads = ThreadUtil.run(3, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 1000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} await, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService3.getPhase(), phaserService10.getArrivedParties());

            phaserService3.arrive();

            LOGGER.info("Thread: {} continue, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService3.getPhase(), phaserService10.getArrivedParties());
            return x;
        });

        for (Thread thread : threads) {
            thread.join();
        }

        LOGGER.info("arrived parties: {}", phaserService10.getArrivedParties());
    }

    /**
     * 10个线程，屏障等待10个线程，线程到达后不等待
     */
    public static void test_arrive_10() throws InterruptedException {
        Thread[] threads = ThreadUtil.run(10, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 1000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} await, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());

            phaserService10.arrive();

            try {
                Thread.sleep(RandomUtil.randomLong(0, 1000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} continue, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());

            phaserService10.arrive();

            LOGGER.info("Thread: {} end, phase: {}, arrived parties: {}", Thread.currentThread().getName(), phaserService10.getPhase(), phaserService10.getArrivedParties());
            return x;
        });

        for (Thread thread : threads) {
            thread.join();
        }

        LOGGER.info("arrived parties: {}", phaserService10.getArrivedParties());
    }

    /**
     * 2个线程等待，屏障等待3个线程，再起一个观察线程等待，之后中断观察线程，最后起一个线程结束等待
     */
    public static void test_await_advance() throws InterruptedException {
        // 先起两个线程，到达等待
        ThreadUtil.run(2, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 再起一个观察线程，到达等待
        Thread[] threads = ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await at phase: 0", Thread.currentThread().getName());

            phaserService3.awaitAdvance(0);

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));
        // 中断观察线程
        for (Thread thread : threads) {
            thread.interrupt();
        }
        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 起一个不等待的观察线程
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await at phase: 1", Thread.currentThread().getName());

            phaserService3.awaitAdvance(1);

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 最后起一个线程，结束等待
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
    }

    /**
     * 2个线程等待，屏障等待3个线程，再起一个观察线程等待，之后中断观察线程，最后起一个线程结束等待
     */
    public static void test_await_advance_interruptibly() throws InterruptedException {
        // 先起两个线程，到达等待
        ThreadUtil.run(2, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 再起一个观察线程，到达等待
        Thread[] threads = ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await at phase: 0", Thread.currentThread().getName());

            try {
                phaserService3.awaitAdvanceInterruptibly(0);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));
        // 中断观察线程
        for (Thread thread : threads) {
            thread.interrupt();
        }
        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 起一个不等待的观察线程
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await at phase: 1", Thread.currentThread().getName());

            phaserService3.awaitAdvance(1);

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 最后起一个线程，结束等待
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
    }

    /**
     * 2个线程等待，屏障等待3个线程，再起一个观察线程等待，之后中断观察线程，最后起一个线程结束等待
     */
    public static void test_await_timeout_advance_interruptibly() throws InterruptedException {
        // 先起两个线程，到达等待
        ThreadUtil.run(2, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(RandomUtil.randomLong(2000, 4000, true));

        // 再起4个观察线程，随机等待[1000, 3000]ms
        ThreadUtil.run(4, x -> {
            LOGGER.info("Thread: {} await at phase: 0", Thread.currentThread().getName());

            try {
                long wait = RandomUtil.randomLong(0, 4000, true);
                LOGGER.error("Thread: {} wait: {}ms", Thread.currentThread().getName(), wait);
                phaserService3.awaitAdvanceInterruptibly(0, wait, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            } catch (TimeoutException e) {
                LOGGER.error("Thread: {} timeout", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });

        Thread.sleep(2000);

        // 最后起一个线程，结束等待
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());
            return x;
        });
    }

    /**
     * @throws InterruptedException
     */
    public static void test_termination() throws InterruptedException {
        ThreadUtil.run(2, x -> {
            LOGGER.info("Thread: {} await, phase: {}", Thread.currentThread().getName(), phaserService3.getPhase());

            phaserService3.arriveAndAwaitAdvance();

            LOGGER.info("Thread: {} continue, phase: {}, termination: {}", Thread.currentThread().getName(), phaserService3.getPhase(), phaserService3.isTerminated());
            return x;
        });

        Thread.sleep(2000);

        phaserService3.forceTermination();
    }
}
