package com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源;//: concurrency/IntGenerator.java

/**
 * 考虑下面的例子，其中一个任务产生偶数，而其他任务消费这些数字。这里，消费者任务的唯一工作就是检查偶数的有效性。
 *
 * 首先，我们定义EvenChecker，即消费者任务，因为它将在随后所有的示例中被复用。为了将EvenChecker与我们要试验的各种类型的生成器解耦，
 * 我们将创建一个名为IntGenerator的抽象类，它包含EvenChecker必须了解的必不可少的方法∶即一个next（O方法，和一个可以执行撤销的方法。这个类没有实现Generator接口，因为它必须产生一个int，而泛型不支持基本类型的参数∶
 */
public abstract class IntGenerator {
  private volatile boolean canceled = false;
  public abstract int next();
  // Allow this to be canceled:
  public void cancel() { canceled = true; }
  public boolean isCanceled() { return canceled; }
} ///:~


/**
 * IntGenerator有一个cancel（）方法，可以修改boolean类型的canceled标志的状态;还有一个 isCanceledO方法，可以查看该对象是否已经被取消。
 * 因为canceled标志是boolean类型的，所以它是原子性的，即诸如赋值和返回值这样的简单操作在发生时没有中断的可能，因此你不会看到这个域处于在执行这些简单操作的过程中的中间状态。
 * 为了保证可视性，canceled标志还是 volatile的。你将在本章稍后学习原子性和可视性。
 *
 * 任何IntGenerator都可以用下面的EvenChecker类来测试∶
 *  见EvenChecker
 */


