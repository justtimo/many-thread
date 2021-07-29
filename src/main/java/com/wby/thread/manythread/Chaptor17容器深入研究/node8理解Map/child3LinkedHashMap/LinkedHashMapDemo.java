package com.wby.thread.manythread.Chaptor17容器深入研究.node8理解Map.child3LinkedHashMap;//: containers/LinkedHashMapDemo.java
// What you can do with a LinkedHashMap.


import com.wby.thread.manythread.net.mindview.util.CountingMapData;

import java.util.LinkedHashMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description:  为了提高速度，LinkedHashMap散列化所有的元素，但是在遍历键值对时，却又以元素的插入顺序返回键值对（System.out.printlnO会迭代遍历该映射，因此可以看到遍历的结果）。
 * 此外，可以在构造器中设定LinkedHashMap，使之采用基于访问的最近最少使用（LRU）算法，于是没有被访问过的（可被看作需要删除的）元素就会出现在队列的前面。
 *
 * 对于需要定期清理元素以节省空间的程序来说，此功能使得程序很容易得以实现。下面就是一个简单的例子，它演示了LinkedHashMap的这两种特点∶
*/
public class LinkedHashMapDemo {
  public static void main(String[] args) {
    LinkedHashMap<Integer,String> linkedMap =
      new LinkedHashMap<Integer,String>(
        new CountingMapData(9));
    print(linkedMap);
    // Least-recently-used order:
    linkedMap =
      new LinkedHashMap<Integer,String>(16, 0.75f, true);
    linkedMap.putAll(new CountingMapData(9));
    print(linkedMap);
    for(int i = 0; i < 6; i++) // Cause accesses:
      linkedMap.get(i);
    print(linkedMap);
    linkedMap.get(0);
    print(linkedMap);
  }
} /* Output:
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{6=G0, 7=H0, 8=I0, 0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0}
{6=G0, 7=H0, 8=I0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 0=A0}
*///:~
/**
* @Description:  在输出中可以看到，键值对是以插入的顺序进行遍历的，甚至LRU算法的版本也是如此。但是，在LRU版本中，在（只）访问过前面六个元素后，最后三个元素移到了队列前面。
 * 然后再一次访问元素"o"时，它就被移到队列后端了。
*/
