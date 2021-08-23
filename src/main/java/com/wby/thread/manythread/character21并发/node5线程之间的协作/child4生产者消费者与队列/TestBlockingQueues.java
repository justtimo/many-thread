package com.wby.thread.manythread.character21并发.node5线程之间的协作.child4生产者消费者与队列;//: concurrency/TestBlockingQueues.java
// {RunByHand}

import com.wby.thread.manythread.character21并发.node2基本的线程机制.child1定义任务.LiftOff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * waitO和notifyAlIO方法以一种非常低级的方式解决了任务互操作问题，即每次交互时都握手。在许多情况下，你可以瞄向更高的抽象级别，使用同步队列来解决任务协作问题，同步队列在任何时刻都只允许一个任务插入或移除元素。
 * 在java.util.concurrent.BlockingQueue接口中提供了这个队列，这个接口有大量的标准实现。你通常可以使用LinkedBlockingQueue，它是一个无届队列，还可以使用ArrayBlockingQueue，它具有固定的尺寸，
 * 因此你可以在它被阻塞之前，向其中放置有限数量的元素。
 *
 * 如果消费者任务试图从队列中获取对象，而该队列此时为空，那么这些队列还可以挂起消费者任务，并且当有更多的元素可用时恢复消费者任务。阻塞队列可以解决非常大量的问题，而其方式与wait（）和notifyAllO）相比，则简单并可靠得多。
 *
 * 下面是一个简单的测试，它将多个LiftOff对象的执行串行化了。消费者是LiftOffRunner，它将每个LiftOff对象从BlockingQueue中推出并直接运行。（即，它通过显式地调用runO而使用自己的线程来运行，而不是为每个任务启动一个新线程。）
 */
class LiftOffRunner implements Runnable {
  private BlockingQueue<LiftOff> rockets;
  public LiftOffRunner(BlockingQueue<LiftOff> queue) {
    rockets = queue;
  }
  public void add(LiftOff lo) {
    try {
      rockets.put(lo);
    } catch(InterruptedException e) {
      print("Interrupted during put()");
    }
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        LiftOff rocket = rockets.take();
        rocket.run(); // Use this thread
      }
    } catch(InterruptedException e) {
      print("Waking from take()");
    }
    print("Exiting LiftOffRunner");
  }
}

public class TestBlockingQueues {
  static void getkey() {
    try {
      // Compensate for Windows/Linux difference in the
      // length of the result produced by the Enter key:
      new BufferedReader(
        new InputStreamReader(System.in)).readLine();
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  static void getkey(String message) {
    print(message);
    getkey();
  }
  static void
  test(String msg, BlockingQueue<LiftOff> queue) {
    print(msg);
    LiftOffRunner runner = new LiftOffRunner(queue);
    Thread t = new Thread(runner);
    t.start();
    for(int i = 0; i < 5; i++)
      runner.add(new LiftOff(5));
    getkey("Press 'Enter' (" + msg + ")");
    t.interrupt();
    print("Finished " + msg + " test");
  }
  public static void main(String[] args) {
    test("LinkedBlockingQueue", // Unlimited size
      new LinkedBlockingQueue<LiftOff>());
    test("ArrayBlockingQueue", // Fixed size
      new ArrayBlockingQueue<LiftOff>(3));
    test("SynchronousQueue", // Size of 1
      new SynchronousQueue<LiftOff>());
  }
} ///:~
/**
 * 各个任务由main（放置到了BlockingQueue中，并且由LiftOffRunner从BlockingQueue中取出。注意，LiftOffRunner可以忽略同步问题，因为它们已经由BlockingQueue解决了。
 */

/**
 * 吐司BlockingQueue
 * 考虑下面这个使用BlockingQueue的示例，有一台机器具有三个任务∶一个制作吐司、一个给吐司抹黄油，另一个在抹过黄油的吐司上涂果酱。我们可以通过各个处理过程之间的 BlockingQueue来运行这个吐司制作程序∶
 */
class Toast {
  public enum Status { DRY, BUTTERED, JAMMED }
  private Status status = Status.DRY;
  private final int id;
  public Toast(int idn) { id = idn; }
  public void butter() { status = Status.BUTTERED; }
  public void jam() { status = Status.JAMMED; }
  public Status getStatus() { return status; }
  public int getId() { return id; }
  public String toString() {
    return "Toast " + id + ": " + status;
  }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

class Toaster implements Runnable {
  private ToastQueue toastQueue;
  private int count = 0;
  private Random rand = new Random(47);
  public Toaster(ToastQueue tq) { toastQueue = tq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(
                100 + rand.nextInt(500));
        // Make toast
        Toast t = new Toast(count++);
        print(t);
        // Insert into queue
        toastQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("Toaster interrupted");
    }
    print("Toaster off");
  }
}

// Apply butter to toast:
class Butterer implements Runnable {
  private ToastQueue dryQueue, butteredQueue;
  public Butterer(ToastQueue dry, ToastQueue buttered) {
    dryQueue = dry;
    butteredQueue = buttered;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Toast t = dryQueue.take();
        t.butter();
        print(t);
        butteredQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("Butterer interrupted");
    }
    print("Butterer off");
  }
}

// Apply jam to buttered toast:
class Jammer implements Runnable {
  private ToastQueue butteredQueue, finishedQueue;
  public Jammer(ToastQueue buttered, ToastQueue finished) {
    butteredQueue = buttered;
    finishedQueue = finished;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Toast t = butteredQueue.take();
        t.jam();
        print(t);
        finishedQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("Jammer interrupted");
    }
    print("Jammer off");
  }
}

// Consume the toast:
class Eater implements Runnable {
  private ToastQueue finishedQueue;
  private int counter = 0;
  public Eater(ToastQueue finished) {
    finishedQueue = finished;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Toast t = finishedQueue.take();
        // Verify that the toast is coming in order,
        // and that all pieces are getting jammed:
        if(t.getId() != counter++ ||
                t.getStatus() != Toast.Status.JAMMED) {
          print(">>>> Error: " + t);
          System.exit(1);
        } else
          print("Chomp! " + t);
      }
    } catch(InterruptedException e) {
      print("Eater interrupted");
    }
    print("Eater off");
  }
}

class ToastOMatic {
  public static void main(String[] args) throws Exception {
    ToastQueue dryQueue = new ToastQueue(),
            butteredQueue = new ToastQueue(),
            finishedQueue = new ToastQueue();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Toaster(dryQueue));
    exec.execute(new Butterer(dryQueue, butteredQueue));
    exec.execute(new Jammer(butteredQueue, finishedQueue));
    exec.execute(new Eater(finishedQueue));
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
} /* (Execute to see output) *///:~
/**
 * Toast是一个使用enum值的优秀示例。注意，这个示例中没有任何显式的同步（即使用Lock对象或synchronized关键字的同步），因为同步由队列（其内部是同步的）和系统的设计隐式地管理了——每片Toast在任何时刻都只由一个任务在操作。
 * 因为队列的阻塞，使得处理过程将被自动地挂起和恢复。你可以看到由BlockingQueue产生的简化十分明显。在使用显式的wait（O和 notifyAllO时存在的类和类之间的耦合被消除了，因为每个类都只和它的BlockingQueue通信。
 *
 * 练习29∶（8）修改ToastOMatic，java，使用两个单独的组装线来创建涂有花生黄油和果冻的吐司三明治（一个用于花生黄油，第二个用于果冻，然后把两条线合并）。
 */






























