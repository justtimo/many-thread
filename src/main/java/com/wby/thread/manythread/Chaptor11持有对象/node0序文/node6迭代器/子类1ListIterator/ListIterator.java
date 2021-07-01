package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node6迭代器.子类1ListIterator;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 17:20
 * @Description:    ListIterator是一个更加强大的Iterator子类，他只能用于各种List类的访问。
 *  尽管Iterator只能向前移动，但是ListIterator可以双向移动。他还可以产生相对于迭代器在列表中指向的当前位置的前一个和后一个的索引，并且可以使用set方法替换他访问的最后一个元素。
 *  你可以通过调用listIterator方法产生一个指向List开始处的ListIterator，并且可以通过调用listIterator(n)方法创建一个一开始就指向列表索引为n的元素处的ListIterator。
 *
 *  下面的例子展示了这些能力
 */
public class ListIterator {
    public static void main(String[] args) {
        List<String> strings1 = Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6");
        java.util.ListIterator<String> it = strings1.listIterator();
        while (it.hasNext()){
            System.out.println(it.next()+","+it.nextIndex()+","+it.previousIndex());
        }
        while (it.hasPrevious()){
            System.out.println(it.previous());
        }
        it = strings1.listIterator(3);
        while (it.hasNext()){
            it.next();
            it.set("wangwei");
        }
    }
}
/**
* @Description:  set方法替换在列表中从位置3开始向前的所有对象
*/
