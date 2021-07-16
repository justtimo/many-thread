package com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child3当你并未碰巧拥有正确的接口时;//: generics/Fill.java
// Generalizing the FilledList idea
// {main: FillTest}

import com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child2讲一个方法应用于序列.SimpleQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* @Description: 上面的例子是受益的，因为Iterable接口已经是内建的，而他正是我们需要的。但是更一般的情况会怎么样呢？
 *  如果不存在刚好适合你的需求的接口呢？
 *
 *  例如，让我们泛化FilledList中的思想，创建一个参数化的方法fill，他接受一个序列，并使用Generator填充。
 *  当我们尝试java编写是，就会陷入问题中，因为没有任何像前面例子中的Iterable接口那样的"Addable"遍历接口。
 *  因此不能说：可以在任何事物上调用add方法，而必须说：可以在Collection子类上调用add方法。
 *  这样的代码并不是特别泛化，因为他必须限制只能工作与Collection实现，如果使用没有实现Collection的类，那么泛化代码将不能工作。
 *
 *  下面是这段代码的样子：
*/
// Doesn't work with "anything that has an add()." There is
// no "Addable" interface so we are narrowed to using a
// Collection. We cannot generalize using generics in
// this case.
public class Fill {
  public static <T> void fill(Collection<T> collection,
  Class<? extends T> classToken, int size) {
    for(int i = 0; i < size; i++)
      // Assumes default constructor:
      try {
        collection.add(classToken.newInstance());
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
  }
}

class Contract {
  private static long counter = 0;
  private final long id = counter++;
  public String toString() {
    return getClass().getName() + " " + id;
  }
}

class TitleTransfer extends Contract {}

class FillTest {
  public static void main(String[] args) {
    List<Contract> contracts = new ArrayList<Contract>();
    Fill.fill(contracts, Contract.class, 3);
    Fill.fill(contracts, TitleTransfer.class, 2);
    for(Contract c: contracts)
      System.out.println(c);
    SimpleQueue<Contract> contractQueue =
      new SimpleQueue<Contract>();
    // Won't work. fill() is not generic enough:
    // Fill.fill(contractQueue, Contract.class, 3);
  }
} /* Output:
Contract 0
Contract 1
Contract 2
TitleTransfer 3
TitleTransfer 4
*///:~
/**
* @Description: 这正是具有潜在类型机制的参数化类型机制的价值所在，因为你不回收任何特定类库的创建者过去所做的设计支配，
 * 因此不需要在每次碰到一个没有考虑到你的具体情况的新类库时，都去重写代码(因此这样的代码才是真正泛化的)。
 * 上面的例子中，因为java设计者没有预见到对“Addable”接口的需要，所以我们被限制为Collection继承层次结构中，即使SimpleQueue
 * 有一个add方法，他也不能工作。因为这会将代码限制为只能工作于Collection，因此这样的代码不是特别“泛化”。
 * 有了潜在类型机制，情况就不同了。
*/
