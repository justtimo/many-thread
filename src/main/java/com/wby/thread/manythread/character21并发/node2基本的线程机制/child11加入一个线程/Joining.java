package com.wby.thread.manythread.character21并发.node2基本的线程机制.child11加入一个线程;//: concurrency/Joining.java
// Understanding join().

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 一个线程可以在其他线程之上调用join（）方法，其效果是等待一段时间直到第二个线程结束才继续执行。如果某个线程在另一个线程t上调用t.joinO，此线程将被挂起，直到目标线程t结束才恢复（即t.isAlive（）返回为假）。
 *
 * 也可以在调用joinO）时带上一个超时参数 （单位可以是毫秒，或者毫秒和纳秒），这样如果目标线程在这段时间到期时还没有结束的话，joinO方法总能返回。
 *
 * 对join（）方法的调用可以被中断，做法是在调用线程上调用interruptO方法，这时需要用到 try-catch子句。
 * 下面这个例子演示了所有这些操作∶
 */
class Sleeper extends Thread {
  private int duration;
  public Sleeper(String name, int sleepTime) {
    super(name);
    duration = sleepTime;
    start();
  }
  public void run() {
    try {
      System.out.println(getName()+" run()");
      sleep(duration);
    } catch(InterruptedException e) {
      print(getName() + " was interrupted. " +
        "isInterrupted(): " + isInterrupted());
      return;
    }
    print(getName() + " has awakened");
  }
}

class Joiner extends Thread {
  private Sleeper sleeper;
  public Joiner(String name, Sleeper sleeper) {
    super(name);
    this.sleeper = sleeper;
    start();
  }
  public void run() {
   try {
     System.out.println(getName()+" run()");
      sleeper.join();
    } catch(InterruptedException e) {
      print("Interrupted");
    }
    print(getName() + " join completed");
  }
}

public class Joining {
  public static void main(String[] args) {
    Sleeper
      sleepy = new Sleeper("Sleepy", 1500),
      grumpy = new Sleeper("Grumpy", 1500);
    Joiner
      dopey = new Joiner("Dopey", sleepy),
      doc = new Joiner("Doc", grumpy);
    grumpy.interrupt();
  }
} /* Output:
Grumpy was interrupted. isInterrupted(): false
Doc join completed
Sleepy has awakened
Dopey join completed
*///:~
/**
 * Sleeper是一个Thread类型，它要休眠一段时间，这段时间是通过构造器传进来的参数所指定的。在runO中，sleepQ方法有可能在指定的时间期满时返回，但也可能被中断。在catch子句中，将根据isInterruptedO的返回值报告这个中断。
 * 当另一个线程在该线程上调用interruptO时，将给该线程设定一个标志，表明该线程已经被中断。然而，异常被捕获时将清理这个标志，所以在catch子句中，在异常被捕获的时候这个标志总是为假。
 * 除异常之外，这个标志还可用于其他情况，比如线程可能会检查其中断状态。
 *
 * Joiner线程将通过在Sleeper对象上调用joinO）方法来等待Sleeper醒来。在main（里面，每个 Sleeper都有—个Joiner，这可以在输出中发现，如果Sleeper被中断或者是正常结束，Joiner将和Sleeper一同结束。
 *
 * 注意，Java SE5的java.util.concurrent类库包含诸如CyclicBarrier（本章稍后会展示）这样的工具，它们可能比最初的线程类库中的join（）更加适合。
 */
