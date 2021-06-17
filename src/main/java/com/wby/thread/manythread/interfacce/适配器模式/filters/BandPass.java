package com.wby.thread.manythread.interfacce.适配器模式.filters;

import com.wby.thread.manythread.interfacce.适配器模式.Waveform;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 15:03
 * @Description:
 */
public class BandPass extends Filter{
    double lowCutoff,HighCutoff;
    public BandPass(double lowCut,double highCut){
        lowCutoff=lowCut;
        HighCutoff=highCut;
    }

    @Override
    public Waveform process(Waveform input) {
        return input;
    }
}
