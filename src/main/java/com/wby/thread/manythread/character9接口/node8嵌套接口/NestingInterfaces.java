//: interfaces/nesting/NestingInterfaces.java
package com.wby.thread.manythread.character9接口.node8嵌套接口;

/**
 * 接口可以嵌套在类或其他接口中9。这揭示了许多非常有趣的特性∶
 */
class A {
  interface B {
    void f();
  }
  public class BImp implements B {
    public void f() {}
  }
  private class BImp2 implements B {
    public void f() {}
  }
  public interface C {
    void f();
  }
  class CImp implements C {
    public void f() {}
  }
  private class CImp2 implements C {
    public void f() {}
  }
  private interface D {
    void f();
  }
  private class DImp implements D {
    public void f() {}
  }
  public class DImp2 implements D {
    public void f() {}
  }
  public D getD() { return new DImp2(); }
  private D dRef;
  public void receiveD(D d) {
    dRef = d;
    dRef.f();
  }
}

interface E {
  interface G {
    void f();
  }
  // Redundant "public":
  public interface H {
    void f();
  }
  void g();
  // Cannot be private within an interface:
  //! private interface I {}
}

public class NestingInterfaces {
  public class BImp implements A.B {
    public void f() {}
  }
  class CImp implements A.C {
    public void f() {}
  }
  // Cannot implement a private interface except
  // within that interface's defining class:
  //! class DImp implements A.D {
  //!  public void f() {}
  //! }
  class EImp implements E {
    public void g() {}
  }
  class EGImp implements E.G {
    public void f() {}
  }
  class EImp2 implements E {
    public void g() {}
    class EG implements G {
      public void f() {}
    }
  }
  public static void main(String[] args) {
    A a = new A();
    // Can't access A.D:
    //! A.D ad = a.getD();
    // Doesn't return anything but A.D:
    //! A.DImp2 di2 = a.getD();
    // Cannot access a member of the interface:
    //! a.getD().f();
    // Only another A can do anything with getD():
    A a2 = new A();
    a2.receiveD(a.getD());
  }
} ///:~
/**
 * 在类中嵌套接口的语法是相当显而易见的，就像非嵌套接口一样，可以拥有public和"包访问"两种可视性。
 *
 * 作为一种新添加的方式，接口也可以被实现为private的，就像在A.D中所看到的（相同的语法既适用于嵌套接口，也适用于嵌套类）。那么private的嵌套接口能带来什么好处呢?
 * 读者可能会猜想，它只能够被实现为DImp中的一个private内部类，但是A.DImp2展示了它同样可以被实现为public类。但是，A，DImp2只能被其自身所使用。你无法说它实现了一个private接口D。
 * 因此，实现一个private接口只是一种方式，它可以强制该接口中的方法定义不要添加任何类型信息（也就是说，不允许向上转型）。
 *
 * getDO方法使我们陷入了一个进退两难的境地，这个问题与private接口相关∶它是一个返回对private接口的引用的public方法。你对这个方法的返回值能做些什么呢?
 * 在mainO中，可以看到数次尝试使用返回值的行为都失败了。只有一种方式可成功，那就是将返回值交给有权使用它的对象。在本例中，是另一个A通过receiveD（）方法来实现的。
 *
 * 接口E说明接口彼此之间也可以嵌套。然而，作用于接口的各种规则，特别是所有的接口元素都必须是public的，在此都会被严格执行。
 * 因此，嵌套在另一个接口中的接口自动就是public的，而不能声明为private的。
 *
 * NestingInterfaces展示了嵌套接口的各种实现方式。特别要注意的是，当实现某个接口时，并不需要实现嵌套在其内部的任何接口。
 * 而且，private接口不能在定义它的类之外被实现。
 *
 * 添加这些特性的最初原因可能是出于对严格的语法一致性的考虑，但是我总认为，一旦你了解了某种特性，就总能够找到它的用武之地。
 */
