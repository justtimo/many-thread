package com.wby.thread.manythread.character9接口.node3完全解耦.适配器模式;

import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.BandPass;
import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.HighPass;
import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.LowPass;
import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.Waveform;
import com.wby.thread.manythread.character9接口.node3完全解耦.复用代码.Apply;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:26
 * @Description:
 */
public class FilterProcessor {
    public static void main(String[] args) {
        Waveform w = new Waveform();
        Apply.process(new FilterAdapter(new LowPass(1.0)), w);
        Apply.process(new FilterAdapter(new HighPass(2.0)), w);
        Apply.process(
                new FilterAdapter(new BandPass(3.0, 4.0)), w);
    }
} /* Output:
Using Processor LowPass
Waveform 0
Using Processor HighPass
Waveform 0
Using Processor BandPass
Waveform 0
*///:~
