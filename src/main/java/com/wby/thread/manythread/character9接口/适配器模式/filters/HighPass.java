package com.wby.thread.manythread.character9接口.适配器模式.filters;

import com.wby.thread.manythread.character9接口.适配器模式.Waveform;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 15:02
 * @Description:
 */
public class HighPass extends Filter{
    double cutoff;
    public HighPass(double cutoff){
        this.cutoff = cutoff;
    }

    @Override
    public Waveform process(Waveform input) {
        return input;
    }
}
