package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child2逆变;//: generics/SuperTypeWildcards.java

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Apple;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Fruit;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明.Jonathan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* @Description: 还可以走另外一条路，即使用 超类型通配符。这里，可以声明通配符是由某个特定类的任何基类来界定的，方法是指定<? super MyClass>,甚至或者使用
 *  类型参数：<? super T>(尽管你不能对泛型参数给出一个超类型边界：即不能声明<T super MyClass>)。这使得你可以安全的传递一个类型对象到泛型类型中。
 *  因此，有了超类型通配符，就可以向Collection中写入了
*/
public class SuperTypeWildcards {
  static void writeTo(List<? super Apple> apples) {
    apples.add(new Apple());
    apples.add(new Jonathan());
    // apples.add(new Fruit()); // Error
  }
} ///:~
/**
* @Description: 参数Apple是Apple的某种基类型的List，这样你就知道向其中添加Apple 或Apple的子类型是安全的。但是，既然Apple是 下界，那么你可以知道
 * 这样的List中添加Fruit是不安全的，因为这将使List敞开口子，从而可以向其中添加非Apple类型的对象，而这是违反静态类型安全的。
 *
 * 因此你可能会根据如何能够向一个泛型类型“写入”(传递给一个方法)，以及如何能够从一个泛型类型中“读取”(从一个方法中返回)，来着手思考子类型和超类型的边界
 *
 * 超类型边界放松了在可以向方法传递的参数上所做的限制：
*/
class GenericWriting {
  static <T> void writeExact(List<T> list, T item) {
    list.add(item);
  }
  static List<Apple> apples = new ArrayList<Apple>();
  static List<Fruit> fruit = new ArrayList<Fruit>();
  static void f1() {
    writeExact(apples, new Apple());
    // writeExact(fruit, new Apple()); // Error:
    // Incompatible types: found Fruit, required Apple
  }
  static <T> void
  writeWithWildcard(List<? super T> list, T item) {
    list.add(item);
  }
  static void f2() {
    writeWithWildcard(apples, new Apple());
    writeWithWildcard(fruit, new Apple());
  }
  public static void main(String[] args) { f1(); f2(); }
} ///:~
/**
* @Description:  writeExact方法使用了一个确切参数类型(无通配符)。在f1方法中，可以看到这工作良好---只要你只向List<Apple>中放置Apple。但是，
 * writeExact方法不允许将Apple放置到List<Fruit>中，即使知道这应该是可以的。
 *
 * 在writeWithWildcard方法中，其参数现在是List<? super T>,因此这个List将持有从T导出的某种具体类型，这样就可以安全的将一个T类型的对象或者从T
 * 导出的任何对象作为参数传递给List的方法。
 * 在f2方法中可以看到这一点，在这个方法中我们仍旧可以向前面那样，将Apple放置到List<Apple>中，但是现在我们还可以如你所期望的那样，将Apple放置到
 * List<Fruit>中。
 *
 * 我们可以执行下面这个相同的类型分析，作为对斜边和通配符的一个复习：
*/
class GenericReading {
  static <T> T readExact(List<T> list) {
    return list.get(0);
  }
  static List<Apple> apples = Arrays.asList(new Apple());
  static List<Fruit> fruit = Arrays.asList(new Fruit());
  // A static method adapts to each call:
  static void f1() {
    Apple a = readExact(apples);
    Fruit f = readExact(fruit);
    f = readExact(apples);
  }
  // If, however, you have a class, then its type is
  // established when the class is instantiated:
  static class Reader<T> {
    T readExact(List<T> list) { return list.get(0); }
  }
  static void f2() {
    Reader<Fruit> fruitReader = new Reader<Fruit>();
    Fruit f = fruitReader.readExact(fruit);
    // Fruit a = fruitReader.readExact(apples); // Error:
    // readExact(List<Fruit>) cannot be
    // applied to (List<Apple>).
  }
  static class CovariantReader<T> {
    T readCovariant(List<? extends T> list) {
      return list.get(0);
    }
  }
  static void f3() {
    CovariantReader<Fruit> fruitReader =
            new CovariantReader<Fruit>();
    Fruit f = fruitReader.readCovariant(fruit);
    Fruit a = fruitReader.readCovariant(apples);
  }
  public static void main(String[] args) {
    f1(); f2(); f3();
  }
} ///:~
/**
* @Description: 与之前一样， 第一个方法readExact使用了精确的类型。因此如果使用这个没有通配符的精确类型，就可以向List中写入和读取这个精确类型。
 * 另外，对于返回值，静态的泛型方法readExact可以有效地“适应”每个方法调用，并能够从List<Apple>中返回一个Apple，从List<Fruit>中返回一个Fruit，
 * 就像在f1方法中看到的那样。因此，如果可以摆脱静态泛型方法，那么当只是读取时，就不需要协变类型了。
 *
 * 但是，如果有一个泛型类，那么当你创建这个类的实例时，要为这个类确定参数。就像在f2中看到的，fruitReader实例可以从List<Fruit>中读取一个Fruit，
 * 因为这就是他的确切类型。但是List<Apple>还应该产生Fruit对象，而fruitReader不允许这么做。
 *
 * 为了修正这个问题，CovariantReader.readCovariant方法将接受List<? extends T>，因此，从这个列表中读取一个T是安全的(你知道在这个列表中的所有
 * 对象至少是一个T，并且可能是T的导出类)。在f3方法中，你可以看到现在可以从List<Apple>中读取Fruit了。
*/
