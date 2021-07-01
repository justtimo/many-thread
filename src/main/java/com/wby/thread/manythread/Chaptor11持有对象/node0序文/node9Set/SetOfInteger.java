package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node9Set;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 19:38
 * @Description:    Set不保存重复的元素(如何判断元素相同较为复杂，稍后可以看到)
 *  Set中最常用的是 测试归属性 ，你可以很轻易地询问某个对象是否在Set中。正因为如此，查询成为了Set最重要的操作，因此通常选择HashSet，他专门对快速查找进行了优化
 *
 *  Set与Collection具有完全相同的接口，因此没有额外的功能。实际上Set就是Collection，只是行为不同：继承与多态的典型应用：表现不同的行为。
 *  Set是基于对象的值来确定归属性的，而更复杂问题在17章中介绍。
 */
public class SetOfInteger {
    public static void main(String[] args) {
        Random random = new Random(47);
        HashSet<Integer> integers = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            integers.add(random.nextInt(30));
        }
        System.out.println(integers);
    }
}
/**
* @Description: 输出结果可能不是顺序的(虽然自测都是顺序的)，这是出于速度原因的考虑，HashSet使用了散列。
 *  HashSet所维护的顺序与TreeSet或LinkedHashSet都不同，因为他们的实现具有不同的元素存储方式。
 *
 *  TreeSet将元素存在红-黑数结构中，而HashSet使用的是散列函数，LinkedHashSet因为查询速度的原因也使用了散列，但是看起来它使用了链表维护元素的插入顺序。
 *
 *  如果想对结果排序，一种方式是使用TreeSet替代HashSet
*/
class SortSetOfInterger{
    public static void main(String[] args) {
        Random random = new Random(47);
        TreeSet<Integer> integers = new TreeSet<Integer>();
        for (int i = 0; i < 10000; i++) {
            integers.add(random.nextInt(30));
        }
        System.out.println(integers);
    }
}
/**
* @Description: 你将会执行最常见的操作之一，就是使用contains测试Set的归属性
*/
class SetOperations{
    public static void main(String[] args) {
        Set<String> set1 = new HashSet<>();
        Collections.addAll(set1,"A B C D E F G H I J K M L".split(" "));
        set1.add("M");
        System.out.println("H"+set1.contains("H"));
        System.out.println("M"+set1.contains("M"));
        Set<String> set2 = new HashSet<>();
        Collections.addAll(set2,"H I J K M L".split(" "));
        System.out.println("set2 in set1"+set1.containsAll(set2));
        set1.remove("H");
        System.out.println("set2 in set1"+set1.containsAll(set2));
        set1.removeAll(set2);
        System.out.println("set2 remove from set1:"+set1);
        Collections.addAll(set1," X Y Z".split(" "));
        System.out.println("X Y Z add to set1"+set1);
    }
}
/**
* @Description: 能够产生每个元素都唯一的列表是相当有用的功能。
 * 例如：在你想要列出在上面的SetOperations.java文件中所有的单词的时候。
*/
class UniqueWords{
    public static void main(String[] args) {
        TreeSet<Integer> integers = new TreeSet<Integer>(
                //new TextFile("SetOperations.java","\\W+")

        );

    }
}
/**
* @Description: TextFile继承自List<String>，其构造起打开文件并根据正则表达式“\\W+”将其断开为单词.所产生的的结果传递给了TreeSet的构造器，它将把List中的内容添加到自身中。
 *  由于他是TreeSet的，因此结果是排序的，本例中，是根据字典序进行排序的，因此大写、小写被划分到了不同的组中。
 *  如果想要按照字母排序，那么可以向TreeSet的构造器传入String.CASE_INSENSITIVE_ORDER比较器（比较器就是建立排序顺序的对象）
*/
class UniqueWordsAlphabetic{
    public static void main(String[] args) {
        TreeSet<String> strings = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        strings.addAll(
                //new TextFile("SetOperations.java","\\W+")
                strings
        );
        System.out.println(strings);
    }
}
