package com.wby.thread.manythread.Chapetor15泛型.node15混型.child2与接口混合;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/15 17:21
 * @Description:
 */
public class SerialNumberedImp implements SerialNumbered {
    private static long counter = 1;
    private final long serialNumber = counter++;

    public long getSerialNumber() {
        return serialNumber;
    }
}
