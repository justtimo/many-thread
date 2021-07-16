package com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child2讲一个方法应用于序列;//: generics/Apply.java
// {main: ApplyTest}

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 反射提供了一些有趣的可能性，但是它将所有的类型检查都转移到了运行时，因此许多情况下这并不是我们希望的。
 *  如果能够实现编译期类型检查，这通常会更符合要求。但是有可能实现编译期类型检查 和潜在类型机制吗？
 *
 *  看一个例子。假设要创建apply方法，他能将任何方法应用于某个序列中的所有对象。这是接口看起来并不适合的情况，因为你想要将任何方法应用于一个对象集合，
 *  而接口对于描述“任何方法”存在过多的限制。如何用java实现这个需求呢？
 *
 *  最初，我们可以用反射来解决这个问题，由于有了1.5的可变参数，这种方式被证明是相当优雅的：
*/
public class Apply {
  public static <T, S extends Iterable<? extends T>>
  void apply(S seq, Method f, Object... args) {
    try {
      for(T t: seq)
        f.invoke(t, args);
    } catch(Exception e) {
      // Failures are programmer errors
      throw new RuntimeException(e);
    }
  }
}

class Shape {
  public void rotate() { print(this + " rotate"); }
  public void resize(int newSize) {
    print(this + " resize " + newSize);
  }
}

class Square extends Shape {}

class FilledList<T> extends ArrayList<T> {
  public FilledList(Class<? extends T> type, int size) {
    try {
      for(int i = 0; i < size; i++)
        // Assumes default constructor:
        add(type.newInstance());
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}

class ApplyTest {
  public static void main(String[] args) throws Exception {
    List<Shape> shapes = new ArrayList<Shape>();
    for(int i = 0; i < 10; i++)
      shapes.add(new Shape());
    Apply.apply(shapes, Shape.class.getMethod("rotate"));
    Apply.apply(shapes,
      Shape.class.getMethod("resize", int.class), 5);
    List<Square> squares = new ArrayList<Square>();
    for(int i = 0; i < 10; i++)
      squares.add(new Square());
    Apply.apply(squares, Shape.class.getMethod("rotate"));
    Apply.apply(squares,
      Shape.class.getMethod("resize", int.class), 5);

    Apply.apply(new FilledList<Shape>(Shape.class, 10),
      Shape.class.getMethod("rotate"));
    Apply.apply(new FilledList<Shape>(Square.class, 10),
      Shape.class.getMethod("rotate"));

    SimpleQueue<Shape> shapeQ = new SimpleQueue<Shape>();
    for(int i = 0; i < 5; i++) {
      shapeQ.add(new Shape());
      shapeQ.add(new Square());
    }
    Apply.apply(shapeQ, Shape.class.getMethod("rotate"));
  }
} /* (Execute to see output) *///:~
/**
 * @Description: 在Apply中，我们运气很好，因为碰巧在java中内建了一个由java容器类库使用的Iterable接口。正因如此，apply方法可以接受任何实现了Iterable
 * 接口的事物，包括诸如List这样的所有Collection类。但是还可以接受其他任何事物，只要能够使这些事物是Iterable的---例如，在main中使用的下面定义的SimpleQueue：
 * 见SimpleQueue。java
 */
/**
* @Description:  在Apply.java中，异常转换为RUntimeException，因为没有多少办法可以从这种异常中恢复---这种情况下，他们实际上代表着程序员的错误。
 *
 * 注意，必须放置边界和通配符，以使得Apply和FilledList在所需要的情况下都可以使用。可以试验下，将这些边界和通配符拿出来，你会发现某些Apply和FilledList
 * 将无法正常工作。
 *
 * FilledList表示有点进退两难的情况，为了使某些类型可用，他必须有默认构造器，但是java没有任何方式可以在编译期断言这种事情，因此者就变成了一个运行时
 * 问题。确保编译期检查的常见建议是定义一个工厂接口，他有一个可以生成对象的方法，然后FilledList将接受这个接口而不是这个类型标记的“原生工厂”，而这样做的
 * 问题是在FilledList中使用的所有类都必须实现这个工厂接口。
 * 大多数类都是在不了解你的接口的情况下创建的，因此也就没有实现这个借口。
 * 稍后将展示一种使用适配器的解决方案。
 *
 * 但是上面所展示的使用类型标记的方法可能是一种合理的这种(至少是一种马上就能想到的解决方案)。
 * 通过这种方式，像FilledList这样的东西就会非常容易，我们会马上想到要使用他而不是忽略他。
 * 当然，因为错误是在运行时报告的，所以你要有把握，这些错误将在开发过程的早期出现。
 *
 * 注意，类型标记技术是java推荐的技术。但是，我发现人们对这种技术的适应度不一，有些人强烈的首选本章前面描述的工厂方式。
 *
 * 尽管java解决方案被证明很优雅，但是我们必须知道使用反射可能比非反射的实现慢一些，因为很多动作是在运行时发生的。
 * 这不应该成为阻止你使用这种方案的理由，至少可以将它当做一种马上就能想到的解决方案(以防止陷入不成熟的优化中)，这是两种方法之间的一个差异。
*/


