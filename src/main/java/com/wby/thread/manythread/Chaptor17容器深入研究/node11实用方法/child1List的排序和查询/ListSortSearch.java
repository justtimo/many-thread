package com.wby.thread.manythread.Chaptor17容器深入研究.node11实用方法.child1List的排序和查询;//: containers/ListSortSearch.java
// Sorting and searching Lists with Collections utilities.

import com.wby.thread.manythread.Chaptor17容器深入研究.node11实用方法.Utilities;

import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: List排序与查询所使用的方法与对象数组所使用的相应方法有相同的名字与语法，只是用 Collections的static方法代替Arrays的方法而已。
 * 下面是一个例子，用到了Utilities.java中的lis数据∶
*/
public class ListSortSearch {
  public static void main(String[] args) {
    List<String> list =
      new ArrayList<String>(Utilities.list);
    list.addAll(Utilities.list);
    print(list);
    Collections.shuffle(list, new Random(47));
    print("Shuffled: " + list);
    // Use a ListIterator to trim off the last elements:
    ListIterator<String> it = list.listIterator(10);
    while(it.hasNext()) {
      it.next();
      it.remove();
    }
    print("Trimmed: " + list);
    Collections.sort(list);
    print("Sorted: " + list);
    String key = list.get(7);
    int index = Collections.binarySearch(list, key);
    print("Location of " + key + " is " + index +
      ", list.get(" + index + ") = " + list.get(index));
    Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
    print("Case-insensitive sorted: " + list);
    key = list.get(7);
    index = Collections.binarySearch(list, key,
      String.CASE_INSENSITIVE_ORDER);
    print("Location of " + key + " is " + index +
      ", list.get(" + index + ") = " + list.get(index));
  }
} /* Output:
[one, Two, three, Four, five, six, one, one, Two, three, Four, five, six, one]
Shuffled: [Four, five, one, one, Two, six, six, three, three, five, Four, Two, one, one]
Trimmed: [Four, five, one, one, Two, six, six, three, three, five]
Sorted: [Four, Two, five, five, one, one, six, six, three, three]
Location of six is 7, list.get(7) = six
Case-insensitive sorted: [five, five, Four, one, one, six, six, three, three, Two]
Location of three is 7, list.get(7) = three
*///:~
/**
* @Description: 与使用数组进行查找和排序一样，如果使用Comparator进行排序，那么binarySearchO必须使用相同的Comparator。
 *
 * 此程序还演示了Collections的shufleO方法，它用来打乱List的顺序。ListIterator是在被打乱的列表中的某个特定位置创建的，并用来移除从该位置到列表尾部的所有元素。
*/
