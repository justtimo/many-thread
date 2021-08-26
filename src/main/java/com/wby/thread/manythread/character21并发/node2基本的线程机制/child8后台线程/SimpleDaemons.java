package com.wby.thread.manythread.character21并发.node2基本的线程机制.child8后台线程;//: concurrency/SimpleDaemons.java
// Daemon threads don't prevent the program from ending.

import java.util.concurrent.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 所谓后台 （daemon）线程，是指在程序运行的时候在后台提供一种通用服务的线程，并目这种线程并不属于程序中不可或缺的部分。因此，当所有的非后台线程结束时，程序也就终止了，同时会杀死进程中的所有后台线程。
 * 反过来说，只要有任何非后台线程还在运行，程序就不会终止。比如，执行main（O的就是一个非后台线程。
 */
public class SimpleDaemons implements Runnable {
  public void run() {
    try {
      while(true) {
        TimeUnit.MILLISECONDS.sleep(100);
        print(Thread.currentThread() + " " + this);
      }
    } catch(InterruptedException e) {
      print("sleep() interrupted");
    }
  }
  public static void main(String[] args) throws Exception {
    for(int i = 0; i < 10; i++) {
      Thread daemon = new Thread(new SimpleDaemons());
      daemon.setDaemon(true); // Must call before start()
      daemon.start();
    }
    print("All daemons started");
    TimeUnit.MILLISECONDS.sleep(1750);
  }
} /* Output: (Sample)
All daemons started
Thread[Thread-0,5,main] SimpleDaemons@530daa
Thread[Thread-1,5,main] SimpleDaemons@a62fc3
Thread[Thread-2,5,main] SimpleDaemons@89ae9e
Thread[Thread-3,5,main] SimpleDaemons@1270b73
Thread[Thread-4,5,main] SimpleDaemons@60aeb0
Thread[Thread-5,5,main] SimpleDaemons@16caf43
Thread[Thread-6,5,main] SimpleDaemons@66848c
Thread[Thread-7,5,main] SimpleDaemons@8813f2
Thread[Thread-8,5,main] SimpleDaemons@1d58aae
Thread[Thread-9,5,main] SimpleDaemons@83cc67
...
*///:~
/**
 * 必须在线程启动之前调用setDaemonO方法，才能把它设置为后台线程。
 *
 * 一旦mainO）完成其工作，就没什么能阻止程序终止了，因为除了后台线程之外，已经没有线程在运行了。main（）线程被设定为知暂睡眠，所以可以观察到所有后台线程启动后的结果.
 * 不这样的话，你就只能看见一些后台线程创建时得到的结果（试试调整sleepO休眠的时间，以观察这个行为）。
 *
 * SimpleDaemons.java创建了显式的线程，以便可以设置它们的后台标志。通过编写定制的 ThreadFactory可以定制由Executor创建的线程的属性（后台、优先级、名称）∶
 */
class DaemonThreadFactory implements ThreadFactory {
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }
} ///:~
/**
 * 这与普通的ThreadFactory的唯一差异就是它将后台状态全部设置为了true。你现在可以用一个新的DaemonThreadFactory作为参数传递给ExecutornewCachedThreadPool0）∶
 */
class DaemonFromFactory implements Runnable {
  public void run() {
    try {
      while(true) {
        TimeUnit.MILLISECONDS.sleep(100);
        print(Thread.currentThread() + " " + this);
      }
    } catch(InterruptedException e) {
      print("Interrupted");
    }
  }
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool(
            new DaemonThreadFactory());
    for(int i = 0; i < 10; i++)
      exec.execute(new DaemonFromFactory());
    print("All daemons started");
    TimeUnit.MILLISECONDS.sleep(500); // Run for a while
  }
} /* (Execute to see output) *///:~
/**
 * 每个静态的ExecutorService创建方法都被重载为接受一个ThreadFactory对象，而这个对象将被用来创建新的线程∶
 */
class DaemonThreadPoolExecutor
        extends ThreadPoolExecutor {
  public DaemonThreadPoolExecutor() {
    super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new DaemonThreadFactory());
  }
} ///:~
/**
 * 可以通过调用isDaemon（）方法来确定线程是否是一个后台线程。如果是一个后台线程，那么它创建的任何线程将被自动设置成后台线程，如下例所示∶
 */
class Daemon implements Runnable {
  private Thread[] t = new Thread[10];
  public void run() {
    for(int i = 0; i < t.length; i++) {
      t[i] = new Thread(new DaemonSpawn());
      t[i].start();
      printnb("DaemonSpawn " + i + " started, ");
    }
    for(int i = 0; i < t.length; i++)
      printnb("t[" + i + "].isDaemon() = " +
              t[i].isDaemon() + ", ");
    while(true)
      Thread.yield();
  }
}

class DaemonSpawn implements Runnable {
  public void run() {
    while(true)
      Thread.yield();
  }
}

class Daemons {
  public static void main(String[] args) throws Exception {
    Thread d = new Thread(new Daemon());
    d.setDaemon(true);
    d.start();
    printnb("d.isDaemon() = " + d.isDaemon() + ", ");
    // Allow the daemon threads to
    // finish their startup processes:
    TimeUnit.SECONDS.sleep(1);
  }
} /* Output: (Sample)
d.isDaemon() = true, DaemonSpawn 0 started, DaemonSpawn 1 started, DaemonSpawn 2 started, DaemonSpawn 3 started, DaemonSpawn 4 started, DaemonSpawn 5 started, DaemonSpawn 6 started, DaemonSpawn 7 started, DaemonSpawn 8 started, DaemonSpawn 9 started, t[0].isDaemon() = true, t[1].isDaemon() = true, t[2].isDaemon() = true, t[3].isDaemon() = true, t[4].isDaemon() = true, t[5].isDaemon() = true, t[6].isDaemon() = true, t[7].isDaemon() = true, t[8].isDaemon() = true, t[9].isDaemon() = true,
*///:~
/**
 * Daemon线程被设置成了后台模式，然后派生出许多子线程，这些线程并没有被显式地设置为后台模式，不过它们的确是后台线程。接着，Daemon线程进入了无限循环，并在循环里调用 yield（方法把控制权交给其他进程。
 *
 * 你应该意识到后台进程在不执行finally子句的情况下就会终止其run（O方法∶
 */
class ADaemon implements Runnable {
  public void run() {
    try {
      print("Starting ADaemon");
      TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
      print("Exiting via InterruptedException");
    } finally {
      print("This should always run?");
    }
  }
}

class DaemonsDontRunFinally {
  public static void main(String[] args) throws Exception {
    Thread t = new Thread(new ADaemon());
    t.setDaemon(true);
    t.start();
  }
} /* Output:
Starting ADaemon
*///:~
/**
 * 当你运行这个程序时，你将看到finally子句就不会执行，但是如果你注释掉对setDaemonO）的调用，就会看到finally子句将会执行。
 *
 * 这种行为是正确的，即便你基于前面对finally给出的承诺，并不希望出现这种行为，但情况仍将如此。当最后一个非后台线程终止时，后台线程会"突然"终止。因此一旦main（O）退出，
 * JVM就会立即关闭所有的后台进程，而不会有任何你希望出现的确认形式。因为你不能以优雅的方式来关闭后台线程，所以它们几乎不是一种好的思想。非后台的Executor通常是一种更好的方式，
 * 因为Executor控制的所有任务可以同时被关闭。正如你将要在本章稍后看到的，在这种情况下，关闭将以有序的方式执行。
 *
 * 练习7;（2）在Daemons.java中使用不同的休眠时间，并观察结果。
 * 练习8∶（1）把SimpleThread.java中的所有线程修改成后台线程，并验证一旦main（O退出，程序立刻终止。
 * 练习9∶（3）修改SimplePriorities.java，使得定制的ThreadFactory可以设置线程的优先级。
 */
