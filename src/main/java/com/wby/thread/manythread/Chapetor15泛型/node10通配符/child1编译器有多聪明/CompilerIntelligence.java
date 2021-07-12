package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明;//: generics/CompilerIntelligence.java

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Apple;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Fruit;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Orange;

import java.util.Arrays;
import java.util.List;

/**
* @Description: 你可能会猜想自己被阻止去调用任何接受参数的方法，但是请考虑下面的程序：
*/
public class CompilerIntelligence {
  public static void main(String[] args) {
    List<? extends Fruit> flist =
      Arrays.asList(new Apple());
    Apple a = (Apple)flist.get(0); // No warning
    flist.contains(new Apple()); // Argument is 'Object'
    flist.indexOf(new Apple()); // Argument is 'Object'
  }
} ///:~
/**
* @Description: 可以看到，对contains方法和indexOf方法的调用，这两个方法都接受Apple对象作为参数，而这些调用都可以正常执行。这是否意味着编译器实际上
 * 将检查代码，以查看是否有某个特定的方法修改了他的对象？
 *
 *  通过查看ArrayList的文档，我们可以发现，编译器并没有这么聪明。尽管add方法接受一个具有泛型参数类型的参数，但是contains方法和indexOf方法都将
 *  接受Object类型的参数。因此当你指定一个ArrayList<? extends Fruit>时，add方法的参数就变成“? Extends Fruit”。
 *  从中可以看到，编译器并不能了解这里需要Fruit的哪个具体子类型，因此他不会接受任何类型的Fruit。如果先将Apple向上转型为Fruit，也无关紧要---编译器
 *  将直接拒绝对参数列表中设计通配符的方法(例如add方法)的调用。
 *
 *  可以在一个非常简单地Holder类中看到这一点：
*/
class Holder<T> {
  private T value;
  public Holder() {}
  public Holder(T val) { value = val; }
  public void set(T val) { value = val; }
  public T get() { return value; }
  public boolean equals(Object obj) {
    return value.equals(obj);
  }
  public static void main(String[] args) {
    Holder<Apple> Apple = new Holder<Apple>(new Apple());
    Apple d = Apple.get();
    Apple.set(d);
    // Holder<Fruit> Fruit = Apple; // Cannot upcast
    Holder<? extends Fruit> fruit = Apple; // OK
    Fruit p = fruit.get();
    d = (Apple)fruit.get(); // Returns 'Object'
    try {
      Orange c = (Orange)fruit.get(); // No warning
    } catch(Exception e) { System.out.println(e); }
    // fruit.set(new Apple()); // Cannot call set()
    // fruit.set(new Fruit()); // Cannot call set()
    System.out.println(fruit.equals(d)); // OK
  }
} /* Output: (Sample)
java.lang.ClassCastException: Apple cannot be cast to Orange
true
*///:~
/**
* @Description: Holder有一个接受T类型对象的set方法，一个get方法，以及一个接受Object对象的equals方法。正如看到的，如果创建一个Holder<Apple>，
 * 不能将其向上转型为Holder<Fruit>，但是可以将其向上转型为Holder<? extends Fruit>.
 * 如果调用get方法，他只会返回一个Fruit---这就是在给定“任何扩展自Fruit的对象”这一边界之后，他所能知道的一切了。
 * 如果能够了解更多的信息，那么你可以转型到某种具体的Fruit类型，而这不会导致任何警告，但是你存在着得到ClassCaseException的风险。
 * set方法不能工作与Apple或Fruit，因为set方法的参数也是“？ Extends Fruit”，这意味着他可以是任何事物，而编译器无法验证“任何事物”的类型安全性。
 *
 * 但是，equals方法工作良好，因为他将接受Object类型而并非T类型的参数。
 * 因此，编译器只关注传递进来和要返回的对象类型，她并不会分析代码，以查看是否执行了任何实际的写入和读取操作。
*/
