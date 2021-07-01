package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node11Queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 09:55
 * @Description:    队列是一个典型的先进先出(FIFO)容器，事物放入容器的顺序和取出的顺序是相同的。
 *  队列常被当做一种可靠的将对象从程序的某处传输到另外一个区域的途径。
 *  队列在并发编程中非常重要，因为他们可以安全的将一个对象从一个任务传输到另一个任务。
 *
 *  LinkedList提供了方法以支持队列，并且他实现了Queue接口，因此LinkedList可以作为Queue的一种实现。
 *  通过将LinkedList向上转型为Queue，下面的例子演示了在Queue接口中与Queue相关的接口
 */
public class QueueDemo {
    public static void printQueue(Queue queue){
        while (queue.peek()!=null){
            System.out.println(queue.remove()+" ");
        }
    }
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            queue.offer(r.nextInt(i+10));
        }
        printQueue(queue);
        Queue<Character> qc = new LinkedList<>();
        for (char c : "Braondtosaumas".toCharArray()) {
            qc.offer(c);
        }
        printQueue(qc);
    }
}
/**
* @Description: offer方法是和Queue有关的方法，他将一个元素插入到队列末尾，或者返回false
 * peek和element方法会在不删除头部元素的情况下返回队头，但是peek方法在队列为空的时候返回null，而element方法则会抛出NoSuchElement异常
 * poll和remove方法移除并返回队头，但是poll在队列为空的时候返回null，而remove方法则会抛出NoSuchElement异常
 *
 * Queue接口窄化了LinkedList的方法访问权限，以使得只有恰当的方法才能使用，因此你能使用的LinkedList方法会变少（这里可以将queue向上转型为LinkedList，但是不推荐这么做）
 *
 * 注意，与Queue有关的方法提供了完整而独立的功能。即，对Queue所继承的Collection，在不需要使用它任何方法的情况下，就可以拥有一个可用的Queue
*/
