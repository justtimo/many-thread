package com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child3RandomList;//: generics/RandomList.java

import java.util.ArrayList;
import java.util.Random;

/**
* @Description: 作为容器的另一个例子，假设需要一个持有特定类型对象的列表，每次调用其上的select()方法时，他可以随机的选取一个元素。
 *  如果我们希望以此构建一个可以应用于各种类型的对象的工具，就需要使用泛型：
*/
public class RandomList<T> {
  private ArrayList<T> storage = new ArrayList<T>();
  private Random rand = new Random(47);
  public void add(T item) { storage.add(item); }
  public T select() {
    return storage.get(rand.nextInt(storage.size()));
  }
  public static void main(String[] args) {
    RandomList<String> rs = new RandomList<String>();
    for(String s: ("The quick brown fox jumped over " +
        "the lazy brown dog").split(" "))
      rs.add(s);
    for(int i = 0; i < 11; i++)
      System.out.print(rs.select() + " ");
  }
} /* Output:
brown over fox quick quick dog brown The brown lazy brown
*///:~
