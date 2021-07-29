package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child5对Map的选择;//: containers/MapPerformance.java
// Demonstrates performance differences in Maps.
// {Args: 100 5000} Small to keep build testing short

import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.Test;
import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.TestParam;
import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.Tester;

import java.util.*;

/**
* @Description: 下面的程序对于Map的不同实现，在性能开销方面给出了指示∶
*/
public class MapPerformance {
  static List<Test<Map<Integer,Integer>>> tests =
    new ArrayList<Test<Map<Integer,Integer>>>();
  static {
    tests.add(new Test<Map<Integer,Integer>>("put") {
      public int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          map.clear();
          for(int j = 0; j < size; j++)
            map.put(j, j);
        }
        return loops * size;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("get") {
      public int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int span = tp.size * 2;
        for(int i = 0; i < loops; i++)
          for(int j = 0; j < span; j++)
            map.get(j);
        return loops * span;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("iterate") {
      public int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops * 10;
        for(int i = 0; i < loops; i ++) {
          Iterator it = map.entrySet().iterator();
          while(it.hasNext())
            it.next();
        }
        return loops * map.size();
      }
    });
  }
  public static void main(String[] args) {
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    Tester.run(new TreeMap<Integer,Integer>(), tests);
    Tester.run(new HashMap<Integer,Integer>(), tests);
    Tester.run(new LinkedHashMap<Integer,Integer>(),tests);
    Tester.run(
      new IdentityHashMap<Integer,Integer>(), tests);
    Tester.run(new WeakHashMap<Integer,Integer>(), tests);
    Tester.run(new Hashtable<Integer,Integer>(), tests);
  }
} /* Output: (Sample)
---------- TreeMap ----------
 size     put     get iterate
   10     748     168     100
  100     506     264      76
 1000     771     450      78
10000    2962     561      83
---------- HashMap ----------
 size     put     get iterate
   10     281      76      93
  100     179      70      73
 1000     267     102      72
10000    1305     265      97
------- LinkedHashMap -------
 size     put     get iterate
   10     354     100      72
  100     273      89      50
 1000     385     222      56
10000    2787     341      56
------ IdentityHashMap ------
 size     put     get iterate
   10     290     144     101
  100     204     287     132
 1000     508     336      77
10000     767     266      56
-------- WeakHashMap --------
 size     put     get iterate
   10     484     146     151
  100     292     126     117
 1000     411     136     152
10000    2165     138     555
--------- Hashtable ---------
 size     put     get iterate
   10     264     113     113
  100     181     105      76
 1000     260     201      80
10000    1245     134      77
*///:~
/**
* @Description: 除了IdentityHashMap，所有的Map实现的插入操作都会随着Map尺寸的变大而明显变慢。但是，查找的代价通常比插入要小得多，这是个好消息，因为我们执行查找元素的
 * 操作要比执行插入元素的操作多得多。
 *
 * Hashtable的性能大体上与HashMap相当。因为HashMap是用来替代Hashtable的，因此它们使用了相同的底层存储和查找机制（你稍后就会学习它），这并没有什么令人吃惊的。
 *
 * TreeMap通常比HashMap要慢。与使用TreeSet一样，TreeMap是一种创建有序列表的方式。树的行为是∶总是保证有序，并且不必进行特殊的排序。一旦你填充了一个TreeMap，
 * 就可以调用keySet（）方法来获取键的Set视图，然后调用toArrayO来产生由这些键构成的数组。之后，你可以使用静态方法Arrays.binarySearch（）在排序数组中快速查找对象。
 * 当然，这只有在 HashMap的行为不可接受的情况下才有意义，因为HashMap本身就被设计为可以快速查找键。，你还可以很方便地通过单个的对象创建操作，或者是调用putAllO，
 * 从TreeMap中创建HashMap。最后，当使用Map时，你的第一选择应该是HashMap，只有在你要求Map始终保持有序时，才需要使用TreeMap。
 *
 * LinkedHashMap在插入时比HashMap慢一点，因为它维护散列数据结构的同时还要维护链表（以保持插入顺序）。正是由于这个列表，使得其迭代速度更快。
 *
 * IdentityHashMap则具有完全不同的性能，因为它使用==而不是equals（）来比较元素。 WeakHashMap将在本章稍后介绍。
 *
 *
 * HashMap的性能因子
 * 我们可以通过手工调整HashMap来提高其性能，从而满足我们特定应用的需求。为了在调整HashMap时让你理解性能问题，某些术语是必需了解的∶
 * ●容量;表中的桶位数。
 * ·初始容量;表在创建时所拥有的桶位数。HashMap和HashSet都具有允许你指定初始容量的构造器。
 * ●尺寸;表中当前存储的项数。
 * ·负载因子。尺寸/容量。空表的负载因子是0，而半满表的负载因子是0.5，依此类推。负载轻的表产生冲突的可能性小，因此对于插入和查找都是最理想的（但是会减慢使用迭代器进行遍历的过程）。HashMap和HashSet都具有允许你指定负载因子的构造器，表示当负载情况达到该负载因子的水平时，容器将自动增加其容量（桶位数），实现方式是使容量大致加倍，并重新将现有对象分布到新的桶位集中（这被称为再散列）。
 * HashMap使用的默认负载因子是0.75 （只有当表达到四分之三满时，才进行再散列），这个因子在时间和空间代价之间达到了平衡。更高的负载因子可以降低表所需的空间，但是会增加
 * [878
 * 查找代价，这很重要，因为查找是我们在大多数时间里所做的操作（包括get（）和putO）。
 * 如果你知道将要在HashMap中存储多少项，那么创建一个具有恰当大小的初始容量将可以避免自动再散列的开销。
*/
