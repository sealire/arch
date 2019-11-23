package org.leesia.test.concurrent.executor;

import org.leesia.concurrent.executor.CompletionService;
import org.leesia.concurrent.taskfactory.CallableFactory;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.concurrent.taskfactory.FunctionFactory;
import org.leesia.concurrent.vo.Result;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class CompletionServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(CompletionServiceTest.class);

    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    private static CompletionService completionService = new CompletionService(executor);


    public static void main(String[] args) throws Exception {

        test_submit_runnable_10();

        executor.shutdown();

        LOGGER.info("main exit");
    }

    /**
     * 10个任务，通过Callable提交后，依次take已返回的任务结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test_take_10() throws ExecutionException, InterruptedException {

        submit(completionService, 10, FunctionFactory.newSleepRandomFunction(1000, 10000));

        take(completionService, 10);
    }

    /**
     * 10个任务，通过Callable提交后，依次poll已返回的任务结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test_poll_10() throws ExecutionException, InterruptedException {

        submit(completionService, 10, FunctionFactory.newSleepRandomFunction(1000, 10000));

        poll(completionService, 10);

        submit(completionService, 10, FunctionFactory.newSleepRandomFunction(1000, 10000));

        poll_timeout(completionService, 10);
    }

    /**
     * 10个任务，通过Runnable提交后，依次获取已返回的任务结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test_submit_runnable_10() throws ExecutionException, InterruptedException {
        Map<Integer, Future> futures = new HashMap<>();
        Map<Integer, Result> results = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            int taskId = RunnableFactory.getTaskId(null);
            Result result = new Result();
            futures.put(taskId, completionService.submit(RunnableFactory.newCustomRunnable(taskId, o -> {
                Result r = (Result) o;
                r.setNum(taskId);
                r.setResult("" + RandomUtil.randomInt(0, 100, true));
                return r;
            }, result), result));
            results.put(taskId, result);
        }

//        for (Map.Entry<Integer, Future> entry : futures.entrySet()) {
//            LOGGER.info("task: {} return {}", entry.getKey(), entry.getValue().get());
//        }

        for (Map.Entry<Integer, Result> entry : results.entrySet()) {
            LOGGER.info("task: {} return {}", entry.getKey(), entry.getValue());
        }
    }

    private static void submit(CompletionService completionService, int threadCount, Function function) {
        for (int i = 0; i < threadCount; i++) {
            if (function == null) {
                completionService.submit(CallableFactory.newBlankCallable(null));
            } else {
                completionService.submit(CallableFactory.newCallable(function, null));
            }
        }
    }

    private static void take(CompletionService completionService, int threadCount) throws InterruptedException, ExecutionException {
        for (int i = 0; i < threadCount; i++) {
            LOGGER.info("task return {}", completionService.take().get());
        }
    }

    private static void poll(CompletionService completionService, int threadCount) throws ExecutionException, InterruptedException {
        for (int i = 0; i < threadCount; i++) {
            ThreadUtil.sleepRandom(1000, 3000);

            Future future = completionService.poll();
            if (future == null) {
                LOGGER.warn("no task return");
            } else {
                LOGGER.info("task return {}", future.get());
            }
        }
    }

    private static void poll_timeout(CompletionService completionService, int threadCount) throws ExecutionException, InterruptedException {
        for (int i = 0; i < threadCount; i++) {
            Future future = completionService.poll(RandomUtil.randomLong(100, 1000, true), TimeUnit.MILLISECONDS);
            if (future == null) {
                LOGGER.warn("no task return");
            } else {
                LOGGER.info("task return {}", future.get());
            }
        }
    }
}
