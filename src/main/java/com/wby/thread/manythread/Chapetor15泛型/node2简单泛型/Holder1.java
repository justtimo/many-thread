package com.wby.thread.manythread.Chapetor15泛型.node2简单泛型;//: generics/Holder1.java


/**
* @Description: 许多原因促成了泛型的出现，最引人注目的原因是：为了创建 容器类(参考11与17章内容)
 *  容器，就是存放要使用的对象的地方。数组也是如此，不过与简单地数组相比，容器类更加灵活，具备更多不同的功能。实际上，所有的程序，在运行时
 *  都要求你持有一大堆对象，所以容器类算得上最重要的类库之一。
 *
 *  先看一个只能持有单个对象的类。当然，这个类可以明确指定其持有的对象的类型
*/
class Automobile {}

public class Holder1 {
  private Automobile a;

  public Holder1(Automobile a) {
    this.a = a;
  }

  Automobile get() {
    return a;
  }
} ///:~
/**
* @Description: 不过这个类可重用性就不怎么样，他无法持有其他类型的任何对象。
 * 在1.5之前，可以让这个类直接持有Object类型的对象
*/
class Holder2 {
  private Object a;
  public Holder2(Object a) {
    this.a = a;
  }
  public void set(Object a) {
    this.a = a;
  }
  public Object get() {
    return a;
  }
  public static void main(String[] args) {
    Holder2 h2 = new Holder2(new Automobile());
    Automobile a = (Automobile)h2.get();
    h2.set("Not an Automobile");
    String s = (String)h2.get();
    h2.set(1); // Autoboxes to Integer
    Integer x = (Integer)h2.get();
  }
} ///:~
/**
* @Description: 现在Holder2可以存储任何类型的数据。
 *  有些情况下，我们确实希望容器能够同时持有多种类型的对象。但是，我们只会使用容器来存储一种类型的对象。
 *  泛型的主要目的之一就是用来指定容器要持有什么类型的对象，而且由编译器保证类型的正确性
 *
 *  因此，与其使用Object，我们更喜欢暂时不指定类型，而是稍后再决定具体使用什么类型。
 *  要达到这个目的，需要使用类型参数，尖括号括住，放在类名后面。
 *  然后使用这个类的时候，再用实际的类型替换此类型参数。
 *  下面的例子中，T就是类型参数：
*/
class Holder3<T> {
  private T a;
  public Holder3(T a) { this.a = a; }
  public void set(T a) { this.a = a; }
  public T get() { return a; }
  public static void main(String[] args) {
    Holder3<Automobile> h3 =
            new Holder3<Automobile>(new Automobile());
    Automobile a = h3.get(); // No cast needed
    // h3.set("Not an Automobile"); // Error
    // h3.set(1); // Error
  }
} ///:~
/**
* @Description: 现在，当你创建Holder3时，必须明确指明想持有什么类型的对象，将其置于尖括号内。
 * 然后你就只能在Holder3中存入该类型(或其子类，因为多态与泛型不冲突)的对象。并且，从Holder3中取出他持有的对象时，自动的就是正确的类型。
 *
 * 这就是java泛型的核心概念：告诉编译器你想使用什么类型，然后编译器就会帮你处理一切细节。
 * 一般而言，你可以认为泛型与其他的类型差不多，只不过他们碰巧有类型参数罢了。稍后可以看到，使用泛型时，我们只需要指定他们的名称以及类型参数列表即可。
*/




















