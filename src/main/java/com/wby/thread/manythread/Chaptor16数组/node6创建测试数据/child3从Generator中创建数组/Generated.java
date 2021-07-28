//: net/mindview/util/Generated.java
package com.wby.thread.manythread.Chaptor16数组.node6创建测试数据.child3从Generator中创建数组;

import com.wby.thread.manythread.net.mindview.util.CollectionData;
import com.wby.thread.manythread.net.mindview.util.CountingGenerator;
import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

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
/**
* @Description: 即使数组a被初始化过，其中的那些值也在将其传递给Generated.array()之后被覆写了，因为这个方法会替换这些值(但是会保证原数组的正确性)。
 * b的初始化展示了从无到有的创建填充了元素的数组
 *
 * 泛型不能用于基本类型，而我们想用生成器来填充基本类型数组。为了解决这个问题，我们创建了一个转换器，他可以接受任意的包装器对象数组，并将其转换为相应的
 * 基本类型数组。如果没有这个工具，我们就必须为所有的基本类型创建特殊的生成器。
*/
class ConvertTo {
  public static boolean[] primitive(Boolean[] in) {
    boolean[] result = new boolean[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i]; // Autounboxing
    return result;
  }
  public static char[] primitive(Character[] in) {
    char[] result = new char[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static byte[] primitive(Byte[] in) {
    byte[] result = new byte[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static short[] primitive(Short[] in) {
    short[] result = new short[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static int[] primitive(Integer[] in) {
    int[] result = new int[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static long[] primitive(Long[] in) {
    long[] result = new long[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static float[] primitive(Float[] in) {
    float[] result = new float[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
  public static double[] primitive(Double[] in) {
    double[] result = new double[in.length];
    for(int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }
} ///:~
/**
* @Description:  primitive方法的每个版本都可以创建适当的具有恰当长度的基本类型数组 ，然后向其中复制包装器类型数组in中的每个元素，注意，下面的表达式中
 * 会自动进行 自动拆包：result[i]=in[i];
 *
 * 下面的例子展示了如何将ConverTo应用于两个版本的Generated.array()上：
*/
class PrimitiveConversionDemonstration {
  public static void main(String[] args) {
    Integer[] a = Generated.array(Integer.class,
            new CountingGenerator.Integer(), 15);
    int[] b = ConvertTo.primitive(a);
    System.out.println(Arrays.toString(b));
    boolean[] c = ConvertTo.primitive(
            Generated.array(Boolean.class,
                    new CountingGenerator.Boolean(), 7));
    System.out.println(Arrays.toString(c));
  }
} /* Output:
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
[true, false, true, false, true, false, true]
*///:~
/**
* @Description:  最后，西面的程序将使用RandomGenerator中的类来测试这些数组生成工具：
*/
class TestArrayGeneration {
  public static void main(String[] args) {
    int size = 6;
    boolean[] a1 = ConvertTo.primitive(Generated.array(
            Boolean.class, new RandomGenerator.Boolean(), size));
    print("a1 = " + Arrays.toString(a1));
    byte[] a2 = ConvertTo.primitive(Generated.array(
            Byte.class, new RandomGenerator.Byte(), size));
    print("a2 = " + Arrays.toString(a2));
    char[] a3 = ConvertTo.primitive(Generated.array(
            Character.class,
            new RandomGenerator.Character(), size));
    print("a3 = " + Arrays.toString(a3));
    short[] a4 = ConvertTo.primitive(Generated.array(
            Short.class, new RandomGenerator.Short(), size));
    print("a4 = " + Arrays.toString(a4));
    int[] a5 = ConvertTo.primitive(Generated.array(
            Integer.class, new RandomGenerator.Integer(), size));
    print("a5 = " + Arrays.toString(a5));
    long[] a6 = ConvertTo.primitive(Generated.array(
            Long.class, new RandomGenerator.Long(), size));
    print("a6 = " + Arrays.toString(a6));
    float[] a7 = ConvertTo.primitive(Generated.array(
            Float.class, new RandomGenerator.Float(), size));
    print("a7 = " + Arrays.toString(a7));
    double[] a8 = ConvertTo.primitive(Generated.array(
            Double.class, new RandomGenerator.Double(), size));
    print("a8 = " + Arrays.toString(a8));
  }
} /* Output:
a1 = [true, false, true, false, false, true]
a2 = [104, -79, -76, 126, 33, -64]
a3 = [Z, n, T, c, Q, r]
a4 = [-13408, 22612, 15401, 15161, -28466, -12603]
a5 = [7704, 7383, 7706, 575, 8410, 6342]
a6 = [7674, 8804, 8950, 7826, 4322, 896]
a7 = [0.01, 0.2, 0.4, 0.79, 0.27, 0.45]
a8 = [0.16, 0.87, 0.7, 0.66, 0.87, 0.59]
*///:~
/**
* @Description:  这些测试还可以确保ConvertTo.primitive方法的每个版本都可以正确的工作。
*/
