package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架;//: containers/Test.java
// Framework for performing timed tests of containers.

/**
* @Description: 性能测试框架
 * 为了防止代码重复以及为了提供测试的一致性，我将测试过程的基本功能放置到了一个测试框架中。下面的代码建立了一个基类，从中可以创建一个匿名内部类列表，
 * 其中每个匿名内部类都用于每种不同的测试，它们每个都被当作测试过程的一部分而被调用。这种方式使得你可以很方便地添加或移除新的测试种类。
 *
 * 这是模版方法设计模式的另一个示例。尽管你遵循了典型的模版方法模式，覆盖了每个特定测试的Test.testO）方法，但是在本例中，其核心代码（不会发生变化）在一个单独的Tester类
 * 中(Krzysztof Sobolewski帮助我设计了本例中的泛型。)。待测容器类型是泛型参数C∶
*/
public abstract class Test<C> {
  String name;
  public Test(String name) { this.name = name; }
  // Override this method for different tests.
  // Returns actual number of repetitions of test.
  public abstract int test(C container, TestParam tp);
} ///:~
/**
* @Description: 见TestParam.java
*/


