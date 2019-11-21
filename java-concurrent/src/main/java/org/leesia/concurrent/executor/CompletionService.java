package org.leesia.concurrent.executor;

import java.util.concurrent.*;

public class CompletionService {

    private java.util.concurrent.CompletionService completionService;

    public CompletionService(Executor executor) {
        completionService = new ExecutorCompletionService(executor);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param <V>
     * @return
     */
    public <V> Future<V> submit(Callable<V> task) {
        return completionService.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param result
     * @param <V>
     * @return
     */
    public <V> Future<V> submit(Runnable task, V result) {
        return completionService.submit(task, result);
    }

    /**
     * 获取任务结果
     *
     * @param <V>
     * @return
     * @throws InterruptedException
     */
    public <V> Future<V> take() throws InterruptedException {
        return completionService.take();
    }

    /**
     * 获取任务结果
     *
     * @param <V>
     * @return
     */
    public <V> Future<V> poll() {
        return completionService.poll();
    }

    /**
     * 获取任务结果
     *
     * @param timeout
     * @param unit
     * @param <V>
     * @return
     * @throws InterruptedException
     */
    public <V> Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException {
        return completionService.poll(timeout, unit);
    }
}
