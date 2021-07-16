package com.wby.thread.manythread.Chaptor16数组.node4多维数组;//: arrays/MultidimensionalPrimitiveArray.java
// Creating multidimensional arrays.

import com.wby.thread.manythread.Chaptor16数组.node1数组为什么特殊.BerylliumSphere;

import java.util.Arrays;
import java.util.Random;

/**
* @Description:  创建多维数组很方便，基本类型的多维数组，可以通过花括号将每个向量分隔开：
*/
public class MultidimensionalPrimitiveArray {
  public static void main(String[] args) {
    int[][] a = {
      { 1, 2, 3, },
      { 4, 5, 6, },
    };
    System.out.println(Arrays.deepToString(a));
  }
} /* Output:
[[1, 2, 3], [4, 5, 6]]
*///:~
/**
* @Description: 每对花括号括起来的集合都会把你带到下一级数组。
 *
 * 下例使用了1.5的Arrays.deepToString方法，可以将多维数组转换为多个String。还可以使用new来分配数组，下面的三维数组
 * 就是在new表达式中分配的
*/
class ThreeDWithNew {
  public static void main(String[] args) {
    // 3-D array with fixed length:
    int[][][] a = new int[2][2][4];
    System.out.println(Arrays.deepToString(a));
  }
} /* Output:
[[[0, 0, 0, 0], [0, 0, 0, 0]], [[0, 0, 0, 0], [0, 0, 0, 0]]]
*///:~
/**
* @Description: 可以看到基本类型数组的值在不显示初始化的情况下，会被自动初始化。对象数组会被初始化为null。
 *
 * 数组中构成矩阵的每个向量都可以具有任意的长度(粗糙数组)
*/
class RaggedArray {
  public static void main(String[] args) {
    Random rand = new Random(47);
    // 3-D array with varied-length vectors:
    int[][][] a = new int[rand.nextInt(7)][][];
    for(int i = 0; i < a.length; i++) {
      a[i] = new int[rand.nextInt(5)][];
      for(int j = 0; j < a[i].length; j++)
        a[i][j] = new int[rand.nextInt(5)];
    }
    System.out.println(Arrays.deepToString(a));
  }
} /* Output:
[[], [[0], [0], [0, 0, 0, 0]], [[], [0, 0], [0, 0]], [[0, 0, 0], [0], [0, 0, 0, 0]], [[0, 0, 0], [0, 0, 0], [0], []], [[0], [], [0]]]
*///:~
/**
* @Description: 第一个new创建了数组，其一维长度有随机数决定，其他维的长度则没有定义。
 * 位于for循环内的第二个new则会决定第二维的长度；第三个new，第三维长度得以确定
 *
 * 可以使用类似的方式处理非基本类型的对象数组。下面，你可以看到如果用花括号把多个new表达式组织到一起：
*/
class MultidimensionalObjectArrays {
  public static void main(String[] args) {
    BerylliumSphere[][] spheres = {
            { new BerylliumSphere(), new BerylliumSphere() },
            { new BerylliumSphere(), new BerylliumSphere(),
                    new BerylliumSphere(), new BerylliumSphere() },
            { new BerylliumSphere(), new BerylliumSphere(),
                    new BerylliumSphere(), new BerylliumSphere(),
                    new BerylliumSphere(), new BerylliumSphere(),
                    new BerylliumSphere(), new BerylliumSphere() },
    };
    System.out.println(Arrays.deepToString(spheres));
  }
} /* Output:
[[Sphere 0, Sphere 1], [Sphere 2, Sphere 3, Sphere 4, Sphere 5], [Sphere 6, Sphere 7, Sphere 8, Sphere 9, Sphere 10, Sphere 11, Sphere 12, Sphere 13]]
*///:~
/**
* @Description: 可以看到 spheres是另一个粗糙数组，其每个对象列表的长度都是不同的
 *
 * 自动包装机制对数组初始化器也起作用：
*/
class AutoboxingArrays {
  public static void main(String[] args) {
    Integer[][] a = { // Autoboxing:
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
            { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 },
            { 51, 52, 53, 54, 55, 56, 57, 58, 59, 60 },
            { 71, 72, 73, 74, 75, 76, 77, 78, 79, 80 },
    };
    System.out.println(Arrays.deepToString(a));
  }
} /* Output:
[[1, 2, 3, 4, 5, 6, 7, 8, 9, 10], [21, 22, 23, 24, 25, 26, 27, 28, 29, 30], [51, 52, 53, 54, 55, 56, 57, 58, 59, 60], [71, 72, 73, 74, 75, 76, 77, 78, 79, 80]]
*///:~
/**
* @Description: 下面的例子展示了如何逐个的、部分的构建一个非基本类型的对象数组：
*/
class AssemblingMultidimensionalArrays {
  public static void main(String[] args) {
    Integer[][] a;
    a = new Integer[3][];
    for(int i = 0; i < a.length; i++) {
      a[i] = new Integer[3];
      for(int j = 0; j < a[i].length; j++)
        a[i][j] = i * j; // Autoboxing
    }
    System.out.println(Arrays.deepToString(a));
  }
} /* Output:
[[0, 0, 0], [0, 1, 2], [0, 2, 4]]
*///:~
/**
* @Description: i*j只是为了使置于Integer中的值变得有意思一些
 *  Arrays.deepToString方法对基本类型数组和对象数组都起作用：
*/
class MultiDimWrapperArray {
  public static void main(String[] args) {
    Integer[][] a1 = { // Autoboxing
            { 1, 2, 3, },
            { 4, 5, 6, },
    };
    Double[][][] a2 = { // Autoboxing
            { { 1.1, 2.2 }, { 3.3, 4.4 } },
            { { 5.5, 6.6 }, { 7.7, 8.8 } },
            { { 9.9, 1.2 }, { 2.3, 3.4 } },
    };
    String[][] a3 = {
            { "The", "Quick", "Sly", "Fox" },
            { "Jumped", "Over" },
            { "The", "Lazy", "Brown", "Dog", "and", "friend" },
    };
    System.out.println("a1: " + Arrays.deepToString(a1));
    System.out.println("a2: " + Arrays.deepToString(a2));
    System.out.println("a3: " + Arrays.deepToString(a3));
  }
} /* Output:
a1: [[1, 2, 3], [4, 5, 6]]
a2: [[[1.1, 2.2], [3.3, 4.4]], [[5.5, 6.6], [7.7, 8.8]], [[9.9, 1.2], [2.3, 3.4]]]
a3: [[The, Quick, Sly, Fox], [Jumped, Over], [The, Lazy, Brown, Dog, and, friend]]
*///:~
/**
* @Description: 在Integer和Double数组中，1.5的包装机制再次为我们创建了包装其对象
*/
