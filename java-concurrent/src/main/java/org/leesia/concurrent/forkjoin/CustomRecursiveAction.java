package org.leesia.concurrent.forkjoin;

import org.leesia.concurrent.vo.Task;

import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * @ClassName: CustomRecursiveAction
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 12:49
 */
public class CustomRecursiveAction<T, R> extends RecursiveAction {

    private Consumer<Task<T, R>> consumer;

    private Task<T, R> task;

    public CustomRecursiveAction() {
    }

    public CustomRecursiveAction(Consumer<Task<T, R>> consumer, Task<T, R> task) {
        this.consumer = consumer;
        this.task = task;
    }

    public Consumer<Task<T, R>> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Task<T, R>> consumer) {
        this.consumer = consumer;
    }

    public Task<T, R> getTask() {
        return task;
    }

    public void setTask(Task<T, R> task) {
        this.task = task;
    }

    @Override
    protected void compute() {

        consumer.accept(task);
    }
}
