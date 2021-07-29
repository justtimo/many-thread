package com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码.child1理解HashCode;

import java.util.Map;

/**
 * @Description: 这里，这个被称为MapEntry的十分简单的类可以保存和读取键与值，它在entrySet（）中用来产生键-值对Set。注意，entrySetO使用了HashSet来保存键-值对，
 * 并且MapEntry采用了一种简单的方式，即只使用key的hashCode（方法。尽管这个解决方案非常简单，并且看起来在 SlowMap.mainO的琐碎测试中可以工作，但是这并不是一个恰当的实现，
 * 因为它创建了键和值的副本。entrySetO的恰当实现应该在Map中提供视图，而不是副本，并且这个视图允许对原始映射表进行修改（副本就不行）。练习16提供了修正这个问题的机会。
 *
 * 注意，在MapEntry中的equals（方法必须同时检查键和值，而hashCode（）方法的含义稍后就会介绍。SlowMap的内容的String表示是由在AbstractMap中定义的toStringO方法自动产生的。
 */
public class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V v) {
        V result = value;
        value = v;
        return result;
    }

    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^
                (value == null ? 0 : value.hashCode());
    }

    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) return false;
        MapEntry me = (MapEntry) o;
        return
                (key == null ?
                        me.getKey() == null : key.equals(me.getKey())) &&
                        (value == null ?
                                me.getValue() == null : value.equals(me.getValue()));
    }

    public String toString() {
        return key + "=" + value;
    }
} ///:~
