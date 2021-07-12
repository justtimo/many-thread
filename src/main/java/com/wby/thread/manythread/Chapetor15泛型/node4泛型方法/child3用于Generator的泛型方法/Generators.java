package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法.child3用于Generator的泛型方法;//: generics/Generators.java
// A utility to use with Generators.


import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Coffee;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.CoffeeGenerator;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Fibonacci;
import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.Collection;

/**
* @Description: 利用生成器，我们可以很方便的填充一个Collection，而泛型化这种操作是具有实际意义的：
*/
public class Generators {
  public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
    for(int i = 0; i < n; i++){
      coll.add(gen.next());
    }
    return coll;
  }
  public static void main(String[] args) {
    Collection<Coffee> coffee = fill(
      new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
    for(Coffee c : coffee)
      System.out.println(c);
    Collection<Integer> fnumbers = fill(
      new ArrayList<Integer>(), new Fibonacci(), 12);
    for(int i : fnumbers)
      System.out.print(i + ", ");
  }
} /* Output:
Americano 0
Latte 1
Americano 2
Mocha 3
1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,
*///:~
/**
* @Description:  请注意，fill()方法是如何透明的应用于Coffee和Integer的容器和生成器。
*/
