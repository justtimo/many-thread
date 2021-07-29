package com.wby.thread.manythread.Chaptor17容器深入研究.node8理解Map.child1性能;//: containers/Maps.java
// Things you can do with Maps.

import com.wby.thread.manythread.net.mindview.util.CountingMapData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
* @Description: 性能是映射表中的一个重要问题，当在get（O）中使用线性搜索时，执行速度会相当地慢，而这正是HashMap提高速度的地方。
 * HashMap使用了特殊的值，称作散列码，来取代对键的缓慢搜索。
 * 散列码是"相对唯一"的、用以代表对象的int值，它是通过将该对象的某些信息进行转换而生成的。hashCode（）是根类Object中的方法，因此所有Java对象都能产生散列码。
 * HashMap就是使用对象的hashCode（）进行快速查询的，此方法能够显著提高性能。
 *
 * 如果这仍不能满足你对性能的要求，那么你还可以通过创建自己的Map来进一步提高查询速度，并且令新的Map只针对你使用的特定类型，这样可以避免与Object之间的类型转换操作。
 * 要到达更高的性能，速度狂们可以参考Donald Knuth的The Art of Computer Programming，Volume 3∶ Sorting and Searching，Second Edition。
 * 使用数组代替溢出桶，这有两个好处∶ 第一，可以针对磁盘存储方式做优化;第二，在创建和回收单独的记录时，能节约很多时间。
 *
 * 下面是基本的Map实现。在HashMap上打星号表示如果没有其他的限制，它就应该成为你的默认选择，因为它对速度进行了优化。其他实现强调了其他的特性，因此都不如HashMap快。
 * HashMap*
 *    Map基于散列表的实现（它取代了Hashtable）。插入和查询"键值对"的开销是固定的。可以通过构造器设置容量和负载因子，以调整容器的性能
 * LinkedHashMap
 *    类似于HashMap，但是迭代遍历它时，取得"键值对"的顺序是其插入次序，或者是最近最少使用（LRU）的次序。只比HashMap慢一点;而在迭代访问时反而更快，因为它使用链表维护内部次序
 * TeeMap
 *    基于红黑树的实现。查看"键"或"键值对"时，它们会被排序（次序由Comparable或Comparator决定）。TreeMap的特点在于，所得到的结果是经过排序的。
 *    TreeMap是唯一的带有subMapO方法的Map，它可以返回一个子树~
 * WeakHasth Map
 *    弱键（weak key）映射，允许释放映射所指向的对象;这是为解决某类特殊问题而设计的。如果映射之外没有引用指向某个"键"，则此"键"可以被垃圾收集器回收
 * ConcurrentHashMap
 *    一种线程安全的Map，它不涉及同步加锁。我们将在"并发"一章中讨论
 * IdentityHashMap
 *    使用==代替equals（）对"键"进行比较的散列映射。专为解决特殊问题而设计的
 *
 * 散列是映射中存储元素时最常用的方式。稍后你将会了解散列机制是如何工作的。
 *
 * 对Map中使用的键的要求与对Set中的元素的要求一样，在TypesForSets.java中展示了这一点。任何键都必须具有一个equalsO方法;如果键被用于散列Map，那么它必须
 * 还具有恰当的 hashCodeO方法;如果键被用于TreeMap，那么它必须实现Comparable。
 *
 * 下面的示例展示了通过Map接口可用的操作，这里使用了前面定义过的CountingMapData测试数据集∶
*/
public class Maps {
  public static void printKeys(Map<Integer,String> map) {
    printnb("Size = " + map.size() + ", ");
    printnb("Keys: ");
    print(map.keySet()); // Produce a Set of the keys
  }
  public static void test(Map<Integer,String> map) {
    print(map.getClass().getSimpleName());
    map.putAll(new CountingMapData(25));
    // Map has 'Set' behavior for keys:
    map.putAll(new CountingMapData(25));
    printKeys(map);
    // Producing a Collection of the values:
    printnb("Values: ");
    print(map.values());
    print(map);
    print("map.containsKey(11): " + map.containsKey(11));
    print("map.get(11): " + map.get(11));
    print("map.containsValue(\"F0\"): "
      + map.containsValue("F0"));
    Integer key = map.keySet().iterator().next();
    print("First key in map: " + key);
    map.remove(key);
    printKeys(map);
    map.clear();
    print("map.isEmpty(): " + map.isEmpty());
    map.putAll(new CountingMapData(25));
    // Operations on the Set change the Map:
    map.keySet().removeAll(map.keySet());
    print("map.isEmpty(): " + map.isEmpty());
  }
  public static void main(String[] args) {
    test(new HashMap<Integer,String>());
    test(new TreeMap<Integer,String>());
    test(new LinkedHashMap<Integer,String>());
    test(new IdentityHashMap<Integer,String>());
    test(new ConcurrentHashMap<Integer,String>());
    test(new WeakHashMap<Integer,String>());
  }
} /* Output:
HashMap
Size = 25, Keys: [15, 8, 23, 16, 7, 22, 9, 21, 6, 1, 14, 24, 4, 19, 11, 18, 3, 12, 17, 2, 13, 20, 10, 5, 0]
Values: [P0, I0, X0, Q0, H0, W0, J0, V0, G0, B0, O0, Y0, E0, T0, L0, S0, D0, M0, R0, C0, N0, U0, K0, F0, A0]
{15=P0, 8=I0, 23=X0, 16=Q0, 7=H0, 22=W0, 9=J0, 21=V0, 6=G0, 1=B0, 14=O0, 24=Y0, 4=E0, 19=T0, 11=L0, 18=S0, 3=D0, 12=M0, 17=R0, 2=C0, 13=N0, 20=U0, 10=K0, 5=F0, 0=A0}
map.containsKey(11): true
map.get(11): L0
map.containsValue("F0"): true
First key in map: 15
Size = 24, Keys: [8, 23, 16, 7, 22, 9, 21, 6, 1, 14, 24, 4, 19, 11, 18, 3, 12, 17, 2, 13, 20, 10, 5, 0]
map.isEmpty(): true
map.isEmpty(): true
...
*///:~
/**
* @Description:  printKeysO展示了如何生成Map的Collection视图。keySetO方法返回由Map的键组成的Set。因为在Java SE5提供了改进的打印支持，你可以直接打印valuesO方法的结果，
 * 该方法会产生一个包含Map中所有"值"的Collection。（注意，键必须是唯一的，而值可以有重复。）
 *
 * 由于这些 Collection背后是由Map支持的，所以对Collection的任何改动都会反映到与之相关联的Map。此程序的剩余部分提供了每种Map操作的简单示例，并测试了每种基本类型的Map。
*/
