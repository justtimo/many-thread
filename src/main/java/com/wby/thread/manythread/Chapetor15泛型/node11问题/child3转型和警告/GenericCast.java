package com.wby.thread.manythread.Chapetor15泛型.node11问题.child3转型和警告;//: generics/GenericCast.java

import com.wby.thread.manythread.Chapetor15泛型.node8擦除的补偿.child1创建类型实例.Widget;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
* @Description: 使用带有泛型类型参数的转型或instanceof不会有任何效果。下面的容器在内部将各个值存储为Object，并在获取这些值时，在将他们转型回T：
*/
class FixedSizeStack<T> {
  private int index = 0;
  private Object[] storage;
  public FixedSizeStack(int size) {
    storage = new Object[size];
  }
  public void push(T item) { storage[index++] = item; }
  @SuppressWarnings("unchecked")
  public T pop() { return (T)storage[--index]; }
}

public class GenericCast {
  public static final int SIZE = 10;
  public static void main(String[] args) {
    FixedSizeStack<String> strings =
      new FixedSizeStack<String>(SIZE);
    for(String s : "A B C D E F G H I J".split(" "))
      strings.push(s);
    for(int i = 0; i < SIZE; i++) {
      String s = strings.pop();
      System.out.print(s + " ");
    }
  }
} /* Output:
J I H G F E D C B A
*///:~
/**
* @Description:  如果没有@SuppressWarnings("unchecked")注解，编译器将对pop方法产生“unchecked cast”警告。由于擦除的原因，编译器无法知道
 * 这个转型是否是安全的，并且pop方法实际上并没有执行恩和转型。这是因为，T被擦除到他的第一个边界，默认情况下是Object，因此pop方法实际上只是将Object
 * 转型为Object。
 *
 * 有时，泛型没有消除对转型的需要，这就会有编译器产生警告，而这个警告是不恰当的。例如：
*/
class NeedCasting {
  @SuppressWarnings("unchecked")
  public void f(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(args[0]));
    List<Widget> shapes = (List<Widget>)in.readObject();
  }
} ///:~
/**
* @Description: 正如你将在下账学到的那样，readObject方法无法知道他正在读取的是什么，因此他返回的是必须转型的对象。但是当注释掉@SuppressWarnings("unchecked")
 * 注解，并编译这个程序时，就会得到下面的警告：
 *
 * 如果遵循这条指示，使用-Xlint:unchecked来重新编译：
 *
 * 你会被强制要求转型，但是又被告知不应该转型。为了解决这个问题，必须使用在1.5中引入的新的转型形式，即通过泛型类来转型：
*/
class ClassCasting {
  @SuppressWarnings("unchecked")
  public void f(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(args[0]));
    // Won't Compile:
//    List<Widget> lw1 =
//    List<Widget>.class.cast(in.readObject());
    List<Widget> lw2 = List.class.cast(in.readObject());
  }
} ///:~
/**
* @Description: 但是不能转型到实际类型(List<Widget>).也就是说，不能声明：
 *    List<Widget>.class.cast(in.readObject());
 *  仍旧会得到一个警告。
*/
