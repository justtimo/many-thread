package com.wby.thread.manythread.character21并发.node3共享受限资源.child4原子类;//: concurrency/AtomicIntegerTest.java

import com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源.EvenChecker;
import com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源.IntGenerator;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java SE5引入了诸如AtomicInteger、AtomicLong、AtomicReference等特殊的原子性变量类，它们提供下面形式的原子性条件更新操作∶
 *    boolean compareAndSet(expectedValue, updateValue);
 *
 * 这些类被调整为可以使用在某些现代处理器上的可获得的，并且是在机器级别上的原子性，因此在使用它们时，通常不需要担心。对于常规编程来说，它们很少会派上用场，
 * 但是在涉及性能调优时，它们就大有用武之地了。例如，我们可以使用AtomicInteger来重写AtomicityTest.java;
 */
public class AtomicIntegerTest implements Runnable {
  private AtomicInteger i = new AtomicInteger(0);
  public int getValue() { return i.get(); }
  private void evenIncrement() { i.addAndGet(2); }
  public void run() {
    while(true)
      evenIncrement();
  }
  public static void main(String[] args) {
    new Timer().schedule(new TimerTask() {
      public void run() {
        System.err.println("Aborting");
        System.exit(0);
      }
    }, 5000); // Terminate after 5 seconds
    ExecutorService exec = Executors.newCachedThreadPool();
    AtomicIntegerTest ait = new AtomicIntegerTest();
    exec.execute(ait);
    while(true) {
      int val = ait.getValue();
      if(val % 2 != 0) {
        System.out.println(val);
        System.exit(0);
      }
    }
  }
} ///:~
/**
 * 这里我们通过使用AtomicInteger而消除了synchronized关键字。因为这个程序不会失败，所以添加了一个Timer，以便在5秒钟之后自动地终止。
 * 下面是用AtomicInteger重写的MutexEvenGeneratorjava∶
 */
class AtomicEvenGenerator extends IntGenerator {
  private AtomicInteger currentEvenValue =
          new AtomicInteger(0);
  public int next() {
    return currentEvenValue.addAndGet(2);
  }
  public static void main(String[] args) {
    EvenChecker.test(new AtomicEvenGenerator());
  }
} ///:~
/**
 *  所有其他形式的同步再次通过使用AtomicInteger得到了根除。
 *
 * 应该强调的是，Atomic类被设计用来构建java.util.concurrent中的类，因此只有在特殊情况下才在自己的代码中使用它们，即便使用了也需要确保不存在其他可能出现的问题。
 * 通常依赖于锁要更安全一些（要么是synchronized关键字，要么是显式的Lock对象）。
 */
































