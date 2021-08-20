//: enumerated/Competitor.java
// Switching one enum on another.
package com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发;

import com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome;

public interface Competitor<T extends Competitor<T>> {
  Outcome compete(T competitor);
} ///:~
