package com.wby.thread.manythread.interfacce.适配器模式;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 14:58
 * @Description:
 */
public class Waveform {
    public static long counter;
    private final long id=counter++;

    public String toString() {
        return "Waveform" +id;
    }
}
