package org.leesia.redislock;

import java.io.Serializable;

/**
 * @Auther: leesia
 * @Date: 2018/9/9 21:31
 * @Description:
 */
public class LockValue implements Serializable {

    private Long serialVersionUID = 1L;

    private String pid;

    private String lockId;

    private long timeout;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
