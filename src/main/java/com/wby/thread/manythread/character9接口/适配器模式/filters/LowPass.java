package com.wby.thread.manythread.character9接口.适配器模式.filters;

import com.wby.thread.manythread.character9接口.适配器模式.Waveform;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 15:00
 * @Description:
 */
public class LowPass extends Filter{
    double cutoff;
    public LowPass(double cutoff){
        this.cutoff = cutoff;
    }

    @Override
    public Waveform process(Waveform input) {
        return input;
    }
}
