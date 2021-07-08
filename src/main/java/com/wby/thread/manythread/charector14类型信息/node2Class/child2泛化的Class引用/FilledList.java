package com.wby.thread.manythread.charector14类型信息.node2Class.child2泛化的Class引用;//: typeinfo/FilledList.java

import java.util.ArrayList;
import java.util.List;
/**
 * @Description:  向Class引用添加泛型语法仅仅是因为他提供了编译器检查。
 *  下面的例子使用了泛型类语法。他存储一个类引用，稍后又产生了一个List，填充List的对象是使用newInstance()，通过该引用生成的：
 */
class CountedInteger {
  private static long counter;
  private final long id = counter++;
  public String toString() {
    return Long.toString(id);
  }
}

public class FilledList<T> {
  private Class<T> type;
  public FilledList(Class<T> type) {
    this.type = type;
  }
  public List<T> create(int nElements) {
    List<T> result = new ArrayList<T>();
    try {
      for(int i = 0; i < nElements; i++)
        result.add(type.newInstance());
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }
  public static void main(String[] args) {
    FilledList<CountedInteger> fl =
      new FilledList<CountedInteger>(CountedInteger.class);
    System.out.println(fl.create(15));
  }
} /* Output:
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
*///:~
/**
* @Description: 注意：这个类必须假设与他一同工作的任何类型都具有一个默认的构造器(无参构造器)，不符合该条件，你将得到一个异常。编译器对该程序不会产生任何警告信息
*/
/**
* @Description: 当你将泛型语法用于Class对象时，newInstance()将返回该对象的确切类型，而不仅仅是ToyTest.java中看到的基本的Object。
 *  这在某些程度上有些受限，见下例：
*/
class GenericToyTest {
  public static void main(String[] args) throws Exception {
    Class<FancyToy> ftClass = FancyToy.class;
    // Produces exact type:
    FancyToy fancyToy = ftClass.newInstance();
    Class<? super FancyToy> up = ftClass.getSuperclass();
    // This won't compile:
    // Class<Toy> up2 = ftClass.getSuperclass();
    // Only produces Object:
    Object obj = up.newInstance();
  }
} ///:~
/**
* @Description: 如果你手头是超类，那编译器将只允许你声明超类引用是“某个类，他是FancyToy超类”
 *  就像在Class<? super FancyToy>所看到的，而不会接受Class<Toy>这样的声明。
 *  这看上去有些怪异，因为getSuperclass()返回的是 基类(不是接口)，并且编译器在编译期就知道他是什么类型----本例中就是Toy.class---而不仅仅是“某个类，他是FancyToy超类”
 *  不管怎么样，正是由于这种含糊性，up.newInstance()的返回值不是精确类型，而是Object
*/
