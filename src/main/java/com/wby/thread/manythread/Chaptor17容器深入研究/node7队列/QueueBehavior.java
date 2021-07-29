package com.wby.thread.manythread.Chaptor17容器深入研究.node7队列;//: containers/QueueBehavior.java
// Compares the behavior of some of the queues


import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
* @Description:  除了并发应用，Queue在Java SE5中仅有的两个实现是LinkedList和PriorityQueue，它们的差异在于排序行为而不是性能。
 * 下面是涉及Queue实现的大部分操作的基本示例（并非所有的操作在本例中都能工作），包括基于并发的Queue。你可以将元素从队列的一端插入，并于另一端将它们抽取出来∶
*/
public class QueueBehavior {
  private static int count = 10;
  static <T> void test(Queue<T> queue, Generator<T> gen) {
    for(int i = 0; i < count; i++)
      queue.offer(gen.next());
    while(queue.peek() != null)
      System.out.print(queue.remove() + " ");
    System.out.println();
  }
  static class Gen implements Generator<String> {
    String[] s = ("one two three four five six seven " +
      "eight nine ten").split(" ");
    int i;
    public String next() { return s[i++]; }
  }
  public static void main(String[] args) {
    test(new LinkedList<String>(), new Gen());
    test(new PriorityQueue<String>(), new Gen());
    test(new ArrayBlockingQueue<String>(count), new Gen());
    test(new ConcurrentLinkedQueue<String>(), new Gen());
    test(new LinkedBlockingQueue<String>(), new Gen());
    test(new PriorityBlockingQueue<String>(), new Gen());
  }
} /* Output:
one two three four five six seven eight nine ten
eight five four nine one seven six ten three two
one two three four five six seven eight nine ten
one two three four five six seven eight nine ten
one two three four five six seven eight nine ten
eight five four nine one seven six ten three two
*///:~
/**
* @Description:  可以看到，除了优先级队列，QUEUE将精确地按照元素被置于Queue中的顺序产生他们
*/
