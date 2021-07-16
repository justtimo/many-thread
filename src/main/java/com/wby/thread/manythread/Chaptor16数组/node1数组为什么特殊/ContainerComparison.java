package com.wby.thread.manythread.Chaptor16数组.node1数组为什么特殊;//: arrays/ContainerComparison.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * @Description: 到底是什么使得数组变得与众不同呢？？？
 * 数组与其他种类的容器区别有三方面：效率、类型和保存基本类型的能力。
 * java中，数组是效率最高的存储和随机访问对象引用序列的方式。
 * 数组就是一个简单地线性序列，这使得元素访问非常快速。
 * 但是代价是大小被固定，并且在其生命周期中不可改变。
 * 你可能会建议使用ArrayList，他可以通过创建新实例，然后把旧实例所有引用移到新实例中，从而实现空间的自动分配。但这种弹性需要开销，因此ArrayList效率比数组低得多
 * <p>
 * 数组和容器都可以保证你不能滥用他们。如果越界，都会得到异常。
 * <p>
 * 在泛型之前，其他容器类在处理对象时，都将他们看做没有任何具体类型。即，他们将这些对象都当做Object处理。
 * 数组之所以优于泛型之前的蓉欧过去，就是因为你可以创建一个数组去持有具体类型。这意味着，你可以通过编译期检查，防止插入错误类型和抽取不当类型。
 * 所以并不是说那种方法更不安全，只是如果编译时就能指出错误，会更优雅。
 * <p>
 * 数组可以持有基本类型，而泛型之前的容器则不能。但是有了泛型，容器就可以指定并检查他们所持有的对象的类型，并且有了自动包装机制，容器看起来还可以持有
 * 基本类型。
 * <p>
 * 下面是数组与泛型容器进行比较的例子：
 */
public class ContainerComparison {
  public static void main(String[] args) {
    BerylliumSphere[] spheres = new BerylliumSphere[10];
    for(int i = 0; i < 5; i++)
      spheres[i] = new BerylliumSphere();
    print(Arrays.toString(spheres));
    print(spheres[4]);

    List<BerylliumSphere> sphereList =
      new ArrayList<BerylliumSphere>();
    for(int i = 0; i < 5; i++)
      sphereList.add(new BerylliumSphere());
    print(sphereList);
    print(sphereList.get(4));

    int[] integers = { 0, 1, 2, 3, 4, 5 };
    print(Arrays.toString(integers));
    print(integers[4]);

    List<Integer> intList = new ArrayList<Integer>(
      Arrays.asList(0, 1, 2, 3, 4, 5));
    intList.add(97);
    print(intList);
    print(intList.get(4));
  }
} /* Output:
[Sphere 0, Sphere 1, Sphere 2, Sphere 3, Sphere 4, null, null, null, null, null]
Sphere 4
[Sphere 5, Sphere 6, Sphere 7, Sphere 8, Sphere 9]
Sphere 9
[0, 1, 2, 3, 4, 5]
4
[0, 1, 2, 3, 4, 5, 97]
4
*///:~
/**
* @Description: 这两种持有对象的方式都是类型检查型的，并且唯一明显的差异就是数组使用[]访问元素。
 * 数组和ArrayList之间的相似性是有意设计的，使得他们之间的切换很容易。但是，容器比数组明显具有更多的功能
 *
 * 随着自动包装机制的出现，容器已经可以与数组一样方便的应用于基本类型中了。
 * 数组进村的优点就是效率。
 * 然而，如果要解决更一般化的问题，数组就可能会受到过多的限制，因此还是会使用容器。
*/
