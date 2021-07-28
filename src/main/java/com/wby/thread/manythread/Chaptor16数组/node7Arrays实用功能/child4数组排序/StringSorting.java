package com.wby.thread.manythread.Chaptor16数组.node7Arrays实用功能.child4数组排序;//: arrays/StringSorting.java
// Sorting an array of Strings.


import com.wby.thread.manythread.net.mindview.util.Generated;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.Arrays;
import java.util.Collections;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description:  使用内置的排序方法，就可以对任意的基本类型数组排序；也可以对任意的对象数组进行排序，只要该对象实现了Comparable接口或具有相关联
 * 的Comparator。下面的例子生成随机的String对象，并对其排序：
*/
public class StringSorting {
  public static void main(String[] args) {
    String[] sa = Generated.array(new String[20],
      new RandomGenerator.String(5));
    print("Before sort: " + Arrays.toString(sa));
    Arrays.sort(sa);
    print("After sort: " + Arrays.toString(sa));
    Arrays.sort(sa, Collections.reverseOrder());
    print("Reverse sort: " + Arrays.toString(sa));
    Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER);
    print("Case-insensitive sort: " + Arrays.toString(sa));
  }
} /* Output:
Before sort: [YNzbr, nyGcF, OWZnT, cQrGs, eGZMm, JMRoE, suEcU, OneOE, dLsmw, HLGEa, hKcxr, EqUCB, bkIna, Mesbt, WHkjU, rUkZP, gwsqP, zDyCy, RFJQA, HxxHv]
After sort: [EqUCB, HLGEa, HxxHv, JMRoE, Mesbt, OWZnT, OneOE, RFJQA, WHkjU, YNzbr, bkIna, cQrGs, dLsmw, eGZMm, gwsqP, hKcxr, nyGcF, rUkZP, suEcU, zDyCy]
Reverse sort: [zDyCy, suEcU, rUkZP, nyGcF, hKcxr, gwsqP, eGZMm, dLsmw, cQrGs, bkIna, YNzbr, WHkjU, RFJQA, OneOE, OWZnT, Mesbt, JMRoE, HxxHv, HLGEa, EqUCB]
Case-insensitive sort: [bkIna, cQrGs, dLsmw, eGZMm, EqUCB, gwsqP, hKcxr, HLGEa, HxxHv, JMRoE, Mesbt, nyGcF, OneOE, OWZnT, RFJQA, rUkZP, suEcU, WHkjU, YNzbr, zDyCy]
*///:~
/**
* @Description: 注意，String排序算法依据 词典编排顺序 排序，所以大写字母开头的词都放在前面输出，然后才是小写字母开头的词。(电话簿通常是这样排序的)
 * 如果想忽略大小写字母将单词都放在一起排序，那么可以像上例中最后一个对sort方法的调用一样，使用String。CASE_INSENSITIVE_ORDER。
 *
 * java类库中的排序算法针对正排序的特殊类型进行了优化---针对基本类型设计的“快速排序”，以及针对对象设计的“稳定归并排序”。所以无需担心排序的性能，除非
 * 你可以证明排序部分的确是程序效率的瓶颈。
*/
