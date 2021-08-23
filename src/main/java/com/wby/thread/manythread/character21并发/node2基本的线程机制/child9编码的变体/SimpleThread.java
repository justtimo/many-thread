package com.wby.thread.manythread.character21并发.node2基本的线程机制.child9编码的变体;//: concurrency/SimpleThread.java
// Inheriting directly from the Thread class.

import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 到目前为止，在你所看到的示例中，任务类都实现了Runnable。在非常简单的情况下，你可能会希望使用直接从Thread继承这种可替换的方式，就像下面这样∶
 */
public class SimpleThread extends Thread {
  private int countDown = 5;
  private static int threadCount = 0;
  public SimpleThread() {
    // Store the thread name:
    super(Integer.toString(++threadCount));
    start();
  }
  public String toString() {
    return "#" + getName() + "(" + countDown + "), ";
  }
  public void run() {
    while(true) {
      System.out.print(this);
      if(--countDown == 0)
        return;
    }
  }
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++)
      new SimpleThread();
  }
} /* Output:
#1(5), #1(4), #1(3), #1(2), #1(1), #2(5), #2(4), #2(3), #2(2), #2(1), #3(5), #3(4), #3(3), #3(2), #3(1), #4(5), #4(4), #4(3), #4(2), #4(1), #5(5), #5(4), #5(3), #5(2), #5(1),
*///:~
/**
 * 你可以通过调用适当的Thread构造器为Thread对象赋予具体的名称，这个名称可以通过使用getName（）从toStringO中获得。
 * 另一种可能会看到的惯用法是自管理的Runnable∶
 */
class SelfManaged implements Runnable {
  private int countDown = 5;
  private Thread t = new Thread(this);
  public SelfManaged() { t.start(); }
  public String toString() {
    return Thread.currentThread().getName() +
            "(" + countDown + "), ";
  }
  public void run() {
    while(true) {
      System.out.print(this);
      if(--countDown == 0)
        return;
    }
  }
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++)
      new SelfManaged();
  }
} /* Output:
Thread-0(5), Thread-0(4), Thread-0(3), Thread-0(2), Thread-0(1), Thread-1(5), Thread-1(4), Thread-1(3), Thread-1(2), Thread-1(1), Thread-2(5), Thread-2(4), Thread-2(3), Thread-2(2), Thread-2(1), Thread-3(5), Thread-3(4), Thread-3(3), Thread-3(2), Thread-3(1), Thread-4(5), Thread-4(4), Thread-4(3), Thread-4(2), Thread-4(1),
*///:~
/**
 * 这与从Thread继承并没有什么特别的差异，只是语法稍微晦涩一些。但是，实现接口使得你可以继承另一个不同的类，而从Thread继承将不行。
 *
 * 注意，startO是在构造器中调用的。这个示例相当简单，因此可能是安全的，但是你应该意识到，在构造器中启动线程可能会变得很有问题，因为另一个任务可能会在构造器结束之前开始执行，这意味着该任务能够访问处于不稳定状态的对象。
 * 这是优选Executor而不是显式地创建Thread对象的另一个原因。
 *
 * 有时通过使用内部类来将线程代码隐藏在类中将会很有用，就像下面这样;
 */
// Using a named inner class:
class InnerThread1 {
  private int countDown = 5;
  private Inner inner;
  private class Inner extends Thread {
    Inner(String name) {
      super(name);
      start();
    }
    public void run() {
      try {
        while(true) {
          print(this);
          if(--countDown == 0) return;
          sleep(10);
        }
      } catch(InterruptedException e) {
        print("interrupted");
      }
    }
    public String toString() {
      return getName() + ": " + countDown;
    }
  }
  public InnerThread1(String name) {
    inner = new Inner(name);
  }
}

// Using an anonymous inner class:
class InnerThread2 {
  private int countDown = 5;
  private Thread t;
  public InnerThread2(String name) {
    t = new Thread(name) {
      public void run() {
        try {
          while(true) {
            print(this);
            if(--countDown == 0) return;
            sleep(10);
          }
        } catch(InterruptedException e) {
          print("sleep() interrupted");
        }
      }
      public String toString() {
        return getName() + ": " + countDown;
      }
    };
    t.start();
  }
}

// Using a named Runnable implementation:
class InnerRunnable1 {
  private int countDown = 5;
  private Inner inner;
  private class Inner implements Runnable {
    Thread t;
    Inner(String name) {
      t = new Thread(this, name);
      t.start();
    }
    public void run() {
      try {
        while(true) {
          print(this);
          if(--countDown == 0) return;
          TimeUnit.MILLISECONDS.sleep(10);
        }
      } catch(InterruptedException e) {
        print("sleep() interrupted");
      }
    }
    public String toString() {
      return t.getName() + ": " + countDown;
    }
  }
  public InnerRunnable1(String name) {
    inner = new Inner(name);
  }
}

// Using an anonymous Runnable implementation:
class InnerRunnable2 {
  private int countDown = 5;
  private Thread t;
  public InnerRunnable2(String name) {
    t = new Thread(new Runnable() {
      public void run() {
        try {
          while(true) {
            print(this);
            if(--countDown == 0) return;
            TimeUnit.MILLISECONDS.sleep(10);
          }
        } catch(InterruptedException e) {
          print("sleep() interrupted");
        }
      }
      public String toString() {
        return Thread.currentThread().getName() +
                ": " + countDown;
      }
    }, name);
    t.start();
  }
}

// A separate method to run some code as a task:
class ThreadMethod {
  private int countDown = 5;
  private Thread t;
  private String name;
  public ThreadMethod(String name) { this.name = name; }
  public void runTask() {
    if(t == null) {
      t = new Thread(name) {
        public void run() {
          try {
            while(true) {
              print(this);
              if(--countDown == 0) return;
              sleep(10);
            }
          } catch(InterruptedException e) {
            print("sleep() interrupted");
          }
        }
        public String toString() {
          return getName() + ": " + countDown;
        }
      };
      t.start();
    }
  }
}

class ThreadVariations {
  public static void main(String[] args) {
    new InnerThread1("InnerThread1");
    new InnerThread2("InnerThread2");
    new InnerRunnable1("InnerRunnable1");
    new InnerRunnable2("InnerRunnable2");
    new ThreadMethod("ThreadMethod").runTask();
  }
} /* (Execute to see output) *///:~
/**
 * InnerThread1创建了一个扩展自Thread的匿名内部类，并且在构造器中创建了这个内部类的一个实例。如果内部类具有你在其他方法中需要访问的特殊能力（新方法），
 * 那这么做将会很，有意义。但是，在大多数时候，创建线程的原因只是为了使用Thread的能力，因此不必创建匿名内部类。InnerThread2展示了可替换的方式;在构造器中创建—一个匿名的Thread子类，
 * 并目将其向上转型为Thread引用t。如果类中的其他方法需要访问t，那它们可以通过Thread接口来实现，并且不需要了解该对象的确切类型。
 *
 * 该示例的第三个和第四个类重复了前面的两个类，但是它们使用的是Runnable接口而不是 Thread类。
 *
 * ThreadMethod类展示了在方法内部如何创建线程。当你准备好运行线程时，就可以调用这个方法，而在线程开始之后，该方法将返回。如果该线程只执行辅助操作，而不是该类的重要操作，
 * 那么这与在该类的构造器内部启动线程相比，可能是一种更加有用而适合的方式。
 *
 * 练习10;（4）按照ThreadMethod类修改练习5，使得runTaskO）方法将接受一个参数，表示要计算总和的斐波纳契数字的数量，并且，每次调用runTaskO时。它将返回对submitO的调用所产生的Future。
 */
