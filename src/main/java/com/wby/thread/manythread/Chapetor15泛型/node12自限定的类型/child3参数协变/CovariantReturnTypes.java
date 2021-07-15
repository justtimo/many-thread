package com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型.child3参数协变;//: generics/CovariantReturnTypes.java

/**
* @Description:   自限定类型的价值在于他们可以产生 协变参数类型---方法参数类型会随着子类变化而变化。
 *
 * 尽管自限定类型还可以产生于子类类型相同的返回类型，但是这并不十分重要，因为 协变返回类型 是在1.5中引入的：
*/
class Base {}

class Derived extends Base {}

interface OrdinaryGetter {
  Base get();
}

interface DerivedGetter extends OrdinaryGetter {
  // Return type of overridden method is allowed to vary:
  Derived get();
}

public class CovariantReturnTypes {
  void test(DerivedGetter d) {
    Derived d2 = d.get();
  }
} ///:~
/**
* @Description:  DerivedGetter中的get方法覆盖了OrdinaryGetter中的get，并返回了一个从OrdinaryGetter.get的返回类型中导出的类型。
 * 尽管这是完全符合逻辑的(导出类方法应该能够返回比他覆盖的基类方法更具体的类型)但是这在早先的java版本中是不合法的
 *
 * 自限定泛型事实上将产生确切的导出类型作为其返回值，就像在get中看到的一样
*/
interface GenericGetter<T extends GenericGetter<T>> {
  T get();
}

interface Getter extends GenericGetter<Getter> {}

class GenericsAndReturnTypes {
  void test(Getter g) {
    Getter result = g.get();
    GenericGetter gg = g.get(); // Also the base type
  }
} ///:~
/**
* @Description:  注意这段代码不能编译，除非是使用包括了协变返回类型的1.5
 * 然而，在非泛型代码中， 参数 类型不能随子类变化而变化
*/
class OrdinarySetter {
  void set(Base base) {
    System.out.println("OrdinarySetter.set(Base)");
  }
}

class DerivedSetter extends OrdinarySetter {
  void set(Derived derived) {
    System.out.println("DerivedSetter.set(Derived)");
  }
}

class OrdinaryArguments {
  public static void main(String[] args) {
    Base base = new Base();
    Derived derived = new Derived();
    DerivedSetter ds = new DerivedSetter();
    ds.set(derived);
    ds.set(base); // Compiles: overloaded, not overridden!
  }
} /* Output:
DerivedSetter.set(Derived)
OrdinarySetter.set(Base)
*///:~
/**
* @Description:  ds.set(derived)和ds.set(base)都是合法的，因此DerivedSetter.set()没有覆盖OrdinarySetter.set()，而是重载了这个方法。
 * 从输出中可以看到，在DerivedSetter中有两个方法，因此基类版本仍然可用，因此可以证明他被重载过。
 *
 * 但是，在使用自限定类型时，在导出类中只有一个方法，并且这个方法接受导出类型而不是基类型为参数：
*/
interface SelfBoundSetter<T extends SelfBoundSetter<T>> {
  void set(T arg);
}

interface Setter extends SelfBoundSetter<Setter> {}

class SelfBoundingAndCovariantArguments {
  void testA(Setter s1, Setter s2, SelfBoundSetter sbs) {
    s1.set(s2);
    // s1.set(sbs); // Error:
    // set(Setter) in SelfBoundSetter<Setter>
    // cannot be applied to (SelfBoundSetter)
  }
} ///:~
/**
* @Description:  编译器不能识别将基类型当作参数传递给set()的尝试，因为没有任何方法具有这样的签名。实际上，这个参数已经被覆盖。
 *
 * 如果不使用自限定类型，普通的继承机制就会介入，而你将能够重载，就像在非泛型的情况下一样：
*/
class GenericSetter<T> { // Not self-bounded
  void set(T arg){
    System.out.println("GenericSetter.set(Base)");
  }
}

class DerivedGS extends GenericSetter<Base> {
  void set(Derived derived){
    System.out.println("DerivedGS.set(Derived)");
  }
}

class PlainGenericInheritance {
  public static void main(String[] args) {
    Base base = new Base();
    Derived derived = new Derived();
    DerivedGS dgs = new DerivedGS();
    dgs.set(derived);
    dgs.set(base); // Compiles: overloaded, not overridden!
  }
} /* Output:
DerivedGS.set(Derived)
GenericSetter.set(Base)
*///:~
/**
* @Description:  这段代码在模仿OrdinaryArguments.java 在那个例子中，DerivedSetter继承自包含一个set(Base)的OrdinarySetter。
 * 而这里，DerivedGS继承自泛型创建的也包含一个set(Base)的GenericSetter<Base>。就像OrdinaryArguments.java一样，你可以从输出中看到，DerivedGS
 * 包含两个set()的重载版本。
 * 如果不使用自限定，将重载参数类型。如果使用自限定，只能获得某个方法的一个版本，他将接受确切的参数类型。
*/
