package com.wby.thread.manythread.Chapetor15泛型.node8擦除的补偿.child1创建类型实例;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/13 19:24
 * @Description:
 */
public class Widget {
    public static class Factory implements FactoryI<Widget> {
        public Widget create() {
            return new Widget();
        }
    }
}
