package com.wby.thread.manythread.character9接口.node3完全解耦;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:27
 * @Description:
 */
public class Processor {
    public String name() {
        return getClass().getSimpleName();
    }
    Object process(Object input) { return input; }
}
