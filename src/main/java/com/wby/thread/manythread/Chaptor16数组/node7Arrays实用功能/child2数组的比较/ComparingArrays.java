package com.wby.thread.manythread.Chaptor16数组.node7Arrays实用功能.child2数组的比较;//: arrays/ComparingArrays.java
// Using Arrays.equals()

import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: Arrays类提供了重载后的equals方法，用来比较整个数组。同样，此方法针对所有基本类型与Object都做了重载。
 * 数组相等的条件是元素个数必须相当，并且对应位置的元素也相等，这可以通过对每个元素使用equals方法作比较来判断。
 *
 * (对于基本类型，需要使用基本类型的包装器类的equals方法，例如，对于int类型使用Integer.equals()作比较)见下例：
*/
public class ComparingArrays {
  public static void main(String[] args) {
    int[] a1 = new int[10];
    int[] a2 = new int[10];
    Arrays.fill(a1, 47);
    Arrays.fill(a2, 47);
    print(Arrays.equals(a1, a2));
    a2[3] = 11;
    print(Arrays.equals(a1, a2));
    String[] s1 = new String[4];
    Arrays.fill(s1, "Hi");
    String[] s2 = { new String("Hi"), new String("Hi"),
      new String("Hi"), new String("Hi") };
    print(Arrays.equals(s1, s2));
  }
} /* Output:
true
false
true
*///:~
/**
* @Description: 最初，a1和a2完全相等，所以输出为true；然后改变其中一个元素，使得结果为false。
 * 在最后一个例子中，s1的所有元素都指向同一个对象，而数组s2包含五个相互独立的对象。然后，数组相等是基于内容的(通过Object.equals比较)，所以结果为true。
 *
*/
