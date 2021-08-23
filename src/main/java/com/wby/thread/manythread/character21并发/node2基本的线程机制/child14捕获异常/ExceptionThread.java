package com.wby.thread.manythread.character21并发.node2基本的线程机制.child14捕获异常;//: concurrency/ExceptionThread.java
// {ThrowsException}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 由于线程的本质特性，使得你不能捕获从线程中逃逸的异常。—日异常逃出任务的run（方法，它就会向外传播到控制台，除非你采取特殊的步骤捕获这种错误的异常。
 * 在Java SE5之前，你可以使用线程组来捕获这些异常，但是有了Java SE5，就可以用Executor来解决这个问题，因此你就不再需要了解有关线程组的任何知识了
 * （除非要理解遗留代码，请查看可以从www. MindView.net下载的《Thinking in Java （2nd Edition）》，以了解线程组的细节）。
 *
 * 下面的任务总是会抛出一个异常，该异常会传播到其runO方法的外部，并且main（）展示了当你运行它时所发生的事情∶
 */
public class ExceptionThread implements Runnable {
  public void run() {
    throw new RuntimeException();
  }
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new ExceptionThread());
  }
} ///:~
/**
 * 输出如下（将某些限定符修整为适合显示）∶
 * java.Lang.RuntimeException
 * at ExceptionThread.run(ExceptionThread.java:7) at ThreadPoolExecutor$Worker.runTask(Unknown Source) at ThreadPoolExecutor$Worker.run(Unknown Source) at java.1ang.Thread.run(Unknown Source)
 *
 * 将main的主体放到try-catch语句块中是没有作用的∶
 */
class NaiveExceptionHandling {
  public static void main(String[] args) {
    try {
      ExecutorService exec =
              Executors.newCachedThreadPool();
      exec.execute(new ExceptionThread());
    } catch(RuntimeException ue) {
      // This statement will NOT execute!
      System.out.println("Exception has been handled!");
    }
  }
} ///:~
/**
 * 这将产生与前面示例相同的结果∶未捕获的异常。
 *
 * 为了解决这个问题，我们要修改Executor产生线程的方式。Thread.UncaughtException- Handler是Java SE5中的新接口，它允许你在每个Thread对象上都附着一个异常处理器。
 * Thread.UncaughtExceptionHandler.uncaughtException（）会在线程因未捕获的异常而临近死亡时被调用。为了使用它，我们创建了一个新类型的ThreadFactory，
 * 它将在每个新创建的Thread对象上附着一个Thread.UncaughtExceptionHandler。我们将这个工厂传递给Executors创建新的 ExecutorService的方法;
 */
class ExceptionThread2 implements Runnable {
  public void run() {
    Thread t = Thread.currentThread();
    System.out.println("run() by " + t);
    System.out.println(
            "eh = " + t.getUncaughtExceptionHandler());
    throw new RuntimeException();
  }
}

class MyUncaughtExceptionHandler implements
        Thread.UncaughtExceptionHandler {
  public void uncaughtException(Thread t, Throwable e) {
    System.out.println("caught " + e);
  }
}

class HandlerThreadFactory implements ThreadFactory {
  public Thread newThread(Runnable r) {
    System.out.println(this + " creating new Thread");
    Thread t = new Thread(r);
    System.out.println("created " + t);
    t.setUncaughtExceptionHandler(
            new MyUncaughtExceptionHandler());
    System.out.println(
            "eh = " + t.getUncaughtExceptionHandler());
    return t;
  }
}

class CaptureUncaughtException {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool(
            new HandlerThreadFactory());
    exec.execute(new ExceptionThread2());
  }
} /* Output: (90% match)
HandlerThreadFactory@de6ced creating new Thread
created Thread[Thread-0,5,main]
eh = MyUncaughtExceptionHandler@1fb8ee3
run() by Thread[Thread-0,5,main]
eh = MyUncaughtExceptionHandler@1fb8ee3
caught java.lang.RuntimeException
*///:~
/**
 * 在程序中添加了额外的跟踪机制，用来验证工厂创建的线程会传递给UncaughtException-Handler。你现在可以看到，未捕获的异常是通过uncaughtException来捕获的。
 *
 * 上面的示例使得你可以按照具体情况逐个地设置处理器。如果你知道将要在代码中处处使用相同的异常处理器，那么更简单的方式是在Thread类中设置一个静态域。并将这个处理器设置为默认的未捕获异常处理器∶
 */
class SettingDefaultHandler {
  public static void main(String[] args) {
    Thread.setDefaultUncaughtExceptionHandler(
            new MyUncaughtExceptionHandler());
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new ExceptionThread());
  }
} /* Output:
caught java.lang.RuntimeException
*///:~
/**
 * 这个处理器只有在不存在线程专有的未捕获异常处理器的情况下才会被调用。系统会检查线程专有版本，如果没有发现，则检查线程组是否有其专有的uncaughtExceptionO方法，如果也没有，再调用defaultUncaughtExceptionHandler。
 */
