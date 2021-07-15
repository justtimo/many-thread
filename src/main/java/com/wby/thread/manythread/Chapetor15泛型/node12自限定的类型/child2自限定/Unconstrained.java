package com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型.child2自限定;//: generics/Unconstrained.java

import com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型.child1古怪的循环泛型.BasicHolder;

/**
* @Description:  BasicHolder可以使用任何类型作为其泛型参数，就像下面看到的那样：
*/
class Other {}

class BasicOther extends BasicHolder<Other> {}

public class Unconstrained {
  public static void main(String[] args) {
    BasicOther b = new BasicOther(), b2 = new BasicOther();
    b.set(new Other());
    Other other = b.get();
    b.f();
  }
} /* Output:
Other
*///:~
/**
* @Description: 自限定将餐区额外的步骤，强制 泛型当做其自己的边界参数来使用。观察所产生的类可以如何使用以及不可以如何使用：
*/
//: generics/SelfBounding.java

class SelfBounded<T extends SelfBounded<T>> {
  T element;
  SelfBounded<T> set(T arg) {
    element = arg;
    return this;
  }
  T get() { return element; }
}

class A extends SelfBounded<A> {}
class B extends SelfBounded<A> {} // Also OK

class C extends SelfBounded<C> {
  C setAndGet(C arg) { set(arg); return get(); }
}

class D {}
// Can't do this:
// class E extends SelfBounded<D> {}
// Compile error: Type parameter D is not within its bound

// Alas, you can do this, so you can't force the idiom:
class F extends SelfBounded {}

class SelfBounding {
  public static void main(String[] args) {
    A a = new A();
    a.set(new A());
    a = a.set(new A()).get();
    a = a.get();
    C c = new C();
    c = c.setAndGet(new C());
  }
} ///:~
/**
* @Description: 自限定所做的就是要求：在继承关系中，向下面使用这个类：
 *    class A extends SelfBounded<A>{}
 *  这会强制要求将正在定义的类当做参数传递给基类。
 *  自限定的参数有何意义呢？他可以保证类型参数必须与正在被定义的类相同。正如在B类的定义中所看到的，还可以从使用了另一个SelfBounded参数的SelfBounded
 *  中导出，尽管在A类看到的用法看起来是主要的用法。对定义E的尝试说明不能使用不是SelfBounded的类型参数。
 *
 *  遗憾的是，F可以变异，不会有任何警告，因此自限定惯用法不是可强制执行的。如果他确实很重要，可以要求一个外部工具来确保不会使用原生类型来替代参数化类型。
 *
 *  注意，可以移除自限定这个限制，这样所有的类仍旧是可以编译的，但是E也是因此变得可编译：
*/
class NotSelfBounded<T> {
  T element;
  NotSelfBounded<T> set(T arg) {
    element = arg;
    return this;
  }
  T get() { return element; }
}

class A2 extends NotSelfBounded<A2> {}
class B2 extends NotSelfBounded<A2> {}

class C2 extends NotSelfBounded<C2> {
  C2 setAndGet(C2 arg) { set(arg); return get(); }
}

class D2 {}
// Now this is OK:
class E2 extends NotSelfBounded<D2> {} ///:~
/**
* @Description: 因此很明显，自限定限制只能强制作用于继承关系。如果使用自限定，就应该了解这个类所用的类型参数将与使用这个参数的类具有相同的基类型。
 * 这会强制要求使用这个类的每个人都要遵循这种形式。
 * 还可以将自限定用于泛型方法：
*/
class SelfBoundingMethods {
  static <T extends SelfBounded<T>> T f(T arg) {
    return arg.set(arg).get();
  }
  public static void main(String[] args) {
    A a = f(new A());
  }
} ///:~
/**
* @Description: 这可以防止这个方法被应用于除上述形式的自限定参数之外的任何事物上。
*/
