package com.wby.thread.manythread.character9接口.node3完全解耦;

import java.util.Arrays;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:28
 * @Description:
 */
public class Splitter extends Processor {
    String process(Object input) {
        // The split() argument divides a String into pieces:
        return Arrays.toString(((String) input).split(" "));
    }
}
