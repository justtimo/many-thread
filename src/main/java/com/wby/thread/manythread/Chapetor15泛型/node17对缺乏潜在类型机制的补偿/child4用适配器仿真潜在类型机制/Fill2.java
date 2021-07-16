package com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child4用适配器仿真潜在类型机制;//: generics/Fill2.java
// Using adapters to simulate latent typing.
// {main: Fill2Test}


import com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child2讲一个方法应用于序列.SimpleQueue;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Coffee;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Latte;
import com.wby.thread.manythread.Chapetor15泛型.node3泛型接口.Mocha;
import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: java泛型并不没有潜在类型机制，而我们需要像潜在类型机制这样的东西编写能够跨类边界应用的代码（即 泛化代码）。
 *  存在某种方式可以绕过这种限制吗？？
 *
 *  潜在类型机制将在这里实现什么？它意味着你可以编写代码声明：“我不关心我在这里使用的类型，只要它具有这些方法即可”。
 *  实际上，潜在类型机制创建了一个包含所需方法的 隐式接口。
 *  因此他遵循这样的规则：如果我们手工编写了必需的接口，那么他就应该能够解决问题
 *
 *  从我们有的接口中编写代码来产生我们需要的接口，这是适配器设计模式的一个典型例子。
 *  可以使用适配器来适配已有的接口，以产生想要的接口。
 *  下面这种使用前面定义的Coffee继承结构的解决方案演示了编写适配器的不同方式：
*/

interface Addable<T> { void add(T t); }

public class Fill2 {
  // Classtoken version:
  public static <T> void fill(Addable<T> addable,
  Class<? extends T> classToken, int size) {
    for(int i = 0; i < size; i++)
      try {
        addable.add(classToken.newInstance());
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
  }
  // Generator version:
  public static <T> void fill(Addable<T> addable,
                              Generator<T> generator, int size) {
    for(int i = 0; i < size; i++)
      addable.add(generator.next());
  }
}

// To adapt a base type, you must use composition.
// Make any Collection Addable using composition:
class AddableCollectionAdapter<T> implements Addable<T> {
  private Collection<T> c;
  public AddableCollectionAdapter(Collection<T> c) {
    this.c = c;
  }
  public void add(T item) { c.add(item); }
}

// A Helper to capture the type automatically:
class Adapter {
  public static <T>
  Addable<T> collectionAdapter(Collection<T> c) {
    return new AddableCollectionAdapter<T>(c);
  }
}

// To adapt a specific type, you can use inheritance.
// Make a SimpleQueue Addable using inheritance:
class AddableSimpleQueue<T>
extends SimpleQueue<T> implements Addable<T> {
  public void add(T item) { super.add(item); }
}

class Fill2Test {
  public static void main(String[] args) {
    // Adapt a Collection:
    List<Coffee> carrier = new ArrayList<Coffee>();
    Fill2.fill(
      new AddableCollectionAdapter<Coffee>(carrier),
      Coffee.class, 3);
    // Helper method captures the type:
    Fill2.fill(Adapter.collectionAdapter(carrier),
      Latte.class, 2);
    for(Coffee c: carrier)
      print(c);
    print("----------------------");
    // Use an adapted class:
    AddableSimpleQueue<Coffee> coffeeQueue =
      new AddableSimpleQueue<Coffee>();
    Fill2.fill(coffeeQueue, Mocha.class, 4);
    Fill2.fill(coffeeQueue, Latte.class, 1);
    for(Coffee c: coffeeQueue)
      print(c);
  }
} /* Output:
Coffee 0
Coffee 1
Coffee 2
Latte 3
Latte 4
----------------------
Mocha 5
Mocha 6
Mocha 7
Mocha 8
Latte 9
*///:~
/**
* @Description: Fill2对Collection的要求与Fill不同，他只需要实现了Addable的独享，而Addable已经为Fill编写了---他是我希望
 *  编译器帮我创建的潜在类型的一种体现。
 *
 *  此版本中，还添加了一个重载的fill方法，他接受一个Generator而不是类型标记。Generator在编译期是类型安全的：编译器将确保传递的是正确的Generator，
 *  因此不会抛出任何异常。
 *
 *  第一个适配器，AddableCollectionAdapter可以工作与基类型Collection，这意味着Collection的任何实现都可以使用。
 *  这个版本直接存储Collection引用，并使用它来实现add
 *
 *  如果有一个具体类型而不是继承结构的基类，那么使用继承来创建适配器时，你可以少编写一些代码，就像AddableSimpleQueue中那样。
 *
 *  在Fill2.main()中，可以看到各种不同类型的适配器在运行。
 *  首先，Collection类型是由AddableCollectionAdapter适配的。这个第二版使用了一个泛化的辅助方法，你可以看到这个泛化方法是如何捕获类型并因此
 *  不必显式的写出来---这是产生更优雅的代码的一种惯用技巧
 *
 *  接下来，使用了预适配的AddableSimpleQueue。。注意，这两种情况下，适配器都允许前面没有实现Addable的类用于Fill2.fill()中。
 *
 *  使用像这样的适配器看起来是对缺乏潜在类型机制的一种补偿，因此允许编写出真正的泛化代码。但是，这是一个额外的步骤，并且是类库创建者和消费者都必须理解
 *  的事情，而缺乏经验的程序员可能还没掌握这个概念。潜在类型机制通过移除这个额外的步骤，使得泛化代码更容易应用，这就是他的价值所在。
*/
