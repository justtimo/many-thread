package com.wby.thread.manythread.Chaptor16数组.node7Arrays实用功能.child5在已排序的数组中查找;//: arrays/ArraySearching.java
// Using Arrays.binarySearch().


import com.wby.thread.manythread.net.mindview.util.ConvertTo;
import com.wby.thread.manythread.net.mindview.util.Generated;
import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 如果数组已经排序好了，就可以使用Arrays.binarySearch()执行快速查找。如果要对未排序的数组使用binarySearch()，那么将产生不可预料
 * 的结果。下面的例子使用RandIntGenerator.Integer填充数组，然后再使用同样的生成器生成要查找的值：
*/
public class ArraySearching {
  public static void main(String[] args) {
    Generator<Integer> gen =
      new RandomGenerator.Integer(1000);
    int[] a = ConvertTo.primitive(
      Generated.array(new Integer[25], gen));
    Arrays.sort(a);
    print("Sorted array: " + Arrays.toString(a));
    while(true) {
      int r = gen.next();
      int location = Arrays.binarySearch(a, r);
      if(location >= 0) {
        print("Location of " + r + " is " + location +
          ", a[" + location + "] = " + a[location]);
        break; // Out of while loop
      }
    }
  }
} /* Output:
Sorted array: [128, 140, 200, 207, 258, 258, 278, 288, 322, 429, 511, 520, 522, 551, 555, 589, 693, 704, 809, 861, 861, 868, 916, 961, 998]
Location of 322 is 8, a[8] = 322
*///:~
/**
* @Description: 在while循环中随机生成一些作为查找的对象，直到找到了一个才停止循环。
 *
 * 如果找到了目标，Arrays.binarySearch()产生的返回值等于或大于0.
 * 否则，他产生负返回值，表示若要保持数组的排序状态此目标元素所应该插入的位置。这个负值的计算方式是：
 *      -(插入点) - 1
 * “插入点”是指，第一个大于查找对象的元素在数组中的位置，如果数组中所有的元素都小于要查找的对象，“插入点”就等于a.size()
 *
 * 如果数组包含重复的元素，则无法保证找到的是这些副本中的哪一个。搜索算法确实不是专为包含重复元素的数组而设计的 ，不过仍然可用。
 * 如果需要对没有重复元素的数组排序，可以使用TreeSet(保持排序顺序)，或者LinkedHashSet(保持插入顺序)，后面我们将会介绍他们。
 * 这些类会自动处理所有的细节。除非他们称为程序性能的瓶颈，否则不需要自己维护数组。
 *
 * 如果使用COmparator排序了某个对象数组(基本类型数组无法使用Comparator进行排序)，在使用binarySearch()时必须提供同样的Comparator(使用binarySearch方法
 * 的重载版本)。例如，可以修改StringSorting.java程序以进行某种查找：
*/
class AlphabeticSearch {
  public static void main(String[] args) {
    String[] sa = Generated.array(new String[30],
            new RandomGenerator.String(5));
    Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER);
    System.out.println(Arrays.toString(sa));
    int index = Arrays.binarySearch(sa, sa[10],
            String.CASE_INSENSITIVE_ORDER);
    System.out.println("Index: "+ index + "\n"+ sa[index]);
  }
} /* Output:
[bkIna, cQrGs, cXZJo, dLsmw, eGZMm, EqUCB, gwsqP, hKcxr, HLGEa, HqXum, HxxHv, JMRoE, JmzMs, Mesbt, MNvqe, nyGcF, ogoYW, OneOE, OWZnT, RFJQA, rUkZP, sgqia, slJrL, suEcU, uTpnX, vpfFv, WHkjU, xxEAJ, YNzbr, zDyCy]
Index: 10
HxxHv
*///:~
/**
* @Description: 这里的COmparator必须接受重载过的binarySearch方法作为其第三个参数。在这里例子中，由于要查找的目标是从数组中选出来的元素，所以肯定能查到
*/
















