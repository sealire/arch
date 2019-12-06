package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LinkedTransferQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 8:52
 */
public class LinkedTransferQueueService<E> {

    private LinkedTransferQueue<E> linkedTransferQueue;

    public LinkedTransferQueueService() {
        linkedTransferQueue = new LinkedTransferQueue<>();
    }

    public LinkedTransferQueueService(Collection<? extends E> c) {
        linkedTransferQueue = new LinkedTransferQueue<>(c);
    }

    /**
     * 有消费者正在获取元素时，将数据传输过去，否则将数据加入队列并阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void transfer(E e) throws InterruptedException {
        linkedTransferQueue.transfer(e);
    }

    /**
     * 有消费者正在获取元素时，将数据传输过去，否则将数据加入队列，不阻塞
     *
     * @param e
     * @return
     */
    public boolean tryTransfer(E e) {
        return linkedTransferQueue.tryTransfer(e);
    }

    /**
     * 有消费者正在获取元素时，将数据传输过去，否则将数据加入队列，若在最大等待时间内没有消费者消费，则从队列中移除
     *
     * @param e
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return linkedTransferQueue.tryTransfer(e, timeout, unit);
    }

    /**
     * 获取元素，无元素时阻塞
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return linkedTransferQueue.take();
    }

    /**
     * 判断是否有消费者在等待数据
     *
     * @return
     */
    public boolean hasWaitingConsumer() {
        return linkedTransferQueue.hasWaitingConsumer();
    }

    /**
     * 获取正在等待数据的消费者线程数
     *
     * @return
     */
    public int getWaitingConsumerCount() {
        return linkedTransferQueue.getWaitingConsumerCount();
    }

    /**
     * 获取元素个数
     *
     * @return
     */
    public int size() {
        return linkedTransferQueue.size();
    }
}
