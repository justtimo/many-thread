package com.wby.thread.manythread.character21并发.node2基本的线程机制.child5休眠;//: concurrency/SleepingTask.java
// Calling sleep() to pause for a while.

import com.wby.thread.manythread.character21并发.node2基本的线程机制.child1定义任务.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 影响任务行为的一种简单方法是调用sleepO，这将使任务中止执行给定的时间。在LiftOff类中，要是把对yieldO的调用换成是调用sleepO，将得到如下结果∶
 */
public class SleepingTask extends LiftOff {
  public void run() {
    try {
      while(countDown-- > 0) {
        System.out.print(status());
        // Old-style:
        // Thread.sleep(100);
        // Java SE5/6-style:
        TimeUnit.MILLISECONDS.sleep(100);
      }
    } catch(InterruptedException e) {
      System.err.println("Interrupted");
    }
  }
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new SleepingTask());
    exec.shutdown();
  }
} /* Output:
#0(9), #1(9), #2(9), #3(9), #4(9), #0(8), #1(8), #2(8), #3(8), #4(8), #0(7), #1(7), #2(7), #3(7), #4(7), #0(6), #1(6), #2(6), #3(6), #4(6), #0(5), #1(5), #2(5), #3(5), #4(5), #0(4), #1(4), #2(4), #3(4), #4(4), #0(3), #1(3), #2(3), #3(3), #4(3), #0(2), #1(2), #2(2), #3(2), #4(2), #0(1), #1(1), #2(1), #3(1), #4(1), #0(Liftoff!), #1(Liftoff!), #2(Liftoff!), #3(Liftoff!), #4(Liftoff!),
*///:~
/**
 * 对sleepO的调用可以抛出InterruptedException异常，并且你可以看到，它在runO中被捕获。因为异常不能跨线程传播回mainO，所以你必须在本地处理所有在任务内部产生的异常。
 *
 * Java SE5引入了更加显式的sleepO版本，作为TimeUnit类的一部分，就像上面示例所示的那样。这个方法允许你指定sleepO延迟的时间单元，因此可以提供更好的可阅读性。TimeUnit还可以被用来执行转换，就像稍后你会在本书中看到的那样。
 *
 * 你可能会注意到，这些任务是按照"完美的分布"顺序运行的，即从0到4，然后再回过头从0开始，当然这取决于你的平台。这是有意义的，因为在每个打印语句之后，每个任务都将要睡眠（即阻塞）。
 * 这使得线程调度器可以切换到另—个线程。进而驱动另一个任务。但是。顺序行为依赖于底层的线程机制，这种机制在不同的操作系统之间是有差异的，因此，你不能依赖于它。如果你必须控制任务执行的顺序。
 * 那么最好的押宝就是使用同步控制（稍候描述），或者在某些情况下，压根不使用线程，但是要编写自己的协作例程，这些例程将会按照指定的顺序在互相之间传递控制权。
 *
 * 练习6∶（2）创建一个任务，它将睡眠1至10秒之间的随机数量的时间，然后显示它的睡眠时间并退出。创建并运行一定数量的这种任务。
 */
