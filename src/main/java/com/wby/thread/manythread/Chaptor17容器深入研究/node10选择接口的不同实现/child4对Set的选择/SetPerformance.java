package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child4对Set的选择;//: containers/SetPerformance.java
// Demonstrates performance differences in Sets.
// {Args: 100 5000} Small to keep build testing short

import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.Test;
import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.TestParam;
import com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架.Tester;

import java.util.*;

/**
* @Description: 可以根据需要选择TreeSet、HashSet或者LinkedHashSet。下面的测试说明了它们的性能表现，据此可在各种实现间做出权衡选择∶
*/
public class SetPerformance {
  static List<Test<Set<Integer>>> tests =
    new ArrayList<Test<Set<Integer>>>();
  static {
    tests.add(new Test<Set<Integer>>("add") {
      public int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          set.clear();
          for(int j = 0; j < size; j++)
            set.add(j);
        }
        return loops * size;
      }
    });
    tests.add(new Test<Set<Integer>>("contains") {
      public int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops;
        int span = tp.size * 2;
        for(int i = 0; i < loops; i++)
          for(int j = 0; j < span; j++)
            set.contains(j);
        return loops * span;
      }
    });
    tests.add(new Test<Set<Integer>>("iterate") {
      public int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops * 10;
        for(int i = 0; i < loops; i++) {
          Iterator<Integer> it = set.iterator();
          while(it.hasNext())
            it.next();
        }
        return loops * set.size();
      }
    });
  }
  public static void main(String[] args) {
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    Tester.fieldWidth = 10;
    Tester.run(new TreeSet<Integer>(), tests);
    Tester.run(new HashSet<Integer>(), tests);
    Tester.run(new LinkedHashSet<Integer>(), tests);
  }
} /* Output: (Sample)
------------- TreeSet -------------
 size       add  contains   iterate
   10       746       173        89
  100       501       264        68
 1000       714       410        69
10000      1975       552        69
------------- HashSet -------------
 size       add  contains   iterate
   10       308        91        94
  100       178        75        73
 1000       216       110        72
10000       711       215       100
---------- LinkedHashSet ----------
 size       add  contains   iterate
   10       350        65        83
  100       270        74        55
 1000       303       111        54
10000      1615       256        58
*///:~
/**
* @Description: HashSet的性能基本上总是比TreeSet好，特别是在添加和查询元素时，而这两个操作也是最重要的操作。TreeSet存在的唯一原因是它可以维持元素的排序状态;
 * 所以，只有当需要—个排好序的Set时，才应该使用TreeSet。因为其内部结构支持排序，并且因为迭代是我们更有可能执行的操作，所以，用TreeSet迭代通常比用HashSet要快。
 *
 * 注意，对于插入操作，LinkedHashSet比HashSet的代价更高;这是由维护链表所带来额外开销造成的。
*/
