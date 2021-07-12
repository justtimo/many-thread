package com.wby.thread.manythread.Chapetor15泛型.node7擦除的神秘之处.child1C加加的方式;//: generics/Manipulation.java
// {CompileTimeError} (Won't compile)

class HasF {
  public void f() { System.out.println("HasF.f()"); }
} ///:~
/**
* @Description: 如果我们将这个示例的其余代码都翻译成java，那么这些代码将不能编译
*/
class Manipulator<T> {
  private T obj;
  public Manipulator(T x) { obj = x; }
  // Error: cannot find symbol: method f():
  public void manipulate() {
    //obj.f();
  }
}

public class Manipulation {
  public static void main(String[] args) {
    HasF hf = new HasF();
    Manipulator<HasF> manipulator =
      new Manipulator<HasF>(hf);
    manipulator.manipulate();
  }
} ///:~
/**
* @Description: 由于有了擦除，java编译器无法将 manipulate()必须能够在obj上调用f()这一需求映射到HasF拥有f()这一事实上。
 * 为了调用f()，我们必须协助泛型类，给定泛型类的边界，以此告知编译器只能接受遵循这个边界的类型。这里重用了extends关键字。
 * 由于有了边界，下面的代码就可以编译了：
*/
class Manipulator2<T extends HasF> {
  private T obj;
  public Manipulator2(T x) { obj = x; }
  public void manipulate() { obj.f(); }
} ///:~
/**
* @Description: 边界<T extends Hasf>声明T必须具有类型HasF或者从HasF导出的类型。如果情况确实如此，那么就可以安全地在obj上调用f()了。
 *
 * 我们说泛型类型参数将 擦除到他的第一个边界(他可能会有多个边界，稍后你就会看到)，我们还提到了 类型参数的擦除 。编译器实际上会把类型参数
 * 替换为他的擦除，就像上面的示例一样。T擦除到了HasF，就好像在类的生命中用HasF替换了T一样
 *
 * 你可能已经正确的观察到，在Manipulation2.java中，泛型没有贡献任何好处。只需要很容易的自己去执行擦除，就可以创建处没有泛型的类
*/
class Manipulator3 {
  private HasF obj;
  public Manipulator3(HasF x) { obj = x; }
  public void manipulate() { obj.f(); }
} ///:~
/**
* @Description:  者提出了很重的一点：只有当你希望使用的类型参数比某个具体类型(以及他的所有子类型)更加“泛化”时----也就是说，当你希望代码能够
 * 跨多个类工作时，使用泛型才有所帮助。因此，类型参数和他们在有用的泛型代码中的应用，通常比简单的类替换要更复杂。
 * 但是，不能因此而认为<T extends HasF>形式的任何东西都是有缺陷的。例如，如果某个类有一个返回T的方法，那么泛型就有所帮助，因为他们之后将返回确切的类型：
*/
class ReturnGenericType<T extends HasF> {
  private T obj;
  public ReturnGenericType(T x) { obj = x; }
  public T get() { return obj; }
} ///:~
/**
* @Description: 必须查看所有的代码，并确定它是否“足够复杂”到必须使用泛型的程度
 * 我们将在本章稍后介绍有关边界的更多细节
*/
