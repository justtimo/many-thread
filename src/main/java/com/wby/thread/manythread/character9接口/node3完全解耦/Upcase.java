package com.wby.thread.manythread.character9接口.node3完全解耦;




public class Upcase extends Processor {
    String process(Object input) { // Covariant return
        return ((String) input).toUpperCase();
    }
}
