//: enumerated/RoShamBo.java
// Common tools for RoShamBo examples.
package com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发;

import com.wby.thread.manythread.character19枚举类型.node6随机选取.Enums;

public class RoShamBo {
  public static <T extends Competitor<T>>
  void match(T a, T b) {
    System.out.println(
      a + " vs. " + b + ": " +  a.compete(b));
  }
  public static <T extends Enum<T> & Competitor<T>>
  void play(Class<T> rsbClass, int size) {
    for(int i = 0; i < size; i++)
      match(
        Enums.random(rsbClass),Enums.random(rsbClass));
  }
} ///:~
