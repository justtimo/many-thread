package com.wby.thread.manythread.character21并发.node7新类库中的构件.child1CountDownLatch;//: concurrency/CountDownLatchDemo.java

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 它被用来同步一个或多个任务，强制它们等待由其他任务执行的一组操作完成。
 *
 * 你可以向CountDownLatch对象设置一个初始计数值，任何在这个对象上调用waitO的方法都将阻塞，直至这个计数值到达0。其他任务在结束其工作时，可以在该对象上调用countDownO来减小这个计数值。
 * CountDownLatch被设计为只触发一次，计数值不能被重置。如果你需要能够重置计数值的版本，则可以使用CyclicBarrier。
 *
 * 调用countDownO的任务在产生这个调用时并没有被阻塞，只有对await（）的调用会被阻塞，直至计数值到达0。
 *
 * CountDownLatch的典型用法是将一个程序分为n个互相独立的可解决任务，并创建值为0的 CountDownLatch。当每个任务完成时，都会在这个锁存器上调用countDownO）。
 * 等待问题被解决的任务在这个锁存器上调用await（），将它们自己拦住，直至锁存器计数结束。下面是演示这种技术的一个框架示例∶
 */
// Performs some portion of a task:
class TaskPortion implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private static Random rand = new Random(47);
  private final CountDownLatch latch;
  TaskPortion(CountDownLatch latch) {
    this.latch = latch;
  }
  public void run() {
    try {
      doWork();
      latch.countDown();
    } catch(InterruptedException ex) {
      // Acceptable way to exit
    }
  }
  public void doWork() throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
    print(this + "completed");
  }
  public String toString() {
    return String.format("%1$-3d ", id);
  }
}

// Waits on the CountDownLatch:
class WaitingTask implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private final CountDownLatch latch;
  WaitingTask(CountDownLatch latch) {
    this.latch = latch;
  }
  public void run() {
    try {
      latch.await();
      print("Latch barrier passed for " + this);
    } catch(InterruptedException ex) {
      print(this + " interrupted");
    }
  }
  public String toString() {
    return String.format("WaitingTask %1$-3d ", id);
  }
}

public class CountDownLatchDemo {
  static final int SIZE = 100;
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // All must share a single CountDownLatch object:
    CountDownLatch latch = new CountDownLatch(SIZE);
    for(int i = 0; i < 10; i++)
      exec.execute(new WaitingTask(latch));
    for(int i = 0; i < SIZE; i++)
      exec.execute(new TaskPortion(latch));
    print("Launched all tasks");
    exec.shutdown(); // Quit when all tasks complete
  }
} /* (Execute to see output) *///:~
/**
 * TaskPortion将随机地休眠一段时间，以模拟这部分工作的完成，而WaitingTask表示系统中必须等待的部分，它要等待到问题的初始部分完成为止。所有任务都使用了在mainO中定义的同一个单一的CountDownLatch。
 *
 * 练习32;（7）使用CountDownLatch解决OrnamentalGarden.java中Entrance产生的结果互相关联的问题。从新版本的示例中移除不必要的代码。
 */

/**
 * 类库的线程安全
 *
 * 注意，TaskPortion包含一个静态的Random对象，这意味着多个任何可能会同时调用 Random.nextInt（。这是否安全呢?
 *
 * 如果存在问题，在这种情况下，可以通过向TaskPortion提供其自己已的Random对象来解决。也就是说，通过移除static限定符的方式解决。但是这个问题对于Java标准类库中的方法来说，也大都存在∶哪些是线程安全的?哪些不是?
 *
 * 遗憾的是，JDK文档并没有指出这一点。Random.nextIntO碰巧是安全的，但是，你必须通过使用Web引擎，或者审视Java类库代码，去逐个地揭示这一点。这对于被设计为支持，至少理论上支持并发的程序设计语言来说，并非是一件好事。
 */































