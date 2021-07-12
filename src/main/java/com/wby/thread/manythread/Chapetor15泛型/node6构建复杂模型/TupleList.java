package com.wby.thread.manythread.Chapetor15泛型.node6构建复杂模型;//: generics/TupleList.java
// Combining generic types to make complex generic types.

import com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库.Amphibian;
import com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库.TupleTest;
import com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库.Vehicle;
import com.wby.thread.manythread.net.mindview.util.FourTuple;

import java.util.ArrayList;

/**
 * @Description: 泛型的一个重要好处是能够简单而安全的创建复杂的模型。例如，我们可以很容易的创建List元组
 */
public class TupleList<A,B,C,D>
extends ArrayList<FourTuple<A,B,C,D>> {
  public static void main(String[] args) {
    TupleList<Vehicle, Amphibian, String, Integer> tl =
      new TupleList<Vehicle, Amphibian, String, Integer>();
    tl.add(TupleTest.h());
    tl.add(TupleTest.h());
    for(FourTuple<Vehicle,Amphibian,String,Integer> i: tl)
      System.out.println(i);
  }
} /* Output: (75% match)
(Vehicle@11b86e7, Amphibian@35ce36, hi, 47)
(Vehicle@757aef, Amphibian@d9f9c3, hi, 47)
*///:~
/**
* @Description: 尽管看上去有些冗长(特别是迭代器的创建)，但最终还是没有用过多的代码就得到了一个相当强大的数据结构
 *  下面是一个例子，他展示了使用泛型类型来构建复杂模型是多么的简单。即使每个类都是作为一个构建块创建的，但是其整个还是包含许多部分。
 *  在本例中，构建的模型是一个零售店，它包含走廊、货架和商品
 *  见Store.java
*/
