package org.leesia.test.concurrent.forkjoin;

import org.leesia.concurrent.forkjoin.CustomRecursiveAction;
import org.leesia.concurrent.forkjoin.ForkJoinPoolService;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.concurrent.util.ListUtil;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName: ForkJoinPoolTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 13:01
 */
public class ForkJoinPoolTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ForkJoinPoolTest.class);

    private static ForkJoinPoolService pool = new ForkJoinPoolService();

    public static void main(String[] args) throws Exception {

        test_execute_submit_1();

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
    public static void test_execute_ForkJoinTask() {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(integers -> ListUtil.sum(integers), nums);

        CustomRecursiveAction action = new CustomRecursiveAction<>(t -> t.compute(), task);
        pool.execute(action);
        pool.shutdown();

        ThreadUtil.sleep(5);

        LOGGER.info("result: {}", action.getTask().getResult());
    }

    public static void test_execute_submit_1() {
        List<Integer> nums = org.leesia.test.concurrent.util.ListUtil.randomNumberList(1, 1000, 100);
        Task<List<Integer>, Long> task = new Task<>(integers -> ListUtil.sum(integers), nums);

        CustomRecursiveAction<List<Integer>, Long> action = new CustomRecursiveAction<>();
        action.setConsumer(t -> {
            List<Integer> nums1 = t.getParam();
            if (nums1 != null && !nums1.isEmpty()) {
                if (nums1.size() > 10) {
                    List<Integer> subLeft = new ArrayList<>();
                    List<Integer> subRight = new ArrayList<>();
                    int mid = nums1.size() / 2;
                    for (int i = 0; i < nums1.size(); i++) {
                        if (i < mid) {
                            subLeft.add(nums1.get(i));
                        } else {
                            subRight.add(nums1.get(i));
                        }
                    }

                    CustomRecursiveAction action1 = new CustomRecursiveAction(action.getConsumer(), new Task(t.getFunction(), subLeft));
                    CustomRecursiveAction action2 = new CustomRecursiveAction(action.getConsumer(), new Task(t.getFunction(), subRight));

                    action.invokeAll(action1, action2);
                } else {
                    t.compute();
                }
            }
        });
        action.setTask(task);

        pool.submit(action);
        pool.shutdown();
    }
}
