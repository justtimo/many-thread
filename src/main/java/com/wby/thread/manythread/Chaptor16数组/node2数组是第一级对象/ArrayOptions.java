package com.wby.thread.manythread.Chaptor16数组.node2数组是第一级对象;//: arrays/ArrayOptions.java
// Initialization & re-assignment of arrays.

import com.wby.thread.manythread.Chaptor16数组.node1数组为什么特殊.BerylliumSphere;

import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description:  数组标识符其实只是一个引用，指向堆中创建的一个真实对象，这个数组对象用以保存指向其他对象的引用。
 * 可以作为数组初始化语法的一部分隐式的创建此对象，或者用new表达式显式的创建。
 *
 * 下例总结了初始化数组的额各种方式，以及如何指向数组的引用赋值，使之指向另一个数组对象。此例子也说明，对象数组和基本类型数组在
 * 使用上几乎相同；唯一的区别是对象数组保存的是引用，基本类型数组直接保存基本类型的值。
*/
public class ArrayOptions {
  public static void main(String[] args) {
    // Arrays of objects:
    BerylliumSphere[] a; // Local uninitialized variable
    BerylliumSphere[] b = new BerylliumSphere[5];
    // The references inside the array are
    // automatically initialized to null:
    print("b: " + Arrays.toString(b));
    BerylliumSphere[] c = new BerylliumSphere[4];
    for(int i = 0; i < c.length; i++)
      if(c[i] == null) // Can test for null reference
        c[i] = new BerylliumSphere();
    // Aggregate initialization:
    BerylliumSphere[] d = { new BerylliumSphere(),
      new BerylliumSphere(), new BerylliumSphere()
    };
    // Dynamic aggregate initialization:
    a = new BerylliumSphere[]{
      new BerylliumSphere(), new BerylliumSphere(),
    };
    // (Trailing comma is optional in both cases)
    print("a.length = " + a.length);
    print("b.length = " + b.length);
    print("c.length = " + c.length);
    print("d.length = " + d.length);
    a = d;
    print("a.length = " + a.length);

    // Arrays of primitives:
    int[] e; // Null reference
    int[] f = new int[5];
    // The primitives inside the array are
    // automatically initialized to zero:
    print("f: " + Arrays.toString(f));
    int[] g = new int[4];
    for(int i = 0; i < g.length; i++)
      g[i] = i*i;
    int[] h = { 11, 47, 93 };
    // Compile error: variable e not initialized:
    //!print("e.length = " + e.length);
    print("f.length = " + f.length);
    print("g.length = " + g.length);
    print("h.length = " + h.length);
    e = h;
    print("e.length = " + e.length);
    e = new int[]{ 1, 2 };
    print("e.length = " + e.length);
  }
} /* Output:
b: [null, null, null, null, null]
a.length = 2
b.length = 5
c.length = 4
d.length = 3
a.length = 3
f: [0, 0, 0, 0, 0]
f.length = 5
g.length = 4
h.length = 3
e.length = 3
e.length = 2
*///:~
/**
* @Description:  数组a是一个尚未初始化的局部变量，在对他正确初始化之前，编译器不允许用此引用做任何事情。
 * 数组b初始化指向一个BerylliumSphere引用的数组，但其实并没有BerylliumSphere对象放置在数组中。但是，仍然可以查询数组的大小，因为b指向一个合法对象。
 * 这样做有个缺点：你无法知道此数组中确切的有多少元素，因为length只表示数组能够容纳度搜好元素。即，length是数组的大小，不是实际保存的元素个数。
 * 新生成一个数组对象时，其中所有的引用被自动初始化为null；所以检查其中的引用是否为null，就可以知道数组的某个位置是否存在对象。
 * 同样，基本类型的数组如果是数值类型，就被初始化为0；char被初始化为(char)O；布尔型为false
 *
 * 数组c表示，数组对象在创建之后，随即将数组的各个位置都赋值为BerylliumSphere对象。
 * 数组d表名使用“聚集初始化”语法创建数组对象(隐式的使用new在堆中创建，就像数组c一样)，并且以BerylliumSphere对象将其初始化的过程，这些操作只用了一条语句
 *
 * 下一个数组初始化可以看做是“动态的聚集初始化”。
 * 数组d采用的聚集初始化操作必须在定义d的位置，但若是使用第二种语法，可以在任意位置创建和初始化数组对象。
 * 例如，假设hide方法需要一个BerylliumSphere对象的数组作为输入参数。可以如下调用：
 *    hide(d);
 * 也可以动态的创建将要作为参数传递的数组：
 *    hide(new BerylliumSphere[]{new BerylliumSphere()})
 * 许多情况下，此语法使得代码书写变得更方便了。
 *
 * 表达式：
 *    a=b；
 * 说明如何将指向某个数组对象的引用赋给另一个数组对象，这与其他类型的对象引用没有区别。
 * 现在a和d都指向堆中的同一个数组对象。
 *
 * ArraySize.java的第二部分说明，基本类型数组的工作方式与对象数组一样，不过基本类型的数组直接存储基本类型数据的值。
*/
