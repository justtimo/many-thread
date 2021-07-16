package com.wby.thread.manythread.Chaptor16数组.node1数组为什么特殊;


public class BerylliumSphere {
    private static long counter;
    private final long id = counter++;

    public String toString() {
        return "Sphere " + id;
    }
}
