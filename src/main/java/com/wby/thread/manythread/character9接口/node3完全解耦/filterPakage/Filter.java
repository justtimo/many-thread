package com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:22
 * @Description:
 */
public class Filter {
    public String name() {
        return getClass().getSimpleName();
    }
    public Waveform process(Waveform input) { return input; }
} ///:~
