package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/29 17:38
 * @Description: 现在已经知道了，尽管实际上只有四种容器;Map、List、Set和Oueue，但是每种接口都有不止一个实现版本。如果需要使用某种接口的功能，应该如何选择使用哪一个实现呢?
 *
 * 每种不同的实现各自的特征、优点和缺点。例如，从容器分类图中可以看出，Hashtable、 Vector和Stack的"特征"是，它们是过去遗留下来的类，目的只是为了支持老的程序（最好不要在新的程序中使用它们）。
 *
 * 在Java类库中不同类型的Queue只在它们接受和产生数值的方式上有所差异（你将在第21章中看到其重要性）。
 *
 * 容器之间的区别通常归结为由什么在背后"支持" 它们。也就是说，所使用的接口是由什么样的数据结构实现的。例如，因为ArrayList和LinkedList都实现了List接口，
 * 所以无论选择哪一个，基本的List操作都是相同的。然而，ArrayList底层由数组支持;而LinkedList是由双向链表实现的，其中的每个对象包含数据的同时还包含指向链表中
 * 前一个与后一个元素的引用。因此，如果要经常在表中插入或删除元素，LinkedList就比较合适（LinkedList还有建立在 AbstractSequentialList基础上的其他功能）;
 * 否则，应该使用速度更快的ArrayList。
 *
 * 再举个例子，Set可被实现为TreeSet、HashSet或LinkedHashSet(或者实现为EnumSet和CopyOnWriteArraySet，它们是特例。尽管我承认各种不同的容器接口都可能拥有额
 * 外的特殊实现，但是本节还是试图只浏览那些更加通用的情况。) 。每一种都有不同的行为∶
 * HashSet是最常用的，查询速度最快;LinkedHashSet保持元素插入的次序;
 * TreeSet基于 TreeMap，生成一个总是处于排序状态的Set。你可以根据所需的行为来选择不同的接口实现。
 *
 * 有时某个特定容器的不同实现会拥有一些共同的操作，但是这些操作的性能却并不相同。在这种情况下，你可以基于使用某个特定操作的频率，以及你需要的执行速度来在它们中间进行选择。
 * 对于类似的情况，一种查看容器实现之间差异的方式是使用性能测试。
 */
public class Text {
}
