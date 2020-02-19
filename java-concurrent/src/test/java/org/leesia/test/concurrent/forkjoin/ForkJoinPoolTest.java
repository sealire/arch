package org.leesia.test.concurrent.forkjoin;

import org.leesia.concurrent.forkjoin.CustomRecursiveAction;
import org.leesia.concurrent.forkjoin.CustomRecursiveTask;
import org.leesia.concurrent.forkjoin.ForkJoinPoolService;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.list.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @ClassName: ForkJoinPoolTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 13:01
 */
public class ForkJoinPoolTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ForkJoinPoolTest.class);

    private static ForkJoinPoolService pool = new ForkJoinPoolService(100);

    public static void main(String[] args) throws Exception {

        test_submit_RecursiveTask();

        LOGGER.info("main exit");
    }

    /**
     * 1个任务，通过Runnable执行
     */
    public static void test_execute_runnable() {
        pool.execute(RunnableFactory.newBlankRunnable(null));
    }

    /**
     * 1个任务，通过RecursiveAction执行
     */
    public static void test_execute_RecursiveAction() {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(ListUtil::sum, nums);

        CustomRecursiveAction action = new CustomRecursiveAction<>(Task::compute, task);
        pool.execute(action);
        pool.shutdown();

        ThreadUtil.sleep(5);

        LOGGER.info("result: {}", action.getTask().getResult());
    }

    /**
     * 1个任务，通过RecursiveAction执行，分解成小任务执行
     */
    public static void test_submit_RecursiveAction() {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(ListUtil::sum, nums);

        CustomRecursiveAction<List<Integer>, Long> action = new CustomRecursiveAction<>();
        action.setConsumer(new Consumer<Task<List<Integer>, Long>>() {
            @Override
            public void accept(Task<List<Integer>, Long> t) {
                splitActionTask(t, this);
            }
        });
        action.setTask(task);

        pool.submit(action);
        pool.shutdown();
    }

    /**
     * 1个任务，通过RecursiveTask执行
     */
    public static void test_execute_RecursiveTask() throws ExecutionException, InterruptedException {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(ListUtil::sum, nums);

        CustomRecursiveTask action = new CustomRecursiveTask<>(Task::compute, task);
        pool.execute(action);
        pool.shutdown();

        LOGGER.info("result: {}", action.get());
        LOGGER.info("result: {}", action.join());
    }

    /**
     * 1个任务，通过RecursiveTask执行，分解成小任务执行
     */
    public static void test_submit_RecursiveTask() {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(ListUtil::sum, nums);

        CustomRecursiveTask<List<Integer>, Long> action = new CustomRecursiveTask<>();
        action.setFunction(new Function<Task<List<Integer>, Long>, Long>() {
            @Override
            public Long apply(Task<List<Integer>, Long> t) {
                return splitTask(t, this);
            }
        });
        action.setTask(task);

        pool.submit(action);
        pool.shutdown();

        LOGGER.info("result: {}", action.join());
    }

    private static void splitActionTask(Task<List<Integer>, Long> task, Consumer<Task<List<Integer>, Long>> consumer) {
        List<Integer> list = task.getParam();
        if (list == null || list.isEmpty()) {
            return;
        }

        if (list.size() <= 10) {
            task.compute();
            return;
        }

        List<Integer> subLeft = new ArrayList<>();
        List<Integer> subRight = new ArrayList<>();
        int mid = list.size() / 2;
        for (int i = 0; i < list.size(); i++) {
            if (i < mid) {
                subLeft.add(list.get(i));
            } else {
                subRight.add(list.get(i));
            }
        }

        CustomRecursiveAction<List<Integer>, Long> action1 = new CustomRecursiveAction<>(consumer, new Task<>(task.getFunction(), subLeft));
        CustomRecursiveAction<List<Integer>, Long> action2 = new CustomRecursiveAction<>(consumer, new Task<>(task.getFunction(), subRight));

        RecursiveAction.invokeAll(action1, action2);
    }

    private static Long splitTask(Task<List<Integer>, Long> task, Function<Task<List<Integer>, Long>, Long> function) {
        List<Integer> list = task.getParam();
        if (list == null || list.isEmpty()) {
            return 0L;
        }

        if (list.size() <= 10) {
            return task.compute();
        }

        List<Integer> subLeft = new ArrayList<>();
        List<Integer> subRight = new ArrayList<>();
        int mid = list.size() / 2;
        for (int i = 0; i < list.size(); i++) {
            if (i < mid) {
                subLeft.add(list.get(i));
            } else {
                subRight.add(list.get(i));
            }
        }

        CustomRecursiveTask<List<Integer>, Long> action1 = new CustomRecursiveTask<>(function, new Task<>(task.getFunction(), subLeft));
        CustomRecursiveTask<List<Integer>, Long> action2 = new CustomRecursiveTask<>(function, new Task<>(task.getFunction(), subRight));

        RecursiveAction.invokeAll(action1, action2);

        return action1.join() + action2.join();
    }
}
