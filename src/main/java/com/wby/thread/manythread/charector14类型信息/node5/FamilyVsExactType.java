//: typeinfo/FamilyVsExactType.java
// The difference between instanceof and class
package com.wby.thread.manythread.charector14类型信息.node5;

import static com.wby.thread.manythread.net.mindview.util.Print.print;


/**
 * @Description:  在查新类型信息时，以instance的形式(即以instanceof的形式或isInstance()的形式，它们产生相同的结果)与直接比较Class对象有一个很重要的差别。
 *
 * 下面的例子演示了这种差别：
 */
class Base {}

class Derived extends Base {}

public class FamilyVsExactType {
  static void test(Object x) {
    print("Testing x of type " + x.getClass());
    print("x instanceof Base " + (x instanceof Base));
    print("x instanceof Derived "+ (x instanceof Derived));
    print("Base.isInstance(x) "+ Base.class.isInstance(x));
    print("Derived.isInstance(x) " +
      Derived.class.isInstance(x));
    print("x.getClass() == Base.class " +
      (x.getClass() == Base.class));
    print("x.getClass() == Derived.class " +
      (x.getClass() == Derived.class));
    print("x.getClass().equals(Base.class)) "+
      (x.getClass().equals(Base.class)));
    print("x.getClass().equals(Derived.class)) " +
      (x.getClass().equals(Derived.class)));
  }
  public static void main(String[] args) {
    test(new Base());
    test(new Derived());
  }
} /* Output:
Testing x of type class typeinfo.Base
x instanceof Base true
x instanceof Derived false
Base.isInstance(x) true
Derived.isInstance(x) false
x.getClass() == Base.class true
x.getClass() == Derived.class false
x.getClass().equals(Base.class)) true
x.getClass().equals(Derived.class)) false
Testing x of type class typeinfo.Derived
x instanceof Base true
x instanceof Derived true
Base.isInstance(x) true
Derived.isInstance(x) true
x.getClass() == Base.class false
x.getClass() == Derived.class true
x.getClass().equals(Base.class)) false
x.getClass().equals(Derived.class)) true
*///:~
/**
* @Description: test()方法使用了两种形式的instanceof作为参数来执行类型检查。然后获取Class引用，并用==和equals()来检查Class对象是否相等。
 *  令人放心的是，instanceof和isInstance()生成的结果完全相同，equals()和==也一样。
 *  但是这两组测试得出的结论却不相同。instanceof保持了类型的概念，他指的是“你是这个类吗，或者你是这个类的派生类吗”而如果用==比较实际的Class对象，
 *  就没有考虑继承---它或者是这个确切的类型，或者不是。
*/
