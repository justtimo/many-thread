package com.wby.thread.manythread.Chaptor16数组.node7Arrays实用功能.child3数组元素的比较;//: arrays/CompType.java
// Implementing Comparable in a class.

import com.wby.thread.manythread.net.mindview.util.Generated;
import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 排序必须根据对象的实际类型执行比较操作。一种自然地解决方案是为每种不同的类型各编写一个不同的排序方法，但是这样的代码难以被新的类型复用。
 *
 * 程序设计的基本目标是“将保持不变的事物与会发生改变的事物相分离”，而这里，不变的是通用的排序方法，变化的是各种对象相互比较的方式。
 * 因此，不是将进行比较的代码编写成不同的程序，而是使用“策略设计模式”。
 * 通过使用策略，可以将“会发生变化的代码”封装在单独的类中(策略对象)，你可以将策略对象传递给总是相同的代码，这些代码将使用策略来完成其算法。
 * 通过这种方式，你能够用不同的对象来表示不同的比较方式，然后将他们传递给相同的排序代码。
 *
 * java有两种方式来提供比较功能。
 * 第一种实现Comparable接口，使你的类生来就具有比较能力。
 * 此接口简单，只有一个compareTo方法。此方法接收另一个Object为参数，如果当前对象小于参数则返回负值，如果相等则返回零，
 * 如果当前对象大于参数则返回正值。
 *
 * 西面的类实现了Comparable接口，并且使用java类库的方法Arrays.sort()演示了比较的效果：
*/
public class CompType implements Comparable<CompType> {
  int i;
  int j;
  private static int count = 1;
  public CompType(int n1, int n2) {
    i = n1;
    j = n2;
  }
  public String toString() {
    String result = "[i = " + i + ", j = " + j + "]";
    if(count++ % 3 == 0)
      result += "\n";
    return result;
  }
  public int compareTo(CompType rv) {
    return (i < rv.i ? -1 : (i == rv.i ? 0 : 1));
  }
  private static Random r = new Random(47);
  public static Generator<CompType> generator() {
    return new Generator<CompType>() {
      public CompType next() {
        return new CompType(r.nextInt(100),r.nextInt(100));
      }
    };
  }
  public static void main(String[] args) {
    CompType[] a =
      Generated.array(new CompType[12], generator());
    print("before sorting:");
    print(Arrays.toString(a));
    Arrays.sort(a);
    print("after sorting:");
    print(Arrays.toString(a));
  }
} /* Output:
before sorting:
[[i = 58, j = 55], [i = 93, j = 61], [i = 61, j = 29]
, [i = 68, j = 0], [i = 22, j = 7], [i = 88, j = 28]
, [i = 51, j = 89], [i = 9, j = 78], [i = 98, j = 61]
, [i = 20, j = 58], [i = 16, j = 40], [i = 11, j = 22]
]
after sorting:
[[i = 9, j = 78], [i = 11, j = 22], [i = 16, j = 40]
, [i = 20, j = 58], [i = 22, j = 7], [i = 51, j = 89]
, [i = 58, j = 55], [i = 61, j = 29], [i = 68, j = 0]
, [i = 88, j = 28], [i = 93, j = 61], [i = 98, j = 61]
]
*///:~
/**
* @Description: 在定义作比较的方法时，由你来负责决定将你的一个对象与另一个对象作比较的含义。这里在比较中只用到了i值，而忽略了j值。
 *
 * generator方法生成一个对象，此对象通过创建一个匿名内部类(见第8章)来实现Generator接口。
 * 该例中构建CompType对象，并使用随机数加以初始化。在main方法中，使用生成器填充CompType的数组，然后对其排序。
 * 如果没有实现Comparable接口，调用sort方法的时候会抛出ClassCastException这个运行时异常。因为sort方法需要把参数的类型转变为Comparable。
 *
 * 假设有人给你一个并没有实现COmparable的类，或者给你的类实现了Comparable，但是你不喜欢他的实现方式，你需要另外一种不同的比较方法。要解决这个问题，
 * 可以创建一个实现了Comparable接口(11章简要介绍过)的单独的类。这是策略设计模式的一个应用实例。
 *
 * 这个类有compare和equals两个方法。然而，不一定要实现equals方法，除非有特殊的性能需要，因为无论何时创建一个类，都是间接继承自Object，而Object
 * 带有equals方法。所以只需要默认的Object的equals方法就可以满足接口的要求了。
 *
 * Collections类包含一个reverseOrder方法，该方法可以产生一个Comparator，他可以反转自然地排序顺序。这很容易应用于CompType：
*/
class Reverse {
  public static void main(String[] args) {
    CompType[] a = Generated.array(
            new CompType[12], CompType.generator());
    print("before sorting:");
    print(Arrays.toString(a));
    Arrays.sort(a, Collections.reverseOrder());
    print("after sorting:");
    print(Arrays.toString(a));
  }
} /* Output:
before sorting:
[[i = 58, j = 55], [i = 93, j = 61], [i = 61, j = 29]
, [i = 68, j = 0], [i = 22, j = 7], [i = 88, j = 28]
, [i = 51, j = 89], [i = 9, j = 78], [i = 98, j = 61]
, [i = 20, j = 58], [i = 16, j = 40], [i = 11, j = 22]
]
after sorting:
[[i = 98, j = 61], [i = 93, j = 61], [i = 88, j = 28]
, [i = 68, j = 0], [i = 61, j = 29], [i = 58, j = 55]
, [i = 51, j = 89], [i = 22, j = 7], [i = 20, j = 58]
, [i = 16, j = 40], [i = 11, j = 22], [i = 9, j = 78]
]
*///:~
/**
* @Description: 也可以编写自己的Comparator。在这里的CompTYpe对象是基于j值而不是基于i值的。
*/
class CompTypeComparator implements Comparator<CompType> {
  public int compare(CompType o1, CompType o2) {
    return (o1.j < o2.j ? -1 : (o1.j == o2.j ? 0 : 1));
  }
}

class ComparatorTest {
  public static void main(String[] args) {
    CompType[] a = Generated.array(
            new CompType[12], CompType.generator());
    print("before sorting:");
    print(Arrays.toString(a));
    Arrays.sort(a, new CompTypeComparator());
    print("after sorting:");
    print(Arrays.toString(a));
  }
} /* Output:
before sorting:
[[i = 58, j = 55], [i = 93, j = 61], [i = 61, j = 29]
, [i = 68, j = 0], [i = 22, j = 7], [i = 88, j = 28]
, [i = 51, j = 89], [i = 9, j = 78], [i = 98, j = 61]
, [i = 20, j = 58], [i = 16, j = 40], [i = 11, j = 22]
]
after sorting:
[[i = 68, j = 0], [i = 22, j = 7], [i = 11, j = 22]
, [i = 88, j = 28], [i = 61, j = 29], [i = 16, j = 40]
, [i = 58, j = 55], [i = 20, j = 58], [i = 93, j = 61]
, [i = 98, j = 61], [i = 9, j = 78], [i = 51, j = 89]
]
*///:~

