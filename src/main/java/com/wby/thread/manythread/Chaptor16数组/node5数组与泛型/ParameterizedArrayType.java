package com.wby.thread.manythread.Chaptor16数组.node5数组与泛型;//: arrays/ParameterizedArrayType.java


import com.wby.thread.manythread.Chaptor16数组.node1数组为什么特殊.BerylliumSphere;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: 通常数组不能与泛型很好地结合。你不能实例化具有参数化类型的数组：
 *    Peel<Banana>[] peels=new Peel<Banana>[10]
 * 这是非法的。擦除会移除参数类型信息，而数组必须知道他们所持有的确切类型，以强制保证类型安全
 *
 * 但是，你可以参数化数组本身的类型
*/
class ClassParameter<T> {
  public T[] f(T[] arg) { return arg; }
}

class MethodParameter {
  public static <T> T[] f(T[] arg) { return arg; }
}

public class ParameterizedArrayType {
  public static void main(String[] args) {
    Integer[] ints = { 1, 2, 3, 4, 5 };
    Double[] doubles = { 1.1, 2.2, 3.3, 4.4, 5.5 };
    Integer[] ints2 =
      new ClassParameter<Integer>().f(ints);
    Double[] doubles2 =
      new ClassParameter<Double>().f(doubles);
    ints2 = MethodParameter.f(ints);
    doubles2 = MethodParameter.f(doubles);
  }
} ///:~
/**
* @Description: 注意，使用参数化方法而不使用参数化类的方便之处在于：你不必为需要应用的每种不同的类型都使用一个参数去实例化这个类，并且你可以
 * 将其定义为静态的。当然，你不能总是选择使用参数化方法而不是参数化类，但是他应该成为首选。
 *
 * 正如上例证明的那样，不能创建泛型数组这一说法并不十分准确。编译器确实不让你实例化范型数组，但是，它允许你创建这种数组的引用。例如：
 *  List<String>[] ls；
 *  这条语句可以通过编译而不报任何错误。而且，尽管你不能创建实际的持有泛型的数组对象，但是你可以创建非泛型的数组，然后将其转型：
*/
class ArrayOfGenerics {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    List<String>[] ls;
    List[] la = new List[10];
    ls = (List<String>[])la; // "Unchecked" warning
    ls[0] = new ArrayList<String>();
    // Compile-time checking produces an error:
    //! ls[1] = new ArrayList<Integer>();

    // The problem: List<String> is a subtype of Object
    Object[] objects = ls; // So assignment is OK
    // Compiles and runs without complaint:
    objects[1] = new ArrayList<Integer>();

    // However, if your needs are straightforward it is
    // possible to create an array of generics, albeit
    // with an "unchecked" warning:
    List<BerylliumSphere>[] spheres =
            (List<BerylliumSphere>[])new List[10];
    for(int i = 0; i < spheres.length; i++)
      spheres[i] = new ArrayList<BerylliumSphere>();
  }
} ///:~
/**
* @Description: 一旦拥有了对List<String>的引用，你就会看到你将得到某些编译器检查。问题是数组是协变类型的，因此List<String>[]也是一个Object[]
 * 并且你可以利用这一点，将一个ArrayList<Integer>赋值到你的数组中，而不会有任何编译期或运行时错误。
 *
 * 如果你知道将来不会向上转型，并且需求相对简单，那么你仍旧可以创建泛型数组，他可以提供基本的编译期类型检查。
 * 但是，实际上，泛型容器总是比泛型数组是更好地选择。
 *
 * 一般，泛型在类或方法的边界处很有效，而在类或方法的内部，擦除通常使得泛型变得不适用。例如，你不能创建泛型数组：
*/
class ArrayOfGenericType<T> {
  T[] array; // OK
  @SuppressWarnings("unchecked")
  public ArrayOfGenericType(int size) {
    //! array = new T[size]; // Illegal
    array = (T[])new Object[size]; // "unchecked" Warning
  }
  // Illegal:
  //! public <U> U[] makeArray() { return new U[10]; }
} ///:~
/**
* @Description: 擦除再次成为了障碍---本例试图创建的类型已被擦除，因而是类型未知的数组。注意，你可以创建Object数组，然后将其转型，
 * 但是如果没有SuppressWarnings注解，将在编译期得到警告，因为这个数组没有真正持有或动态检查类型T。
 * 即，如果创建一个String[]，java在编译期和运行时都会强制要求只能将String对象置于该数组中。但是如果是Object[]，那么可以将基本类型之外的任何对象放在数组中。
*/
