package com.wby.thread.manythread.Chaptor17容器深入研究.node3Collection的功能方法;//: containers/CollectionMethods.java
// Things you can do with all Collections.


import com.wby.thread.manythread.net.mindview.util.Countries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 下面的列表列出了可以通过COllection执行的所有操作(不包括从Object继承而来的方法)。因此，他们也是可通过Set或
 * List执行的所有操作(List还有额外的功能)。Map不是继承自Collection的，所以会另行介绍。
 *
 * 请注意，其中不包括随机访问所选择元素的get方法，因为Collection包括Set，而Set是自己维护内部顺序的(这使得随机访问变得没有意义)。
 * 因此，如果想检查Collection中的元素，那就必须使用迭代器。
 *
 * 下面的例子展示了所有这些方法。虽然任何实现了Collection的类都可以使用这些方法，但例子中使用ArrayList，以说明各种Collection子类的“最基本的共同特性”
*/
public class CollectionMethods {
  public static void main(String[] args) {
    Collection<String> c = new ArrayList<String>();
    c.addAll(Countries.names(6));
    c.add("ten");
    c.add("eleven");
    print(c);
    // Make an array from the List:
    Object[] array = c.toArray();
    // Make a String array from the List:
    String[] str = c.toArray(new String[0]);
    // Find max and min elements; this means
    // different things depending on the way
    // the Comparable interface is implemented:
    print("Collections.max(c) = " + Collections.max(c));
    print("Collections.min(c) = " + Collections.min(c));
    // Add a Collection to another Collection
    Collection<String> c2 = new ArrayList<String>();
    c2.addAll(Countries.names(6));
    c.addAll(c2);
    print(c);
    c.remove(Countries.DATA[0][0]);
    print(c);
    c.remove(Countries.DATA[1][0]);
    print(c);
    // Remove all components that are
    // in the argument collection:
    c.removeAll(c2);
    print(c);
    c.addAll(c2);
    print(c);
    // Is an element in this Collection?
    String val = Countries.DATA[3][0];
    print("c.contains(" + val  + ") = " + c.contains(val));
    // Is a Collection in this Collection?
    print("c.containsAll(c2) = " + c.containsAll(c2));
    Collection<String> c3 =
      ((List<String>)c).subList(3, 5);
    // Keep all the elements that are in both
    // c2 and c3 (an intersection of sets):
    c2.retainAll(c3);
    print(c2);
    // Throw away all the elements
    // in c2 that also appear in c3:
    c2.removeAll(c3);
    print("c2.isEmpty() = " +  c2.isEmpty());
    c = new ArrayList<String>();
    c.addAll(Countries.names(6));
    print(c);
    c.clear(); // Remove all elements
    print("after c.clear():" + c);
  }
} /* Output:
[ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO, ten, eleven]
Collections.max(c) = ten
Collections.min(c) = ALGERIA
[ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO, ten, eleven, ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
[ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO, ten, eleven, ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
[BENIN, BOTSWANA, BULGARIA, BURKINA FASO, ten, eleven, ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
[ten, eleven]
[ten, eleven, ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
c.contains(BOTSWANA) = true
c.containsAll(c2) = true
[ANGOLA, BENIN]
c2.isEmpty() = true
[ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
after c.clear():[]
*///:~
/**
* @Description: 创建ArrayList来保存不同的数据集，然后向上转型为Collection，所以很明显，代码只用到了Collection接口。
 * main方法用简单的练习展示了Collection中的所有方法。
 *
 * 本章后面将介绍List、Set和Map的各种实现，每种情况都会(以星号)标出默认的选择。对遗留类Vector、Stack和Hashtable的描述放到了本章最后，
 *
*/
