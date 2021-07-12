package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法.child2可变参数与泛型方法;//: generics/GenericVarargs.java

import java.util.ArrayList;
import java.util.List;

/**
* @Description:  泛型方法与可变参数列表能够很好的共存：
*/
public class GenericVarargs {
  public static <T> List<T> makeList(T... args) {
    List<T> result = new ArrayList<T>();
    for(T item : args)
      result.add(item);
    return result;
  }
  public static void main(String[] args) {
    List<String> ls = makeList("A");
    System.out.println(ls);
    ls = makeList("A", "B", "C");
    System.out.println(ls);
    ls = makeList("ABCDEFFHIJKLMNOPQRSTUVWXYZ".split(""));
    System.out.println(ls);
  }
} /* Output:
[A]
[A, B, C]
[, A, B, C, D, E, F, F, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]
*///:~
/**
* @Description: makeList()方法展示了与标准类库中java.util.Arrays.asList()方法相同的功能。
*/
