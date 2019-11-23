package org.leesia.concurrent.forkjoin;

import org.leesia.concurrent.vo.Task;

import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

/**
 * @ClassName: CustomRecursiveTask
 * @Description: TODO
 * @author: leesia
 * @date: 2019/11/22 12:53
 */
public class CustomRecursiveTask<T, R> extends RecursiveTask<R> {

    private Function<Task<T, R>, R> function;

    private Task<T, R> task;

    public CustomRecursiveTask() {
    }

    public CustomRecursiveTask(Function<Task<T, R>, R> function, Task<T, R> task) {
        this.function = function;
        this.task = task;
    }

    public Function<Task<T, R>, R> getFunction() {
        return function;
    }

    public void setFunction(Function<Task<T, R>, R> function) {
        this.function = function;
    }

    public Task<T, R> getTask() {
        return task;
    }

    public void setTask(Task<T, R> task) {
        this.task = task;
    }

    @Override
    protected R compute() {

        return function.apply(task);
    }
}
