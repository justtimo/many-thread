package com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:21
 * @Description:
 */
public class Waveform {
    private static long counter;
    private final long id = counter++;
    public String toString() { return "Waveform " + id; }
} ///:~
