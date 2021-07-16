package com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child2讲一个方法应用于序列;

import java.util.Iterator;
import java.util.LinkedList;

public class SimpleQueue<T> implements Iterable<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    public void add(T t) {
        storage.offer(t);
    }

    public T get() {
        return storage.poll();
    }

    public Iterator<T> iterator() {
        return storage.iterator();
    }
} ///:~
