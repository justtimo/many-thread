package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node11Queue.child1PriorityQueue;

import com.wby.thread.manythread.Chaptor11持有对象.node0序文.node11Queue.QueueDemo;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 10:31
 * @Description:    先进先出描述了最典型的队列规则。
 *  队列规则：在给定一组队列中元素的情况下，确定下一个弹出队列的元素的规则。
 *  先进先出声明的是下一个元素应该是等待时间最长的那个元素。
 *
 *  优先级队列声明下一个元素是最需要的元素（最高优先级）
 *  如果构建了一个消息系统，某些消息比其他消息更重要，那么应该更快的处理，那么他们何时得到处理就与他们何时到达无关。
 *
 *  当使用PriorityQueue的offer方法插入一个对象时，这个对象会在队列中被排序(这依赖于具体实现。优先级队列算法通常会在插入时排序(维护一个堆)，但是他们也可能在移除时选择最重要的元素。
 *      如果对象的优先级在它在队列中等待时可以修改，name算法的选择就很重要了)，默认的排序将使用对象在队列中的自然顺序，但是可以通过提供自己的Comparator来修改这个顺序
 *  PriorityQueue可以确保当你调用peek、poll、remove方法时，获取的元素将是队列中优先级最高的
 *
 *  下例中，第一个值集 与 前一个例子中的随机数相同，因此可以看到她与上一个例子弹出的顺序不同
 */
public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            priorityQueue.offer(rand.nextInt(i+10));
        }
        QueueDemo.printQueue(priorityQueue);

        List<Integer> ints = Arrays.asList(25, 22, 26, 43, 14, 677, 8, 5, 4, 24, 89, 16, 17, 19, 56, 26, 123, 86, 64, 43, 23);
        priorityQueue=new PriorityQueue<Integer>(ints);
        QueueDemo.printQueue(priorityQueue);
        priorityQueue=new PriorityQueue<Integer>(ints.size(), Collections.reverseOrder());
        priorityQueue.addAll(ints);
        QueueDemo.printQueue(priorityQueue);

        String fact="ENDGE WOJQIWE ASDADZXZCG QWE QTHGFG";
        List<String> strings = Arrays.asList(fact.split(" "));
        PriorityQueue<String> stringPQ = new PriorityQueue<>(strings);
        QueueDemo.printQueue(stringPQ);
        stringPQ=new PriorityQueue<>(strings.size(),Collections.reverseOrder());
        stringPQ.addAll(strings);
        QueueDemo.printQueue(stringPQ);

        HashSet<Character> charSet = new HashSet<>();
        for (char c : fact.toCharArray()) {
            charSet.add(c);
        }
        PriorityQueue<Character> characterPQ = new PriorityQueue<>(charSet);
        QueueDemo.printQueue(characterPQ);
    }
}
/**
* @Description: 其中， PriorityQueue中是允许重复元素的，最小的值拥有最高优先级（如果是String，空格也算作值，并且比字幕优先级高）。
 *  为了展示可以使用什么方法来通过提供自己的comparator对象来改变顺序，使用了Collections.reverseOrder()产生反序的comparator
 *
 *  Integer、String、Charactor可以和PriorityQueue一起工作，因为这些类已经内建了自然排序。如果想在PriorityQueue中使用自己的类，
 *  就必须包括额外的功能以产生自然排序，或者必须提供自己的comparator
*/
