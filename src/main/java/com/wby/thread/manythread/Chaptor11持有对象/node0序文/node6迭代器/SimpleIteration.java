package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node6迭代器;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 16:51
 * @Description:    任何容器类，都必有有某种方式能够插入元素并将他们取回。对于List就是add和get方法
 *  从更高的角度来说，会有一个缺点：要使用容器，必须对容器的确切类型进行编程。如果原本对着List编码，后来发现如果能够把相同的代码应用于Set，将会很方便。
 *
 *  迭代器(也是一种设计模式)的概念可以达成此目的。迭代器通常被称为轻量级对象：创建他的代价小。
 *  因此会有一些奇怪的限制：例如java的迭代器只能单向移动，这个Iterator只能用来：
 *      1.使用iterator方法要求容器返回一个Iterator对象，Iterator将准备好返回序列的第一个元素
 *      2.使用next获得序列中的下一个元素
 *      3.使用hasNext检查序列中是否还有元素
 *      4.使用remove将迭代器新进返回的元素全部删除
 */
public class SimpleIteration {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6"));
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
        for (String string : strings) {
            System.out.println(string);
        }
        for (int i = 0; i < strings.size(); i++) {
            iterator.next();
            iterator.remove();
        }
    }
}
/**
* @Description: Iterator还可以移除由next产生的最后一个元素，这意味着调用remove之前必须先调用next
*/

/**
* @Description: 接受对象容器并传递他，从而在每个对象上都执行操作，这种思想十分强大，并且贯穿本书。
 * 现在考虑创建一个display方法，他不必知道容器的确切类型
*/
class CrossContainerIteration{
    public static void display(Iterator<String> it){
        while (it.hasNext()){
            String next = it.next();
            System.out.println(next);
        }
    }
    public static void main(String[] args) {
        List<String> strings1 = Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6");
        ArrayList<String> strings = new ArrayList<>(strings1);
        LinkedList<String> stringsLL = new LinkedList<>(strings1);
        HashSet<String> stringsHS = new HashSet<>(strings1);
        TreeSet<String> stringsTS = new TreeSet<>(strings1);
        display(strings.iterator());
        display(stringsLL.iterator());
        display(stringsHS.iterator());
        display(stringsTS.iterator());
    }
}
/**
* @Description: 注意display方法不包含任何有关他所遍历的序列的类型信息，而这也展示了Iterator的真正威力：能够将遍历序列与序列底层结构分离。
 *  真因为如此，我们才会说：迭代器统一了对容器的访问方式。
*/
