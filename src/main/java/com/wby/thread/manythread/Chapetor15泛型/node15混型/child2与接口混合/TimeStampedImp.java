package com.wby.thread.manythread.Chapetor15泛型.node15混型.child2与接口混合;

import java.util.Date;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/15 17:21
 * @Description:
 */
public class TimeStampedImp implements TimeStamped {
    private final long timeStamp;

    public TimeStampedImp() {
        timeStamp = new Date().getTime();
    }

    public long getStamp() {
        return timeStamp;
    }
}
