package com.wby.thread.manythread.character21并发.node5线程之间的协作.child2notify与notifyAll;//: concurrency/NotifyVsNotifyAll.java

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 因为在技术上，可能会有多个任务在单个Car对象上处于waitO）状态，因此调用notifyAllO比只调用notifyO要更安全。但是，上面程序的结构只会有一个任务实际处于waitO状态，因此你可以使用notify（来代替notifyAll（）。
 *
 * 使用notifyO而不是notifyAllO是一种优化。使用notify（时，在众多等待同一个锁的任务中只有一个会被唤醒，因此如果你希望使用notify（，就必须保证被唤醒的是恰当的任务。
 * 另外，为了使用notifyO，所有任务必须等待相同的条件，因为如果你有多个任务在等待不同的条件，那么你就不会知道是否唤醒了恰当的任务。如果使用notifyO，当条件发生变化时，必须只有一个任务能够从中受益。
 * 最后，这些限制对所有可能存在的子类都必须总是起作用的。如果这些规则中有任何一条不满足，那么你就必须使用notifyAllIO而不是notify（。
 *
 * 在有关Java的线程机制的讨论中，有一个令人困惑的描述; notifyAlIO将唤醒"所有正在等待的任务"。这是否意味着在程序中任何地方，任何处于wait（）状态中的任务都将被任何对 notifyAlIO的调用唤醒呢?
 * 在下面的示例中，与Task2相关的代码说明了情况并非如此——事实上，当notifyAlIO因某个特定锁而被调用时，只有等待这个锁的任务才会被唤醒∶
 */
class Blocker {
  synchronized void waitingCall() {
    try {
      while(!Thread.interrupted()) {
        wait();
        System.out.print(Thread.currentThread() + " ");
      }
    } catch(InterruptedException e) {
      // OK to exit this way
    }
  }
  synchronized void prod() { notify(); }
  synchronized void prodAll() { notifyAll(); }
}

class Task implements Runnable {
  static Blocker blocker = new Blocker();
  public void run() { blocker.waitingCall(); }
}

class Task2 implements Runnable {
  // A separate Blocker object:
  static Blocker blocker = new Blocker();
  public void run() { blocker.waitingCall(); }
}

public class NotifyVsNotifyAll {
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new Task());
    exec.execute(new Task2());
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      boolean prod = true;
      public void run() {
        if(prod) {
          System.out.print("\nnotify() ");
          Task.blocker.prod();
          prod = false;
        } else {
          System.out.print("\nnotifyAll() ");
          Task.blocker.prodAll();
          prod = true;
        }
      }
    }, 400, 400); // Run every .4 second
    TimeUnit.SECONDS.sleep(5); // Run for a while...
    timer.cancel();
    System.out.println("\nTimer canceled");
    TimeUnit.MILLISECONDS.sleep(500);
    System.out.print("Task2.blocker.prodAll() ");
    Task2.blocker.prodAll();
    TimeUnit.MILLISECONDS.sleep(500);
    System.out.println("\nShutting down");
    exec.shutdownNow(); // Interrupt all tasks
  }
} /* Output: (Sample)
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
Timer canceled
Task2.blocker.prodAll() Thread[pool-1-thread-6,5,main]
Shutting down
*///:~
/**
 * Task和Task2每个都有其自己的Blocker对象，因此每个Task对象都会在Task.blocker上阻塞，而每个Task2都会在Task2.blocker上阻塞。在mainO中，
 * javautil.Timer对象被设置为每4/10秒执行一次runO方法，而这个run（O方法将经由"激励"方法交替地在Task.blocker上调用notifyO和 notifyAO。
 *
 * 从输出中你可以看到，即使存在Task2.blocker上阻塞的Task2对象，也没有任何在 Task.blocker上的notifyO或notifyAllQ调用会导致Task2对象被唤醒。
 * 与此类似，在mainO的结尾，调用了timer的cancelO，即使计时器被撤销了，前5个任务也依然在运行，并仍旧在它们对 Task，blocker.waitingCallO的调用中被阻塞。
 * 对Task2.blocker.prodAlO的调用所产生的输出不包括任何在Task.blocker中的锁上等待的任务。
 *
 * 如果你浏览Blocker中的prodO）和prodAl1O），就会发现这是有意义的。这些方法是 synchronized的，这意味着它们将获取自身的锁，因此当它们调用notifyO或notifyAll（O时，
 * 只在这个锁上调用是符合逻辑的-—因此，将只唤醒在等待这个特定锁的任务。
 *
 * Blocker.waitingCall（）非常简单，以至于在本例中，你只需声明for（;;）而不是while（!Thread。 interrupted（））就可以达到相同的效果，因为在本例中，
 * 由于异常而离开循环和通过检查 interrupted（）标志离开循环是没有任何区别的——在两种情况下都要执行相同的代码。但是，事实上，这个示例选择了检查interruptedO，因为存在着两种离开循环的方式。
 * 如果在以后的某个 1207时刻，你决定要在循环中添加更多的代码，那么如果没有覆盖从这个循环中退出的这两条路径，就会产生引入错误的风险。
 *
 * 练习23∶（7）演示当你使用notifyO来代替notifyAllO时，WaxOMatic.java可以成功地工作。
 */
