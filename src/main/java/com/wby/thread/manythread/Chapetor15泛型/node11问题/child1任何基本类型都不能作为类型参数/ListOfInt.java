package com.wby.thread.manythread.Chapetor15泛型.node11问题.child1任何基本类型都不能作为类型参数;//: generics/ListOfInt.java
// Autoboxing compensates for the inability to use
// primitives in generics.

import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.*;

/**
* @Description: 正如之前提到的，你将在java泛型中发现的限制之一是，不能将基本类型用作类型参数。因此，不能创建ArrayList<int>之类的东西
 * 解决办法是使用基本类型的包装器以及1.5的自动包装机制。如果创建一个ArrayList<Integer>，并将基本类型int应用于这个容器，那么你将发现自动包装机制
 * 将自动地实现int到Integer的双向转换---因此，这几乎就像是一个ArrayList<int>一样：
*/
public class ListOfInt {
  public static void main(String[] args) {
    List<Integer> li = new ArrayList<Integer>();
    for(int i = 0; i < 5; i++)
      li.add(i);
    for(int i : li)
      System.out.print(i + " ");
  }
} /* Output:
0 1 2 3 4
*///:~
/**
* @Description: 注意，自动包装机制甚至允许用foreach语法来产生int
 *  通常，这种解决方案工作的很好----能够成功地存储和读取int，有一些转换碰巧发生的同时会对你屏蔽掉。但是，如果性能成为了问题，就需要使用专门适配基本
 *  类型的容器版本。org.apache.commons.collections.primitives就是一种开源的这类版本。
 *
 *  下面是另外一种方式，它可以创建持有Byte的Set：
*/
class ByteSet {
  Byte[] possibles = { 1,2,3,4,5,6,7,8,9 };
  Set<Byte> mySet =
          new HashSet<Byte>(Arrays.asList(possibles));
  // But you can't do this:
  // Set<Byte> mySet2 = new HashSet<Byte>(
  //   Arrays.<Byte>asList(1,2,3,4,5,6,7,8,9));
} ///:~
/**
* @Description: 注意，自动包装机制解决了一些问题，但是不不是解决了所有问题。下面的例子展示了一个泛型的Generator接口，他执行next方法返回一个具有
 * 其参数类型的对象。FArray类包含一个泛型方法，他通过使用生成器在数组中填充对象(这使得类泛型在本例中无法工作，因为这个方法是静态的)。Generator
 * 实现来自于16章，并且在main中，可以看到FArray.fill方法使用它在数组中填充对象：
*/
// Fill an array using a generator:
class FArray {
  public static <T> T[] fill(T[] a, Generator<T> gen) {
    for(int i = 0; i < a.length; i++)
      a[i] = gen.next();
    return a;
  }
}

class PrimitiveGenericTest {
  public static void main(String[] args) {
    String[] strings = FArray.fill(
            new String[7], new RandomGenerator.String(10));
    for(String s : strings)
      System.out.println(s);
    Integer[] integers = FArray.fill(
            new Integer[7], new RandomGenerator.Integer());
    for(int i: integers)
      System.out.println(i);
    // Autoboxing won't save you here. This won't compile:
    // int[] b =
    //   FArray.fill(new int[7], new RandIntGenerator());
  }
} /* Output:
YNzbrnyGcF
OWZnTcQrGs
eGZMmJMRoE
suEcUOneOE
dLsmwHLGEa
hKcxrEqUCB
bkInaMesbt
7052
6665
2654
3909
5202
2209
5458
*///:~
/**
* @Description: 由于RandomGeneraator.Integer实现了Generator<Integer>，所以我的希望是自动包装机制可以自动的将next的值从Integer转换为int。
 * 但是，自动包装机制不能应用于数组，因此这无法工作
*/
