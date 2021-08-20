package com.wby.thread.manythread.character19枚举类型.node4values的神秘之处;
//: enumerated/Reflection.java
// Analyzing enums using reflection.

import com.wby.thread.manythread.net.mindview.util.OSExecute;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 前面已经提到，编译器为你创建的enum类都继承自Enum类。然而，如果你研究一下Enum类就会发现，它并没有valuesO）方法。可我们明明已经用过该方法了，
 * 难道存在某种"隐藏的"方法吗?我们可以利用反射机制编写一个简单的程序，来查看其中的究竟∶
 */
enum Explore { HERE, THERE }

public class Reflection {
  public static Set<String> analyze(Class<?> enumClass) {
    print("----- Analyzing " + enumClass + " -----");
    print("Interfaces:");
    for(Type t : enumClass.getGenericInterfaces())
      print(t);
    print("Base: " + enumClass.getSuperclass());
    print("Methods: ");
    Set<String> methods = new TreeSet<String>();
    for(Method m : enumClass.getMethods())
      methods.add(m.getName());
    print(methods);
    return methods;
  }
  public static void main(String[] args) {
    Set<String> exploreMethods = analyze(Explore.class);
    Set<String> enumMethods = analyze(Enum.class);
    print("Explore.containsAll(Enum)? " +
      exploreMethods.containsAll(enumMethods));
    printnb("Explore.removeAll(Enum): ");
    exploreMethods.removeAll(enumMethods);
    print(exploreMethods);
    // Decompile the code for the enum:
    OSExecute.command("javap Explore");
  }
} /* Output:
----- Analyzing class Explore -----
Interfaces:
Base: class java.lang.Enum
Methods:
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, values, wait]
----- Analyzing class java.lang.Enum -----
Interfaces:
java.lang.Comparable<E>
interface java.io.Serializable
Base: class java.lang.Object
Methods:
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, wait]
Explore.containsAll(Enum)? true
Explore.removeAll(Enum): [values]
Compiled from "Reflection.java"
final class Explore extends java.lang.Enum{
    public static final Explore HERE;
    public static final Explore THERE;
    public static final Explore[] values();
    public static Explore valueOf(java.lang.String);
    static {};
}
*///:~
/**
 * 答案是，valuesO是由编译器添加的static方法。可以看出，在创建Explore的过程中，编译器还为其添加了valueOfO方法。这可能有点令人迷惑，Enum类不是已经有valueOfO）方法了吗。
 * 不过Enum中的valueOfO方法需要两个参数，而这个新增的方法只需一个参数。由于这里使用的 Set只存储方法的名字，而不考虑方法的签名，所以在调用Explore.removeAlI（Enum）之后，就只剩下【values】了。
 *
 * 从最后的输出中可以看到，编译器将Explore标记为final类，所以无法继承自enum。其中还有一个static的初始化子句，稍后我们将学习如何重定义该句。
 *
 * 由于擦除效应（在第15章中介绍过），反编译无法得到Enum的完整信息，所以它展示的 Explore的父类只是一个原始的Enum，而非事实上的Enum<Explore>。
 *
 * 由于valuesO方法是由编译器插入到enum定义中的static方法，所以，如果你将enum实例向上转型为Enum，那么valuesO方法就不可访问了。不过，在Class中有一个getEnumConstantsO方法，
 * 所以即便Enum接口中没有valuesO方法，我们仍然可以通过Class对象取得所有enum实例∶
 */
enum Search { HITHER, YON }

class UpcastEnum {
  public static void main(String[] args) {
    Search[] vals = Search.values();
    Enum e = Search.HITHER; // Upcast
    // e.values(); // No values() in Enum
    for(Enum en : e.getClass().getEnumConstants())
      System.out.println(en);
  }
} /* Output:
HITHER
YON
*///:~
/**
 * 因为getEnumConstants（是Class上的方法，所以你甚至可以对不是枚举的类调用此方法
 */
class NonEnum {
  public static void main(String[] args) {
    Class<Integer> intClass = Integer.class;
    try {
      for(Object en : intClass.getEnumConstants())
        System.out.println(en);
    } catch(Exception e) {
      System.out.println(e);
    }
  }
} /* Output:
java.lang.NullPointerException
*///:~
/**
 * 只不过，此时该方法返回null，所以当你试图使用其返回的结果时会发生异常。
 */
