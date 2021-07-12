package com.wby.thread.manythread.Chapetor15泛型.node8擦除的补偿.child1创建类型实例;//: generics/InstantiateGenericType.java

import static com.wby.thread.manythread.net.mindview.util.Print.print;

class ClassAsFactory<T> {
  T x;
  public ClassAsFactory(Class<T> kind) {
    try {
      x = kind.newInstance();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}

class Employee {}

public class InstantiateGenericType {
  public static void main(String[] args) {
    ClassAsFactory<Employee> fe =
      new ClassAsFactory<Employee>(Employee.class);
    print("ClassAsFactory<Employee> succeeded");
    try {
      ClassAsFactory<Integer> fi =
        new ClassAsFactory<Integer>(Integer.class);
    } catch(Exception e) {
      print("ClassAsFactory<Integer> failed");
    }
  }
} /* Output:
ClassAsFactory<Employee> succeeded
ClassAsFactory<Integer> failed
*///:~
/**
* @Description:  这可以编译，但是会因为ClassAsFactory<Integer>而失败，因为Integer没有任何默认的构造器。因为这个错误不是在编译期捕获的，所以
 * SUn的伙伴们对这种方式并不赞成，他们建议使用显式的工厂，并将限制类型，使得只能接受实现了这个工厂的类：
*/
interface FactoryI<T> {
  T create();
}

class Foo2<T> {
  private T x;
  public <F extends FactoryI<T>> Foo2(F factory) {
    x = factory.create();
  }
  // ...
}

class IntegerFactory implements FactoryI<Integer> {
  public Integer create() {
    return new Integer(0);
  }
}

class Widget {
  public static class Factory implements FactoryI<Widget> {
    public Widget create() {
      return new Widget();
    }
  }
}

class FactoryConstraint {
  public static void main(String[] args) {
    new Foo2<Integer>(new IntegerFactory());
    new Foo2<Widget>(new Widget.Factory());
  }
} ///:~
/**
* @Description: 注意，这确实只是传递Class<T>的一种变体。两种方式都传递了工厂对象，Class<T>碰巧是内建的工厂对象，而上面的方式创建了一个显式的工厂对象，
 * 但是你却获得了编译期检查。
 *
 * 另种方式是 模板方法 设计模式。下面的例子中，get方法是模板方法，而create方法是在子类中定义的、用来产生子类类型的对象：
*/
abstract class GenericWithCreate<T> {
  final T element;
  GenericWithCreate() { element = create(); }
  abstract T create();
}

class X {}

class Creator extends GenericWithCreate<X> {
  X create() { return new X(); }
  void f() {
    System.out.println(element.getClass().getSimpleName());
  }
}

class CreatorGeneric {
  public static void main(String[] args) {
    Creator c = new Creator();
    c.f();
  }
} /* Output:
X
*///:~












