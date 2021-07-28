//: net/mindview/util/CollectionData.java
// A Collection filled with data using a generator object.
package com.wby.thread.manythread.Chaptor17容器深入研究.node2填充容器.child1一种Generator解决方案;

import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
* @Description: 事实上，所有的Collection子类型都有一个接收另一个Collection对象的构造器，用所接收的Collection对象的元素来填充新的容器。 为了
 *
 * 更加容易的创建测试数据，我们需要做的是构建接收Generator(在15章中定义并在16章中深入探讨过)和quantity数值并将他们作为构造器参数的类：
*/
public class CollectionData<T> extends ArrayList<T> {
  public CollectionData(Generator<T> gen, int quantity) {
    for(int i = 0; i < quantity; i++)
      add(gen.next());
  }
  // A generic convenience method:
  public static <T> CollectionData<T>
  list(Generator<T> gen, int quantity) {
    return new CollectionData<T>(gen, quantity);
  }
} ///:~
/**
* @Description: 这个类使用Generator在容器中放置所需数量的对象，然后所产生的容器可以传递给任何Collection的构造器，这个构造器会把其中的数据复制到
 * 自身中。addAll方法是所有Collection子类型的一部分，他也可以用来组装现有的Collection。
 *
 * 泛型便利方法可以减少在使用类时所必需的类型检查数量。
 *
 * CollectionData是适配器设计模式的一个实例，他将Generator适配到Collection的构造器上。
 *
 * 下面是初始化LinkedHashSet的一个示例：
*/
class Government implements Generator<String> {
  String[] foundation = ("strange women lying in ponds " +
          "distributing swords is no basis for a system of " +
          "government").split(" ");
  private int index;
  public String next() { return foundation[index++]; }
}

class CollectionDataTest {
  public static void main(String[] args) {
    Set<String> set = new LinkedHashSet<String>(
            new CollectionData<String>(new Government(), 15));
    // Using the convenience method:
    set.addAll(CollectionData.list(new Government(), 15));
    System.out.println(set);
  }
} /* Output:
[strange, women, lying, in, ponds, distributing, swords, is, no, basis, for, a, system, of, government]
*///:~
/**
* @Description: 这些元素的顺序与他们的插入顺序相同，因为LinkedHashSet维护的是保持了插入顺序的链接列表。
 * 在16章中定义的所有操作符现在通过CollectionData适配器都是可用的。下面是使用了其中两个操作符的示例：
*/
class CollectionDataGeneration {
  public static void main(String[] args) {
    System.out.println(new ArrayList<String>(
            CollectionData.list( // Convenience method
                    new RandomGenerator.String(9), 10)));
    System.out.println(new HashSet<Integer>(
            new CollectionData<Integer>(
                    new RandomGenerator.Integer(), 10)));
  }
} /* Output:
[YNzbrnyGc, FOWZnTcQr, GseGZMmJM, RoEsuEcUO, neOEdLsmw, HLGEahKcx, rEqUCBbkI, naMesbtWH, kjUrUkZPg, wsqPzDyCy]
[573, 4779, 871, 4367, 6090, 7882, 2017, 8037, 3455, 299]
*///:~
/**
* @Description: RandomGenerator.String所产生的String长度是通过构造器参数控制的。
*/
