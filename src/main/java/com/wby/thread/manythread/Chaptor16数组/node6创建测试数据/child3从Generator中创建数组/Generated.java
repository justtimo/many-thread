//: net/mindview/util/Generated.java
package com.wby.thread.manythread.Chaptor16数组.node6创建测试数据.child3从Generator中创建数组;

import com.wby.thread.manythread.net.mindview.util.CollectionData;
import com.wby.thread.manythread.net.mindview.util.CountingGenerator;
import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.Arrays;

/**
* @Description:  为了接受Generator并产生数组，我们需要两个转换工具。
 *  第一个工具使用任意的Generator来产生Object子类型的数组。
 *  第二个工具，为了处理基本类型，工具接受任意基本类型的包装器类型数组，并产生相应的基本类型数组。
 *
 *  第一个工具有两种选项，并由重载的静态方法array来表示。该方法的第一个版本接受一个已有的数组，并使用某个Generator来填充他，而第二个版本接受一个Class
 *  独享，一个Generator和所需的元素数量，然后创建一个新数组，并使用所接收的Generator来填充他。
 *
 *  注意这个工具只能产生Object子类型的数组，而不能产生基本类型数组：
*/
public class Generated {
  // Fill an existing array:
  public static <T> T[] array(T[] a, Generator<T> gen) {
    return new CollectionData<T>(gen, a.length).toArray(a);
  }
  // Create a new array:
  @SuppressWarnings("unchecked")
  public static <T> T[] array(Class<T> type,
      Generator<T> gen, int size) {
    T[] a =
      (T[])java.lang.reflect.Array.newInstance(type, size);
    return new CollectionData<T>(gen, size).toArray(a);
  }
} ///:~
/**
* @Description:  CollectionData类将在17章定义，他会创建一个Collection对象，该对象中填充的元素是由生成器gen产生的，而元素的数量则有构造器
 * 第二个参数确定。
 * 所有的Collection子类型都拥有toArray方法，该方法将使用Collection中的元素来填充参数数组。
 *
 * 第二个方法使用反射来动态创建具有恰当类型和数量的新数组，然后与第一个方法相同的计数来填充该数组。
 *
 * 我们可以使用前一节中定义的CountingGenerator类中的某个生成器来测试Generated：
*/
class TestGenerated {
  public static void main(String[] args) {
    Integer[] a = { 9, 8, 7, 6 };
    System.out.println(Arrays.toString(a));
    a = Generated.array(a,new CountingGenerator.Integer());
    System.out.println(Arrays.toString(a));
    Integer[] b = Generated.array(Integer.class,
            new CountingGenerator.Integer(), 15);
    System.out.println(Arrays.toString(b));
  }
} /* Output:
[9, 8, 7, 6]
[0, 1, 2, 3]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
*///:~
