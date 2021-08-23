package com.wby.thread.manythread.character9接口.node3完全解耦.复用代码;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 18:23
 * @Description:
 */
public class Apply {
    public static void process(Processor p, Object s) {
        print("Using Processor " + p.name());
        print(p.process(s));
    }
} ///:~
