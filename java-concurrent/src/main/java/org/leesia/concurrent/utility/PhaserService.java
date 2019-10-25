package org.leesia.concurrent.utility;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PhaserService {

    private Phaser phaser;

    public PhaserService(int parties) {
        phaser = new Phaser(parties);
    }

    /**
     * 到达并等待
     *
     * @return
     */
    public int arriveAndAwaitAdvance() {
        return phaser.arriveAndAwaitAdvance();
    }

    /**
     * 到达并退出，parties减1
     *
     * @return
     */
    public int arriveAndDeregister() {
        return phaser.arriveAndDeregister();
    }

    /**
     * 获取到达了第几个屏障
     *
     * @return
     */
    public int getPhase() {
        return phaser.getPhase();
    }

    /**
     * 获取注册的parties
     *
     * @return
     */
    public int getRegisteredParties() {
        return phaser.getRegisteredParties();
    }

    /**
     * 添加1个parties
     *
     * @return
     */
    public int register() {
        return phaser.register();
    }

    /**
     * 批量添加parties
     *
     * @param parties
     * @return
     */
    public int bulkRegister(int parties) {
        return phaser.bulkRegister(parties);
    }

    /**
     * 获取已经到达的parties数
     *
     * @return
     */
    public int getArrivedParties() {
        return phaser.getArrivedParties();
    }


    /**
     * 获取未到达的parties数
     *
     * @return
     */
    public int getUnarrivedParties() {
        return phaser.getUnarrivedParties();
    }

    /**
     * 到达，不等待
     *
     * @return
     */
    public int arrive() {
        return phaser.arrive();
    }

    /**
     * phase与当前getPhase()一致则等待，否则不等待
     *
     * @param phase
     * @return
     */
    public int awaitAdvance(int phase) {
        return phaser.awaitAdvance(phase);
    }

    /**
     * phase与当前getPhase()一致则等待，否则不等待，不可中断
     *
     * @param phase
     * @return
     */
    public int awaitAdvanceInterruptibly(int phase) throws InterruptedException {
        return phaser.awaitAdvanceInterruptibly(phase);
    }

    /**
     * phase与当前getPhase()一致则等待，设置最大等待时间，否则不等待，可中断
     *
     * @param phase
     * @return
     */
    public int awaitAdvanceInterruptibly(int phase, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        return phaser.awaitAdvanceInterruptibly(phase, timeout, unit);
    }

    /**
     * 使屏障失效
     */
    public void forceTermination() {
        phaser.forceTermination();
    }

    /**
     * 判断屏障是否已销毁
     *
     * @return
     */
    public boolean isTerminated() {
        return phaser.isTerminated();
    }
}
