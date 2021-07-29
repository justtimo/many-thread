package com.wby.thread.manythread.Chaptor17容器深入研究.node11实用方法.child3Collection或Map的同步控制;//: containers/Synchronization.java
// Using the Collections.synchronized methods.
import java.util.*;

/**
* @Description:  关键字synchronized是多 线程议题中的重要部分，第21章将讨论这种较为复杂的主题。这里，我只提醒读者注意，Collections类有办法能够自动同步整个容器。
 * 其语法与"不可修改的"方法相似∶
*/
public class Synchronization {
  public static void main(String[] args) {
    Collection<String> c =
      Collections.synchronizedCollection(
        new ArrayList<String>());
    List<String> list = Collections.synchronizedList(
      new ArrayList<String>());
    Set<String> s = Collections.synchronizedSet(
      new HashSet<String>());
    Set<String> ss = Collections.synchronizedSortedSet(
      new TreeSet<String>());
    Map<String,String> m = Collections.synchronizedMap(
      new HashMap<String,String>());
    Map<String,String> sm =
      Collections.synchronizedSortedMap(
        new TreeMap<String,String>());
  }
} ///:~
/**
* @Description: 做就不会有任何机会暴露出不同步的版本。
 *
 * 快速报错
 * Java 容器有一种保护机制，能够防止多个进程同时修改同一个容器的内容。如果在你迭代遍历某个容器的过程中，另一个进程介入其中，并且插入、删除或修改此容器内的某个对象，
 * 那么就会出现问题∶也许迭代过程已经处理过容器中的该元素了，也许还没处理，也许在调用 sizeO）之后容器的尺寸收缩了——还有许多灾难情景。Java容器类类库采用快速报错（fail-fast）机制。
 * 它会探查容器上的任何除了你的进程所进行的操作以外的所有变化，一旦它发现其他进程修改了容器，就会立刻抛出ConcurrentModificationException异常。这就是"快速报错" 的意思———即，不是使用复杂的算法在事后来检查问题。
 *
 * 很容易就可以看出"快速报错"机制的工作原理;只需创建一个迭代器，然后向迭代器所指向的Collection添加点什么，就像这样∶
*/
class FailFast {
  public static void main(String[] args) {
    Collection<String> c = new ArrayList<String>();
    Iterator<String> it = c.iterator();
    c.add("An object");
    try {
      String s = it.next();
    } catch(ConcurrentModificationException e) {
      System.out.println(e);
    }
  }
} /* Output:
java.util.ConcurrentModificationException
*///:~
/**
* @Description: 程序运行时发生了异常，因为在容器取得迭代器之后，又有东西被放入到了该容器中。当程序的不同部分修改同一个容器时，就可能导致容器的状态不一致，
 * 所以，此异常提醒你，应该修改代码。在此例中，应该在添加完所有的元素之后，再获取迭代器。
 *
 * ConcurrentHashMap、CopyOnWriteArrayList和CopyOnWriteArraySet都使用了可以避免ConcurrentModificationException的技术
*/
