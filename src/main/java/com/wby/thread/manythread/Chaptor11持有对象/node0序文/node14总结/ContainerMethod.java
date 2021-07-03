package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node14总结;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 17:36
 *  java提供了大量持有对象的方式：
 *      1.数组将数字与对象联系起来。他保存类型明确的对象，查询对象时，不需要对结果做类型转换。
 *          他可以是多维的，可以保存基本类型的数据。
 *          但是数组一旦生成，其容量就不能改变。
 *      2.Collection保存单一的元素，而Map保存相关联的键值对。
 *          有了java泛型，你就可以指定容器中对象的类型，就不会把错误的对象放到容器中，并且从容器中获取对象时，不必进行类型转换。
 *          各种Collection和Map都可以在你向其中添加元素时自动调整尺寸。
 *          容器不能持有基本类型，但是自动包装机制会执行基本类型到容器中所持有的包装器类型之间的双向转换
 *      3.就像数组一样，List也建立数字索引与对象的关联。
 *          因此，数组和List都是排好序的容器。
 *          List能够自动扩充容量
 *      4.如果要进行大量随机访问，应该使用ArrayList。要哦经常插入删除，使用LinkedList
 *      5.各种Queue以及栈的行为，都是通过LinkedList提供支持
 *      6.Map是一种将对象（而非数字）与对象相关联的设计。
 *          hashMap被设计用来快速访问。
 *          TreeMap保持“键”始终处于排序状态，所以没有HashMap快。
 *          LinkedHashMap保持元素的插入顺序，但是也通过散列提供了快速访问能力。
 *      7.Set不接受重复的元素.
 *          HashSet有最快的查询速度
 *          TreeSet保持元素处于排序状态
 *          LinkedHashSet以插入顺序保持元素
 *      8.新程序中不应该使用过时的Vector、HashTable和Stack
 *
 *  其实只有四种容器：Map、List、Set和Queue
 *      他们各有两到三个实现版本（Queue的juc包下的实现）
 *
 *  下面的例子展示了各种不同的类在方法上的差异,实际代码来自15章
 */
public class ContainerMethod {
    public static void main(String[] args) {
        //ContainerMethodDiffirence.main(args);
    }
}
/**
* @Description: 可以看到，除了TreeSet之外的所有Set拥有与Collection一样的接口。
 *  List和Collection存在着明显的不同，尽管List所要求的方法都在Collection中。
 *  Queue接口中的方法都是独立的；在创建具有Queue功能的实现时，不需要使用Collection方法。
 *  Map和Collection之间的唯一重叠就是Map可以使用entrySet()、values()方法产生Collection
 *
 *  注意，标机接口util.RandomAccess附着到了ArrayList上，而不是LinkedList上，这为想要根据所使用的特定的List而动态修改其行为的算法提供了信息
*/
