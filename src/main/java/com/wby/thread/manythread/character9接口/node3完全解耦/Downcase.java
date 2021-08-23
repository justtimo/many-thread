package com.wby.thread.manythread.character9接口.node3完全解耦;



public class Downcase extends Processor {
    String process(Object input) {
        return ((String) input).toLowerCase();
    }
}
