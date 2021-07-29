package com.wby.thread.manythread.Chaptor17容器深入研究.node11实用方法.child2设定Collection或Map不可修改;//: containers/ReadOnly.java
// Using the Collections.unmodifiable methods.

import com.wby.thread.manythread.net.mindview.util.Countries;

import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 创建一个只读的Collection或Map，有时可以带来某些方便。Collections类可以帮助达成此目的，它有一个方法，参数为原本的容器，返回值是容器的只读版本。
 * 此方法有大量变种，对应于Collection（如果你不能把Collection视为更具体的类型）、List、Set和Map。下例将说明如何正确生成各种只读容器∶
*/
public class ReadOnly {
  static Collection<String> data =
    new ArrayList<String>(Countries.names(6));
  public static void main(String[] args) {
    Collection<String> c =
      Collections.unmodifiableCollection(
        new ArrayList<String>(data));
    print(c); // Reading is OK
    //! c.add("one"); // Can't change it

    List<String> a = Collections.unmodifiableList(
        new ArrayList<String>(data));
    ListIterator<String> lit = a.listIterator();
    print(lit.next()); // Reading is OK
    //! lit.add("one"); // Can't change it

    Set<String> s = Collections.unmodifiableSet(
      new HashSet<String>(data));
    print(s); // Reading is OK
    //! s.add("one"); // Can't change it

    // For a SortedSet:
    Set<String> ss = Collections.unmodifiableSortedSet(
      new TreeSet<String>(data));

    Map<String,String> m = Collections.unmodifiableMap(
      new HashMap<String,String>(Countries.capitals(6)));
    print(m); // Reading is OK
    //! m.put("Ralph", "Howdy!");

    // For a SortedMap:
    Map<String,String> sm =
      Collections.unmodifiableSortedMap(
        new TreeMap<String,String>(Countries.capitals(6)));
  }
} /* Output:
[ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO]
ALGERIA
[BULGARIA, BURKINA FASO, BOTSWANA, BENIN, ANGOLA, ALGERIA]
{BULGARIA=Sofia, BURKINA FASO=Ouagadougou, BOTSWANA=Gaberone, BENIN=Porto-Novo, ANGOLA=Luanda, ALGERIA=Algiers}
*///:~
/**
* @Description: 对特定类型的"不可修改的"方法的调用并不会产生编译时的检查，但是转换完成后，任 .何会改变容器内容的操作都会引起UnsupportedOperationException异常。
 *
 * 无论哪一种情况，在将容器设为只读之前，必须填入有意义的数据。装载数据后，就应该使用"不可修改的"方法返回的引用去替换掉原本的引用。这样，就不用担心无意中修改了只读的内容。
 * 另一方面，此方法允许你保留一份可修改的容器，作为类的private成员，然后通过某个方法调用返回对该容器的"只读"的引用。这样以来，就只有你可以修改容器的内容，而别人只能读取。
*/
