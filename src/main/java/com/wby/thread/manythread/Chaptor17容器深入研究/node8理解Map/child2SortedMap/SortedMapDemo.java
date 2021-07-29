package com.wby.thread.manythread.Chaptor17容器深入研究.node8理解Map.child2SortedMap;//: containers/SortedMapDemo.java
// What you can do with a TreeMap.


import com.wby.thread.manythread.net.mindview.util.CountingMapData;

import java.util.Iterator;
import java.util.TreeMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 使用SortedMap（TreeMap是其现阶段的唯一实现），可以确保键处于排序状态。这使得它具有额外的功能，这些功能由SortedMap接口中的下列方法提供∶
 * Comparator comparatorO∶返回当前Map使用的Comparator;或者返回null，表示以自然方式排序。
 * T firstKey（）返回Map中的第一个键。T lastKey（）返回Map中的最末一个键。
 * SortedMap subMap（fromKey， toKey）生成此Map的子集，范围由fromKey（包含）到toKey（不包含）的键确定。
 * SortedMap headMap（toKey）生成此Map的子集，由键小于toKey的所有键值对组成。
 * SortedMap tailMap（fromKey）生成此Map的子集，由键大于或等于fromKey的所有键值对组成。
 *
 * 下面的例子与SortedSetDemo，java相似，演示了TreeMap新增的功能∶
*/
public class SortedMapDemo {
  public static void main(String[] args) {
    TreeMap<Integer,String> sortedMap =
      new TreeMap<Integer,String>(new CountingMapData(10));
    print(sortedMap);
    Integer low = sortedMap.firstKey();
    Integer high = sortedMap.lastKey();
    print(low);
    print(high);
    Iterator<Integer> it = sortedMap.keySet().iterator();
    for(int i = 0; i <= 6; i++) {
      if(i == 3) low = it.next();
      if(i == 6) high = it.next();
      else it.next();
    }
    print(low);
    print(high);
    print(sortedMap.subMap(low, high));
    print(sortedMap.headMap(high));
    print(sortedMap.tailMap(low));
  }
} /* Output:
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0, 9=J0}
0
9
3
7
{3=D0, 4=E0, 5=F0, 6=G0}
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0}
{3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0, 9=J0}
*///:~
/**
* @Description:  此处，键值对是按键的次序排列的。TreeMap中的次序是有意义的，因此"位置"的概念才有意义，所以才能取得第一个和最后一个元素，并且可以提取Map的子集。
*/
