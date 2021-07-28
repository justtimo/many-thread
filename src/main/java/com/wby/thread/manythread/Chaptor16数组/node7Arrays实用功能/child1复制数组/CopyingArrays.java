package com.wby.thread.manythread.Chaptor16数组.node7Arrays实用功能.child1复制数组;//: arrays/CopyingArrays.java
// Using System.arraycopy()

import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * @Description:  他有一套用于数组的static使用方法，其中有六个基本方法：equals方法用于比较两个数组是否相等(deepEquals用于多维数组)；fill方法在前面已经
 * 讨论过了；sort方法用于对数组排序；binarySearch方法用于在已经排序的数组中查找元素；toString方法产生数组的String表示；hashCode方法产生数组的散列码(
 * 17章中讨论)。所有这些方法对各种基本类型和Object类而重载过。
 * 此外，Arrays.asList()接受任意的序列或数组作为参数，并将其转变为List容器---这个方法11章已经介绍过了。
 *
 * 在讨论Arrays方法前，先看看另一个不属于Arrays但很有用的方法。
 */
/**
* @Description: java类库提供有static方法System.arrayCopy方法，用它复制驻足比用for循环快很多。
 * System.arraycopy方法针对所有类型做了重载。
 * 下面的例子就是用来处理int数组：
*/
public class CopyingArrays {
  public static void main(String[] args) {
    int[] i = new int[7];
    int[] j = new int[10];
    Arrays.fill(i, 47);
    Arrays.fill(j, 99);
    print("i = " + Arrays.toString(i));
    print("j = " + Arrays.toString(j));
    System.arraycopy(i, 0, j, 0, i.length);
    print("j = " + Arrays.toString(j));
    int[] k = new int[5];
    Arrays.fill(k, 103);
    System.arraycopy(i, 0, k, 0, k.length);
    print("k = " + Arrays.toString(k));
    Arrays.fill(k, 103);
    System.arraycopy(k, 0, i, 0, k.length);
    print("i = " + Arrays.toString(i));
    // Objects:
    Integer[] u = new Integer[10];
    Integer[] v = new Integer[5];
    Arrays.fill(u, new Integer(47));
    Arrays.fill(v, new Integer(99));
    print("u = " + Arrays.toString(u));
    print("v = " + Arrays.toString(v));
    System.arraycopy(v, 0, u, u.length/2, v.length);
    print("u = " + Arrays.toString(u));
  }
} /* Output:
i = [47, 47, 47, 47, 47, 47, 47]
j = [99, 99, 99, 99, 99, 99, 99, 99, 99, 99]
j = [47, 47, 47, 47, 47, 47, 47, 99, 99, 99]
k = [47, 47, 47, 47, 47]
i = [103, 103, 103, 103, 103, 47, 47]
u = [47, 47, 47, 47, 47, 47, 47, 47, 47, 47]
v = [99, 99, 99, 99, 99]
u = [47, 47, 47, 47, 47, 99, 99, 99, 99, 99]
*///:~
/**
* @Description:  System.arraycopy需要的参数有：源数组，表示从源数组中的什么位置开始复制的偏移量，表示从目标数组的什么位置开始复制的偏移量，以及需要复制
 * 的元素个数。当然，对数组的任何越界操作都会导致异常。
 *
 * 这个例子说明基本类型数组与对象数组都可以复制。然而，如果复制对象数组，那么只是复制了对象的引用---而不是对象本身的拷贝。
 * 这杯称为 浅复制 (参考在线补充材料了解更多内容)
 *
 * System.arraycopy不会执行自动包装和自动拆包，两个数组必须具有相同的确切类型。
*/
