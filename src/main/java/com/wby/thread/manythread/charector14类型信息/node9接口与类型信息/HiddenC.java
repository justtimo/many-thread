//: typeinfo/packageaccess/HiddenC.java
package com.wby.thread.manythread.charector14类型信息.node9接口与类型信息;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
* @Description: 在这个包中唯一public的部分是HiddenC，在被调用时将产生A接口类型的对象。
 * 这里有趣的是：即使你从makeA()返回的是C类型，你再包外部任然不能使用A之外的任何方法，因为你不能在包外部命名C。
*/
class C implements A {
  public void f() { print("public C.f()"); }
  public void g() { print("public C.g()"); }
  void u() { print("package C.u()"); }
  protected void v() { print("protected C.v()"); }
  private void w() { print("private C.w()"); }
}

public class HiddenC {
  public static A makeA() {
    return new C();
  }
} ///:~
/**
* @Description: 如果你试图将其向下转型为C，将被禁止，因为在包的外部没有任何C类型可用：
*/
class HiddenImplementation {
  public static void main(String[] args) throws Exception {
    A a = HiddenC.makeA();
    a.f();
    System.out.println(a.getClass().getName());
    // Compile error: cannot find symbol 'C':
    /* if(a instanceof C) {
      C c = (C)a;
      c.g();
    } */
    // Oops! Reflection still allows us to call g():
    callHiddenMethod(a, "g");
    // And even methods that are less accessible!
    callHiddenMethod(a, "u");
    callHiddenMethod(a, "v");
    callHiddenMethod(a, "w");
  }
  static void callHiddenMethod(Object a, String methodName)
          throws Exception {
    Method g = a.getClass().getDeclaredMethod(methodName);
    g.setAccessible(true);
    g.invoke(a);
  }
} /* Output:
public C.f()
typeinfo.packageaccess.C
public C.g()
package C.u()
protected C.v()
private C.w()
*///:~
/**
* @Description: 正如你所看到的，通过使用反射，仍旧可以达到并调用所有方法，设置是private方法
 * 如果知道方法名，你就可以在其Method对象上调用setAccessible(true)，就像在callHiddenMethod()中看到的那样。
 *
 * 你可能会认为，可以通过只发布编译后的代码来阻止这种情况，但这并不能解决问题。
 * 因为只需要运行javap，一个随JDK发布的反编译器就可以突破这一限制。下面是一个使用他的命令行：
 *    javap -private C
 * -private标志表示所有的成员都应该显示，包括私有成员，下面是输出：
*/
/**
* @Description: 因此任何人都能获取你最私有的方法的名字和签名，然后使用它们
 *  如果你将接口实现为一个私有内部类，与会怎么样呢？下面演示了这个例子：
*/
class InnerA {
  private static class C implements A {
    public void f() { print("public C.f()"); }
    public void g() { print("public C.g()"); }
    void u() { print("package C.u()"); }
    protected void v() { print("protected C.v()"); }
    private void w() { print("private C.w()"); }
  }
  public static A makeA() { return new C(); }
}

class InnerImplementation {
  public static void main(String[] args) throws Exception {
    A a = InnerA.makeA();
    a.f();
    System.out.println(a.getClass().getName());
    // Reflection still gets into the private class:
    HiddenImplementation.callHiddenMethod(a, "g");
    HiddenImplementation.callHiddenMethod(a, "u");
    HiddenImplementation.callHiddenMethod(a, "v");
    HiddenImplementation.callHiddenMethod(a, "w");
  }
} /* Output:
public C.f()
InnerA$C
public C.g()
package C.u()
protected C.v()
private C.w()
*///:~
/**
* @Description: 这里对反射仍旧没有隐藏任何东西。那么如果是匿名类呢？
*/
class AnonymousA {
  public static A makeA() {
    return new A() {
      public void f() { print("public C.f()"); }
      public void g() { print("public C.g()"); }
      void u() { print("package C.u()"); }
      protected void v() { print("protected C.v()"); }
      private void w() { print("private C.w()"); }
    };
  }
}

class AnonymousImplementation {
  public static void main(String[] args) throws Exception {
    A a = AnonymousA.makeA();
    a.f();
    System.out.println(a.getClass().getName());
    // Reflection still gets into the anonymous class:
    HiddenImplementation.callHiddenMethod(a, "g");
    HiddenImplementation.callHiddenMethod(a, "u");
    HiddenImplementation.callHiddenMethod(a, "v");
    HiddenImplementation.callHiddenMethod(a, "w");
  }
} /* Output:
public C.f()
AnonymousA$1
public C.g()
package C.u()
protected C.v()
private C.w()
*///:~
/**
* @Description: 看起来没有任何方式可以阻止反射到达并调用那些非公共访问权限的方法。对域来说，确实如此，即便是private域
 * 下面例子演示了这种情况：
*/
class WithPrivateFinalField {
  private int i = 1;
  private final String s = "I'm totally safe";
  private String s2 = "Am I safe?";
  public String toString() {
    return "i = " + i + ", " + s + ", " + s2;
  }
}

class ModifyingPrivateFields {
  public static void main(String[] args) throws Exception {
    WithPrivateFinalField pf = new WithPrivateFinalField();
    System.out.println(pf);
    Field f = pf.getClass().getDeclaredField("i");
    f.setAccessible(true);
    System.out.println("f.getInt(pf): " + f.getInt(pf));
    f.setInt(pf, 47);
    System.out.println(pf);
    f = pf.getClass().getDeclaredField("s");
    f.setAccessible(true);
    System.out.println("f.get(pf): " + f.get(pf));
    f.set(pf, "No, you're not!");
    System.out.println(pf);
    f = pf.getClass().getDeclaredField("s2");
    f.setAccessible(true);
    System.out.println("f.get(pf): " + f.get(pf));
    f.set(pf, "No, you're not!");
    System.out.println(pf);
  }
} /* Output:
i = 1, I'm totally safe, Am I safe?
f.getInt(pf): 1
i = 47, I'm totally safe, Am I safe?
f.get(pf): I'm totally safe
i = 47, I'm totally safe, Am I safe?
f.get(pf): Am I safe?
i = 47, I'm totally safe, No, you're not!
*///:~
/**
* @Description: 但是final域实际上在遭遇修改时是安全的。运行时系统会在不抛出异常的情况下接受任何修改尝试，但是实际上不会发生任何修改。
 *  通常，所有这些违反访问权限的操作并非最糟糕的事。
 *  如果有人使用这样的技术去调用标识为private或包访问权限的方法(很明显这些访问权限标识这些人不应该调用它)，那么对他们来说，如果你修改了这些方法的
 *  某些方面导致他们出了什么问题，他们不应该抱怨。
 *  另一方面，总是在类中留下后面这一事实，也许可以使你能够解决某些特定类型的问题，但如果不这样做，这些问题将难以或者不可能解决，通常反射的好处是不可否认的。
*/
