package org.leesia.concurrent.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.LDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

/**
 * @ClassName: Task
 * @Description:
 * @author: leesia
 * @date: 2019/11/19 11:54
 */
@Getter
@Setter
@ToString
public class Task<T, R> implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 线程名
     */
    private String threadName;

    /**
     * 任务名
     */
    private String taskName;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务
     */
    private Date endTime;

    /**
     * 参数
     */
    private T param;

    /**
     * 结果
     */
    private R result;

    /**
     * 计算逻辑
     */
    private Function<T, R> function;

    /**
     * 构造函数
     *
     * @param function
     */
    public Task(Function<T, R> function, T param) {
        this.function = function;
        this.param = param;
    }

    /**
     * 计算结果
     */
    public R compute() {
        startTime = new Date();

        threadName = getThreadName();
        taskName = getTaskName();

        result = function.apply(param);

        endTime = new Date();

        logger.info("task: {}, thread: {}, run from: {} to: {}, input: {}, output: {}",
                taskName, threadName, LDateUtil.format(startTime, "YYYY-MM-dd HH:mm:ss:SSS"),
                LDateUtil.format(endTime, "YYYY-MM-dd HH:mm:ss:SSS"), param, result);

        return result;
    }

    /**
     * 获取线程名
     *
     * @return
     */
    private String getThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * 获取任务名
     *
     * @return
     */
    private String getTaskName() {
        return "task-" + RandomUtil.randomNumberString(10);
    }
}
