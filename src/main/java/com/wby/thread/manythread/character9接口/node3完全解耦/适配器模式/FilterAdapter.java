package com.wby.thread.manythread.character9接口.node3完全解耦.适配器模式;

import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.Filter;
import com.wby.thread.manythread.character9接口.node3完全解耦.filterPakage.Waveform;
import com.wby.thread.manythread.character9接口.node3完全解耦.复用代码.Processor;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:26
 * @Description:
 */
public class FilterAdapter implements Processor {
    Filter filter;
    public FilterAdapter(Filter filter) {
        this.filter = filter;
    }
    public String name() { return filter.name(); }
    public Waveform process(Object input) {
        return filter.process((Waveform)input);
    }
}
