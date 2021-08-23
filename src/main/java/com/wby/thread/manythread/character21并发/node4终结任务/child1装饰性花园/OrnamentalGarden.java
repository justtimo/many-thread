package com.wby.thread.manythread.character21并发.node4终结任务.child1装饰性花园;//: concurrency/OrnamentalGarden.java

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 在前面的某些示例中，cancelO和isCanceledO方法被放到了一个所有任务都可以看到的类中。这些任务通过检查isCanceled（）来确定何时终止它们自己，对于这个问题来说，这是一种合理的方式。
 * 但是，在某些情况下，任务必须更加突然地终止。本节你将学习到有关这种终止的各类话题和问题。
 *
 * 首先，让我们观察一个示例，它不仅演示了终止问题，而且还是一个资源共享的示例。
 *
 * 在这个仿真程序中，花园委员会希望了解每天通过多个大门进入公园的总人数。每个大门都有一个十字转门或某种其他形式的计数器，并且任何一个十字转门的计数值递增时;就表示公园中的总人数的共享计数值也会递增。
 */
class Count {
  private int count = 0;
  private Random rand = new Random(47);
  // Remove the synchronized keyword to see counting fail:
  public synchronized int increment() {
    int temp = count;
    if(rand.nextBoolean()) // Yield half the time
      Thread.yield();
    return (count = ++temp);
  }
  public synchronized int value() { return count; }
}

class Entrance implements Runnable {
  private static Count count = new Count();
  private static List<Entrance> entrances =
    new ArrayList<Entrance>();
  private int number = 0;
  // Doesn't need synchronization to read:
  private final int id;
  private static volatile boolean canceled = false;
  // Atomic operation on a volatile field:
  public static void cancel() { canceled = true; }
  public Entrance(int id) {
    this.id = id;
    // Keep this task in a list. Also prevents
    // garbage collection of dead tasks:
    entrances.add(this);
  }
  public void run() {
    while(!canceled) {
      synchronized(this) {
        ++number;
      }
      print(this + " Total: " + count.increment());
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch(InterruptedException e) {
        print("sleep interrupted");
      }
    }
    print("Stopping " + this);
  }
  public synchronized int getValue() { return number; }
  public String toString() {
    return "Entrance " + id + ": " + getValue();
  }
  public static int getTotalCount() {
    return count.value();
  }
  public static int sumEntrances() {
    int sum = 0;
    for(Entrance entrance : entrances)
      sum += entrance.getValue();
    return sum;
  }
}

public class OrnamentalGarden {
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new Entrance(i));
    // Run for a while, then stop and collect the data:
    TimeUnit.SECONDS.sleep(3);
    Entrance.cancel();
    exec.shutdown();
    if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
      print("Some tasks were not terminated!");
    print("Total: " + Entrance.getTotalCount());
    print("Sum of Entrances: " + Entrance.sumEntrances());
  }
} /* Output: (Sample)
Entrance 0: 1 Total: 1
Entrance 2: 1 Total: 3
Entrance 1: 1 Total: 2
Entrance 4: 1 Total: 5
Entrance 3: 1 Total: 4
Entrance 2: 2 Total: 6
Entrance 4: 2 Total: 7
Entrance 0: 2 Total: 8
...
Entrance 3: 29 Total: 143
Entrance 0: 29 Total: 144
Entrance 4: 29 Total: 145
Entrance 2: 30 Total: 147
Entrance 1: 30 Total: 146
Entrance 0: 30 Total: 149
Entrance 3: 30 Total: 148
Entrance 4: 30 Total: 150
Stopping Entrance 2: 30
Stopping Entrance 1: 30
Stopping Entrance 0: 30
Stopping Entrance 3: 30
Stopping Entrance 4: 30
Total: 150
Sum of Entrances: 150
*///:~
/**
 * 这里使用单个的Count对象来跟踪花园参观者的主计数值，并且将其当作Entrance类中的一个静态域进行存储。Count.incrementO和Count.value（都是synchronized的，用来控制对count域的访问。
 * increment（方法使用了Random对象，目的是在从把count读取到temp中，到递增 temp并将其存储回count的这段时间里，有大约一半的时间产生让步。如果你将increment（）上的 synchronized关键字注释掉，
 * 那么这个程序就会崩溃，因为多个任务将同时访问并修改count（yield）会使问题更快地发生）。
 *
 * 每个Entrance任务都维护着一个本地值number，它包含通过某个特定入口进入的参观者的数量。这提供了对count对象的双重检查，以确保其记录的参观者数量是正确的。Entrance.run（）只是递增number和count对象，然后休眠100毫秒。
 *
 * 因为Entrance.canceled是一个volatile布尔标志，而它只会被读取和赋值（不会与其他域组合在一起被读取），所以不需要同步对其的访问，就可以安全地操作它。如果你对诸如此类的情况有任何疑虑，那么最好总是使用synchronized。
 *
 * 这个程序在以稳定的方式关闭所有事物方面还有一些小麻烦，其部分原因是为了说明在终止多线程程序时你必须相当小心，而另一部分原因是为了演示interruptO的值，稍后你将学习有关这个值的知识。
 *
 * 在3秒钟之后，mainO向Entrance发送static cancelO消息，然后调用exec对象的shutdownO方法，之后调用exec上的awaitTermination（O方法。ExecutorService.awaitTerminationO等待每个任务结束，
 * 如果所有的任务在超时时间达到之前全部结束，则返回true，否则返回false，表示不是所有的任务都已经结束了。尽管这会导致每个任务都退出其runO方法，并因此作为任务而终止，但是Entrance对象仍旧是有效的，
 * 因为在构造器中，每个Entrance对象都存储在称为entrances的静态List<Entrance>中。因此，sumEntrancesO仍旧可以作用于这些有效的Entrance对象。
 *
 * 当这个程序运行时，你将看到，在人们通过十字转门时，将显示总人数和通过每个入口的人数。如果移除Count.increment（）上面的synchronized声明，你将会注意到总人数与你的期望有差异，每个十字转门统计的人数将与count中的值不同。
 * 只要用互斥来同步对Count的访问，问题就可以解决了。请记住，Count.incrementO通过使用temp和yieldO，增加了失败的可能性。在真正的线程问题中，失败的可能性从统计学角度看可能非常小，
 * 因此你可能很容易就掉进了轻信所有事物都将正确工作的陷阱里。就像在上面的示例中，有些还未发生的问题就有可能会隐藏起来，因此在复审并发代码时，要格外地仔细。
 *
 * 练习17∶ （2） 创建一个辐射计数器，它可以具有任意数量的传感器。
 */
