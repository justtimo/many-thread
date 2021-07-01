package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node7LinkedList;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 17:35
 * @Description:  LinkedList与ArrayList都实现了List接口，但是他执行某些操作（在List的中间插入和删除）时比ArrayList更高效，但随机访问时会逊色一些。
 *  LinkedList还添加了可以使其用在栈、队列、双向队列的方法。
 *  这些方法名字彼此有些差异，是的在特定用法的上下文环境中更加适用(特别是在Queue中)
 *  例如：
 *      getFirst和element完全一样，都是返回列表的头(第一个元素),而并不移除他，如果List为空抛出NoSuchElementException。
 *      peek方法与这两个方式稍有差异，它在列表为空时返回null
 *
 *      removeFirst和remove也是完全一样的，他们移除并返回列表的头，而且列表为空时抛出NoSuchElementException。
 *      poll方法列表为空时返回null
 *  addFirst和 add和addLast相同，他们都将某个元素插入到列表的尾端
 *  removeLast移除并返回列表的最后一个元素
 *  offer方法添加元素到末尾
 *  下面的例子展示了这些特性之间的相同性和差异性
 */
public class LinkedListFeatures {
    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList<>(Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6"));
        String first = strings.getFirst();
        String element = strings.element();
        String peek = strings.peek();

        String s = strings.removeFirst();
        String remove = strings.remove();
        String poll = strings.poll();

        strings.addFirst("wby7");
        strings.add("wby8");
        strings.addLast("wby9");

        String s1 = strings.removeLast();

        boolean offer = strings.offer("wby11");


    }
}
