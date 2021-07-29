//: net/mindview/util/Deque.java
// Creating a Deque from a LinkedList.
package com.wby.thread.manythread.Chaptor17容器深入研究.node7队列.child2双向队列;
import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
* @Description:  双向队列（双端队列）就像是一个队列，但是你可以在任何一端添加或移除元素。在 LinkedList中包含支持双向队列的方法，但是在Java标准类库
 * 中没有任何显式的用于双向队列的接口。因此，LinkedList无法去实现这样的接口，你也无法像在前面的示例中转型到Queue那样去向上转型到Deque。
 * 但是，你可以使用组合来创建一个Deque类，并直接从LinkedList中暴露相关的方法∶
*/
public class Deque<T> {
  private LinkedList<T> deque = new LinkedList<T>();
  public void addFirst(T e) { deque.addFirst(e); }
  public void addLast(T e) { deque.addLast(e); }
  public T getFirst() { return deque.getFirst(); }
  public T getLast() { return deque.getLast(); }
  public T removeFirst() { return deque.removeFirst(); }
  public T removeLast() { return deque.removeLast(); }
  public int size() { return deque.size(); }
  public String toString() { return deque.toString(); }
  // And other methods as necessary...
} ///:~
/**
* @Description: 如果将这个Deque用于自己的程序中，你可能会发现，为了使它实用，还需要增加其他方法。下面是对Deque类的简单测试：
*/
class DequeTest {
  static void fillTest(Deque<Integer> deque) {
    for(int i = 20; i < 27; i++)
      deque.addFirst(i);
    for(int i = 50; i < 55; i++)
      deque.addLast(i);
  }
  public static void main(String[] args) {
    Deque<Integer> di = new Deque<Integer>();
    fillTest(di);
    print(di);
    while(di.size() != 0)
      printnb(di.removeFirst() + " ");
    print();
    fillTest(di);
    while(di.size() != 0)
      printnb(di.removeLast() + " ");
  }
} /* Output:
[26, 25, 24, 23, 22, 21, 20, 50, 51, 52, 53, 54]
26 25 24 23 22 21 20 50 51 52 53 54
54 53 52 51 50 20 21 22 23 24 25 26
*///:~
/**
* @Description:  你不太可能在两端都放入元素并抽取他们，因此，Deque不如Queue那样常用。
*/
