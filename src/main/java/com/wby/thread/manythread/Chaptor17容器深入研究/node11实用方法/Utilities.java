package com.wby.thread.manythread.Chaptor17容器深入研究.node11实用方法;//: containers/Utilities.java
// Simple demonstrations of the Collections utilities.

import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: Java中有大量用于容器的卓越的使用方法，它们被表示为java.util.Collections类内部的静态方法。你已经看到过其中的一部分，例如addAllO、
 * reverseOrderO）和binarySearch（。下面是另外一部分（synchronized和unmodifiable的使用方法将在后续的小节中介绍）。在这张表中，在相关的情况中使用了泛型∶
 *
 * checkedCollection(Colection<T>, Class<T> type)
 * checkedList(List<T>, Class<T> type)
 * checkedMap(Map<K,V>, Class<K> keylype, Clss<V> valuelType)
 * checkedSet(Set<T>, Class<T> type)                                                              产生Collection或者Collection的具体子类型的动态类型安全的视图。在不可能使用静态检查版本时使用这些方法
 * chekedSortedMap(SortedMap<K,V>,Class<K> keyType,[879                                           这些方法在第15章中的"动态类型安全"标题下进行过说明
 * Class<V> valueType)
 * checkedSortedSet(SortedSet<T>,Class<T> type)
 *
 * max(Coletion)                                                                                  返回参数Collection中最大或最小的元素-采用 Colletion内置的自然比较法
 * min(Cletion)
 *
 * max(Coletion, Comparator)                                                                      返回参数Collection中最大或最小的元素-采用Comparator进行比较
 * min(Colection Comparator)
 *
 * indexOrSubListList source,List target)                                                         返回target在source中第一次出现的位置，或者在找不到时返回-1。
 *
 * lastIndexOrSubLst(List source,List target)                                                     返回target在source中最后一次出现的位置，或者在找不到时返回-1
 *
 * replaceAll(List<T>,T oldval,T newVa)                                                           使用newVal替换所有的oldVal逆转所有元素的次序
 *
 * reverseList)                                                                                   逆转所有元素次序
 *
 * reverseOrder(                                                                                  返回一个Comparator，它可以逆转实现了 Comparator<T>的对象集合的自然顺序。第二个版本可以逆转所提供的Comparator的顺序
 * reverseOrder(Comparator<T>)
 *
 * rotate(List, int distance)                                                                     所有元素向后移动distance个位置，将末尾的元素循环到前面来
 *
 * shufne(List)                                                                                   随机改变指定列表的顺序。第一种形式提供了其自己的随机机制，你可以通过第二种形式提供自己的随机机制
 * shufie(List, Random)
 *
 * sort(List<T>)                                                                                  使用List<T>中的自然顺序排序。第二种形式允许提供用于排序的Comparator
 * sort(List<T>,Comparator<? Super T>c)
 *
 * copy(List<? super T>dest,List<?extendsT>sre)                                                   将srce中的元素复制到dest
 *
 * swap(List,int i,it j))                                                                         交换list中位置i与位置j的元素。通常比你自己写的代码快
 *
 * fill(List<? super T>,T x)                                                                      用对象x替换list中的所有元素
 *
 * nCopes(int n,T x)                                                                              返回大小为n的List<T>，此List不可改变，其中的引用都指向x
 *
 * disjoint(Coletion,Colletion)                                                                   当两个集合没有任何相同元素时，返回true
 *
 * frequency(Colletion,Object x)                                                                  返回Collction中等于x的元素个数
 *
 * emptyList
 * emptyMapO                                                                                      返回不可变的空List、Map或Set。这些方法都是泛型的，因此所产生的结果将被参数化为所希望的类型
 * emptSet)
 *
 * singleton(T x)
 * singletonList"T x)                                                                             产生不可变的Set<T>、List<T>或Map<K，V>，它们都只包含基于所给定参数的内容而形成的单一项
 * singletonMap(K key, V value)
 *
 * list(Enumeration <T> e)                                                                        产生一个ArrayList<T>，它包含的元素的顺序，与（旧式的）Enumeration （Iterator的前身）返回这些元素的顺序相同。用来转换遗留的老代码
 *
 * enumeration(Collection<T>)                                                                     为参数生成一个旧式的Enumeration<T>
 *
 * 注意，min（）和max（）只能作用于Collection对象，而不能作用于List，所以你无需担心Collection是否应该被排序（如前所述，只有在执行binarySearch（）之前，
 * 才确实需要对List或数组进行排序）。
*/
public class Utilities {
  public static List<String> list = Arrays.asList(
    "one Two three Four five six one".split(" "));
  public static void main(String[] args) {
    print(list);
    print("'list' disjoint (Four)?: " +
      Collections.disjoint(list,
        Collections.singletonList("Four")));
    print("max: " + Collections.max(list));
    print("min: " + Collections.min(list));
    print("max w/ comparator: " + Collections.max(list,
      String.CASE_INSENSITIVE_ORDER));
    print("min w/ comparator: " + Collections.min(list,
      String.CASE_INSENSITIVE_ORDER));
    List<String> sublist =
      Arrays.asList("Four five six".split(" "));
    print("indexOfSubList: " +
      Collections.indexOfSubList(list, sublist));
    print("lastIndexOfSubList: " +
      Collections.lastIndexOfSubList(list, sublist));
    Collections.replaceAll(list, "one", "Yo");
    print("replaceAll: " + list);
    Collections.reverse(list);
    print("reverse: " + list);
    Collections.rotate(list, 3);
    print("rotate: " + list);
    List<String> source =
      Arrays.asList("in the matrix".split(" "));
    Collections.copy(list, source);
    print("copy: " + list);
    Collections.swap(list, 0, list.size() - 1);
    print("swap: " + list);
    Collections.shuffle(list, new Random(47));
    print("shuffled: " + list);
    Collections.fill(list, "pop");
    print("fill: " + list);
    print("frequency of 'pop': " +
      Collections.frequency(list, "pop"));
    List<String> dups = Collections.nCopies(3, "snap");
    print("dups: " + dups);
    print("'list' disjoint 'dups'?: " +
      Collections.disjoint(list, dups));
    // Getting an old-style Enumeration:
    Enumeration<String> e = Collections.enumeration(dups);
    Vector<String> v = new Vector<String>();
    while(e.hasMoreElements())
      v.addElement(e.nextElement());
    // Converting an old-style Vector
    // to a List via an Enumeration:
    ArrayList<String> arrayList =
      Collections.list(v.elements());
    print("arrayList: " + arrayList);
  }
} /* Output:
[one, Two, three, Four, five, six, one]
'list' disjoint (Four)?: false
max: three
min: Four
max w/ comparator: Two
min w/ comparator: five
indexOfSubList: 3
lastIndexOfSubList: 3
replaceAll: [Yo, Two, three, Four, five, six, Yo]
reverse: [Yo, six, five, Four, three, Two, Yo]
rotate: [three, Two, Yo, Yo, six, five, Four]
copy: [in, the, matrix, Yo, six, five, Four]
swap: [Four, the, matrix, Yo, six, five, in]
shuffled: [six, matrix, the, Four, Yo, five, in]
fill: [pop, pop, pop, pop, pop, pop, pop]
frequency of 'pop': 7
dups: [snap, snap, snap]
'list' disjoint 'dups'?: true
arrayList: [snap, snap, snap]
*///:~
/**
* @Description: 该程序的输出可看作是对每个实用方法的行为的解释。请注意由于大小写的缘故而造成的使用String.CASE_INSENSITIVE_ORDER Comparator时min（和maxO）的差异。
*/
