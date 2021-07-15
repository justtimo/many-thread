package com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型.child1古怪的循环泛型;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/15 15:31
 * @Description:
 */
public class BasicHolder<T> {
    T element;
    public void set(T arg) { element = arg; }
    public T get() { return element; }
    public void f() {
        System.out.println(element.getClass().getSimpleName());
    }
} ///:~
