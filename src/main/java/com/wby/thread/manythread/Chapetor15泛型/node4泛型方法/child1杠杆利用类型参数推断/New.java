//: net/mindview/util/New.java
// Utilities to simplify generic container creation
// by using type argument inference.
package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法.child1杠杆利用类型参数推断;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Person;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Pet;

import java.util.*;

/**
* @Description:  人们对泛型有一个抱怨，使用泛型有时候需要向程序中加入更多的代码。
 *  考虑11章中的holding/MapOfList.java类，如果创建一个持有List的Map，就要向下面这样：
 *  Map<Person,List<? extends Pet>> petPeople=new HashMap<Person,List<? extends Pet>>();
 *  本章稍后会介绍表达式中问号与extends的用法。
 *
 *  看到了吧，你在重复自己做过的事情，编译器本来应该能够从泛型参数列表中的一个参数推断出另一个参数。
 *  可惜的是，编译器暂时还做不到。然而，在泛型方法中，类型参数推断可以为我们简化一部分工作。
 *  例如：我们可以编写一个工具类，它包含各种各样的static方法，专门用来创建各种常用的容器对象：
*/
public class New {

  public static <K,V> Map<K,V> map() {
    return new HashMap<K,V>();
  }
  public static <T> List<T> list() {
    return new ArrayList<T>();
  }
  public static <T> LinkedList<T> lList() {
    return new LinkedList<T>();
  }
  public static <T> Set<T> set() {
    return new HashSet<T>();
  }
  public static <T> Queue<T> queue() {
    return new LinkedList<T>();
  }
  // Examples:
  public static void main(String[] args) {
    Map<String, List<String>> sls = New.map();
    List<String> ls = New.list();
    LinkedList<String> lls = New.lList();
    Set<String> ss = New.set();
    Queue<String> qs = New.queue();
  }
} ///:~
/**
* @Description: main()方法演示了如何使用这个工具类，类型参数推断便面了重复的泛型参数列表。他同样可以应用于holding/MapOfList.java
 *
*/
class SimplerPets {
  public static void main(String[] args) {
    Map<Person, List<? extends Pet>> petPeople = New.map();
    // Rest of the code is the same...
  }
} ///:~
/**
* @Description:  对于类型参数推断而言，这是一个有趣的例子。不过，很难说它为我们带来了多少好处。
 *  如果某人阅读以上代码，他必须分析理解工具类New，以及New所隐含的功能。而这似乎与不使用New时(具有重复的类型参数列表的定义)的工作效率差不多。
 *  这真够讽刺的，要知道，我们引入New工具类的目的，正是为了使代码简单易读。不过，如果标准java类库要是能添加类似New.java这样的工具类的话，我们还是应该使用这样的工具类。
 *
 *  类型推断只对赋值操作有效，其他时候并不起作用。如果你将一个泛型方法调用的结果(例如New.map())作为参数，传递给另一个方法，这是编译器并不会执行类型推断。
 *  这种情况下，编译器认为：调用泛型方法后，其返回值被赋给一个Object类型的变量。下面的例子证明了这一点：
*/
class LimitsOfInference {
  static void
  f(Map<Person, List<? extends Pet>> petPeople) {}
  public static void main(String[] args) {
    // f(New.map()); // Does not compile
  }
} ///:~

/**
* @Description: 显式的类型说明
 *  在泛型方法中，可以显示的知名类型，不过这种语法很少使用。要显式的指明类型，必须在点操作符与方法名之间插入尖括号，然后把类型置于尖括号内。如果是在
 *  定义该方法的类的内部，必须在点操作符之前使用this关键字，如果是使用static的方法，必须在点操作符之前加上类名。
 *
 *  使用这种语法，可以解决LimitsOfInference.java中的问题：
*/
class ExplicitTypeSpecification {
  static void f(Map<Person, List<Pet>> petPeople) {}
  public static void main(String[] args) {
    f(New.<Person, List<Pet>>map());
  }
} ///:~
/**
* @Description: 当然，这种语法抵消了New类为我们带来的好处(即省去了大量的类型说明)，不过，只有在编写非赋值语句时，
 * 我们才需要这样的额外说明
*/





















