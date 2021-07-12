package com.wby.thread.manythread.Chapetor15泛型.node7擦除的神秘之处.child3擦除的问题;//: generics/ErasureAndInheritance.java

/**
* @Description: 因此，擦除主要的理由是从非泛型化代码到泛化代码的转变过程，以及在不破坏现有类库的情况下，将泛型融入java语言。擦除使得现有的非泛型客户端代码
 * 能够在不改变的情况下继续使用，知道客户端准备好用泛型重写这些代码。这是一个崇高的冬季，因为他不会突然破坏所有现有的代码。
 *
 * 擦除的代价是显著的。泛型不能用显式的引用运行时类型的操作之中，例如转型、instanceof操作和new表达式。因为所有关于参数的类型信息都丢失了，无论何时，当你编写
 * 泛型代码时，都必须提醒自己，你只是 看起好像拥有 有关参数的类型信息而已。因此，如果你编写了下面这样的代码：
*/
class Foo<T>{
  T var;
}
/**
* @Description:  那么，看起来当你在创建Foo的实例时：
 *   Foo<Cat> f=new Foo<Cat>;
 *   class Foo中的代码应该知道现在工作与Cat上，而泛型语法也在强烈暗示：整个泪中的各个地方，类型T都在被替换。
 *   但事实并非如此，无论何时，在你编写这个类的代码时，必须提醒自己：他只是一个Object
 *
 *   另外，擦除和迁移兼容性意味着，使用泛型并不是强制的，尽管你可能希望这样：
*/
class GenericBase<T> {
  private T element;
  public void set(T arg) { arg = element; }
  public T get() { return element; }
}

class Derived1<T> extends GenericBase<T> {}

class Derived2 extends GenericBase {} // No warning

// class Derived3 extends GenericBase<?> {}
// Strange error:
//   unexpected type found : ?
//   required: class or interface without bounds

public class ErasureAndInheritance {
  //@SuppressWarnings("unchecked")
  public static void main(String[] args) {
    Derived2 d2 = new Derived2();
    Object obj = d2.get();
    d2.set(obj); // Warning here!
  }
} ///:~
/**
 * @Description: Derived2继承自GenericBase，但是没有任何泛型参数，编译器也不会发出警告。
 *  警告在set被调用时才会出现。实际上注释掉注解并没有出现警告：java版本为1.8；下面People的例子也证明了这点
 *
 *  为了关闭警告，java提供了一个注解，@SuppressWarnings("unchecked")
 *  注意，这个注解被放在可以生产这类警告的方法上，而不是整个类。当你要关闭警告时，要尽量“聚焦”，这样就不会因为过于宽泛的关闭警告，导致意外的遮蔽真正的问题。
 *
 *  可以推断，Derived3产生的错误意味着编译器期望得到一个原生基类。
 *
 *  当你希望将类型参数不要仅仅当做Object处理时，就需要付出额外努力来管理边界，并且与在C++这样的语言中获得参数化类型相比，你需要付出更多的努力来获得少的多的
 *  回报。这并不是说，对于大多数的编程问题而言，这些语言通常都会比java更得心应手；只是说，他们的参数化类型比java更灵活、更强大。
 */
class People <T>{
  private T name;
  private T age;

  public People(){

  }
  public T getName() {
    return name;
  }
  public void setName(T name) {
    this.name = name;
  }
  public T getAge() {
    return age;
  }
  public void setAge(T age) {
    this.age = age;
  }

}
class Child extends People{}

class FanXing2 {
  public static void main(String[] args) {
//   如果实例化对象时，不指定泛型，则认为是Object
    People people = new People();
    Object name = people.getName();
    people.setName(name);
//   里面的参数是Object类型
    people.setName("刘备");
    people.setAge("12");

    String ageString= (String) people.getAge();
    String nameString=(String) people.getName();

    System.out.println(nameString);
    System.out.println(ageString);


  }

}




















